package com.dldriver.driver.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import cz.msebera.android.httpclient.Header;

import com.dldriver.driver.NetworkResponses;
import com.dldriver.driver.R;
import com.dldriver.driver.contracts.OrderContracts;
import com.dldriver.driver.interactors.OrderInteracterImpl;
import com.dldriver.driver.interfaces.ClickListeners;
import com.dldriver.driver.models.BaseError;
import com.dldriver.driver.models.RemarkList;
import com.dldriver.driver.models.Remarks;
import com.dldriver.driver.models.Response;
import com.dldriver.driver.room.AppDatabase;
import com.dldriver.driver.ui.adapter.RemarksAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentStatusDialog extends DialogFragment implements CompoundButton.OnCheckedChangeListener, NetworkResponses<Response>, ClickListeners.getRemarkList {


    private boolean isChecked;
    private int mDriverId;
    private int mOrderId;
    private static OrderContracts.IOrderPresenter iOrderPresenter;
    private RecyclerView rvRemarks;
    private List<Remarks> checkedRemarksArraylist = new ArrayList<>();

    public PaymentStatusDialog() {
        // Required empty public constructor
    }

    public static PaymentStatusDialog newInstance(OrderContracts.IOrderPresenter iOrderPresenter, int orderId, int driverId) {
        PaymentStatusDialog.iOrderPresenter = iOrderPresenter;

        Bundle args = new Bundle();
        args.putInt("orderId", orderId);
        args.putInt("driverId", driverId);
        PaymentStatusDialog fragment = new PaymentStatusDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static final String TAG = "PaymentStatusDialog";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_status_dialog, container, false);
        rvRemarks = view.findViewById(R.id.rvRemarks);
        mDriverId = getArguments().getInt("driverId");
        mOrderId = getArguments().getInt("orderId");
        AppCompatCheckBox checkBox = view.findViewById(R.id.checkBoxDialog);
        ImageView back = view.findViewById(R.id.backButton);
        back.setOnClickListener(v -> this.dismiss());
        EditText feedback = view.findViewById(R.id.feedbackDialog);
        Button submit = view.findViewById(R.id.submitDialog);
        checkBox.setOnCheckedChangeListener(this);


        getRemarks();

        submit.setOnClickListener(v -> {
            if (!isChecked) {
                if(checkedRemarksArraylist.size() > 0) {
                    postRemarks();
                    OrderInteracterImpl orderInteracter = new OrderInteracterImpl(this, iOrderPresenter);
                    orderInteracter.updatePaymentStatus(mOrderId, mDriverId,
                            isChecked ? 1 : 0, this);
                }else{
                   Toast.makeText(getContext(),"Select Remark",Toast.LENGTH_LONG).show();
                }

            } else {
                OrderInteracterImpl orderInteracter = new OrderInteracterImpl(this, iOrderPresenter);
                orderInteracter.updatePaymentStatus(mOrderId, mDriverId,
                        isChecked ? 1 : 0, this);

            }
        });
        return view;
    }

    private void postRemarks() {
        Log.d("Success-", "JSON:" + "Inside updateRemarksList");
        try {

            JSONObject object = new JSONObject();
            object.put("id", mDriverId);
            object.put("orderId", mOrderId);
            JSONArray jsonArray = new JSONArray();
            for (Remarks remarks : checkedRemarksArraylist) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("remarksId", remarks.getId());
                    obj.put("remarks_text", remarks.getRemarks());
                    jsonArray.put(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            object.put("remarks", jsonArray);
            String Data = object.toString();
            Log.d("Success-", "JSON:" + "Inside updateTimeList :" + object.toString());
            //     apiCalls.callApiPost(TimingsSettingActivity.this, params, dialog, url, 3);

            StringEntity entity = null;


            entity = new StringEntity(Data.toString());
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            String url = "http://148.72.64.138:3007/orderremarks/new";

            new AsyncHttpClient().post(null, url, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    try {

                        String object = new String(responseBody);
                        JSONObject jsonObject = new JSONObject(object);
                        String result = jsonObject.getString("result");
                        Log.d("Success-", "JSON:" + "Inside updateTimeList result :" + object.toString());
                        // if (result.equals("Data Inserted")) {
//                        Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

                        // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                        // }
                    } catch (JSONException e) {
                       // Toast.makeText(getContext(), "msg", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("Success-", "JSON:" + "Inside updateTimeList error :" + error.toString());
                }
            });
        } catch (Exception e) {
//Exception
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getRemarks() {
        Log.d("Success-", "JSON:" + "Inside Remarks");
        RequestParams params = new RequestParams();
        params.put("id", mDriverId);
        // params.put(Constants.email, "deepu.tk2@gmail.com");
        String url = "http://148.72.64.138:3007/adminremarks/driverlist";
        callApiPost(params, url);
    }

    public void callApiPost(RequestParams params
            , String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(50000);


        client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("Failure-", "JSON:" + errorResponse);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Success-", "JSON:" + response.toString());
                setDataToRecyclerview(response.toString());
                //    mActivity.getResponse(response.toString(), requestId);
            }

        });
    }

    private void setDataToRecyclerview(String response) {
        try {
            final RemarkList userData;
            Gson gson = new Gson();
            userData = gson.fromJson(response, RemarkList.class);
            try {

                RemarksAdapter mAdapterDate = new RemarksAdapter(userData.getResult(), getActivity(), this);
                RecyclerView.LayoutManager mLayoutManagerDate = new LinearLayoutManager(getActivity());
                rvRemarks.setLayoutManager(mLayoutManagerDate);
                rvRemarks.setItemAnimator(new DefaultItemAnimator());
                rvRemarks.setAdapter(mAdapterDate);


            } catch (Exception e) {


                e.printStackTrace();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.isChecked = isChecked;
        if (isChecked)
            rvRemarks.setVisibility(View.INVISIBLE);
        else
            rvRemarks.setVisibility(View.VISIBLE);
    }

    @Override
    public void success(Response response) {
        AppDatabase mDatabase = AppDatabase.getInstance(getContext());
        new Thread(() -> {
            if (isChecked)
                mDatabase.mAddressDao().updatePaymentStatus(mOrderId, 1);
            else {
                mDatabase.mAddressDao().updatePaymentStatus(mOrderId, 0);
            }
        }).start();
        getDialog().dismiss();
        getFragmentManager().popBackStack();
    }

    @Override
    public void failed(Response failedResonse) {
        Snackbar.make(getView(), failedResonse.getMessage(), 1500).show();
    }

    @Override
    public void error(BaseError errorMessage) {
        Snackbar.make(getView(), errorMessage.getMessage(), 1500).show();
    }

    @Override
    public void onClickedRemarkItem(List<Remarks> timelist) {
        Log.d("Success-", "JSON:" + "Inside onClickedItem :" + timelist.toString());
        checkedRemarksArraylist = new ArrayList<>();
        for (Remarks time : timelist) {
            if (time.getValue() == 1)

                checkedRemarksArraylist.add(time);
        }
    }
}