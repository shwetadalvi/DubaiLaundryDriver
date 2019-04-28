package com.dldriver.driver;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dldriver.driver.models.OfferAndVatModel;
import com.dldriver.driver.models.categorydto;
import com.dldriver.driver.models.data1;
import com.dldriver.driver.ui.editOrder.HeadingView;
import com.dldriver.driver.ui.editOrder.InfoView;
import com.dldriver.driver.utils.ApiConstants;
import com.dldriver.driver.utils.PrefUtils;
import com.dldriver.driver.utils.api.ApiCalls;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import static com.dldriver.driver.utils.Constants.DEFAULT_PREFERENCE;
import static com.dldriver.driver.utils.Constants.LOGIN_PREFERENCE_DRVER_ID;


public class CategoryListActivity extends BaseActivity {

    private Context mContext;
    private ExpandablePlaceHolderView mExpandableView;
    private ExpandablePlaceHolderView.OnScrollListener mOnScrollListener;
    private boolean mIsLoadingMore = false;
    private boolean mNoMoreToLoad = false;
    ApiCalls apiCalls;
    SimpleArcDialog dialog;
    public static final int CATEGORIES = 10;
    private TextView title;
    private ConstraintLayout bottomLayout;

    public TextView cartcount, cartamount;
    ArrayList<data1.Detail.Item> dry;
    ArrayList<data1.Detail.Item> wash;
    ArrayList<data1.Detail.Item> iron;
    ArrayList<data1.Detail.Item> drytmp;
    ArrayList<data1.Detail.Item> washtmp;
    ArrayList<data1.Detail.Item> irontmp;
    private double totalamount;
    int driverId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

       driverId = Integer.parseInt(new PrefUtils(this).getStringPreference(DEFAULT_PREFERENCE, LOGIN_PREFERENCE_DRVER_ID));

        apiCalls = new ApiCalls();
        dialog = new SimpleArcDialog(this);

        getOfferAndVatData();

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bottomLayout = findViewById(R.id.bottomLayout_edit);

        dry = Almoski.getInst().getDrycleanList1();
        wash = Almoski.getInst().getWashList1();
        iron = Almoski.getInst().getIronList1();

        drytmp = Almoski.getInst().getDrycleanList1temp();
        washtmp = Almoski.getInst().getWashList1temp();
        irontmp = Almoski.getInst().getIronList1temp();

        if (null != dry || null != wash || null != iron) {
            // totalamount= checkCart();
            // editOrder();
            bottomLayout.setVisibility(View.VISIBLE);

            // setDatas();
        }


        bottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalamount= checkCart();
                editOrder();




                // for(int i=0;i<Almoski.getInst().get)

                //   if(Almoski.getInst().isOffer()){
              /*              String splitAmount[]=cartamount.getText().toString().split("AED");



                            Double amount= Double.valueOf((splitAmount[1]));
                            double vat=(amount*0.05);
                            double subtotal= amount+vat;

                            double discount=(subtotal*0.3);
                            double discountAmount=subtotal-(subtotal*0.3);



                            if(discountAmount<80){


                                new SweetAlertDialog(CategoryListActivity.this, SweetAlertDialog.WARNING_TYPE)

                                        .setContentText("Minimum Order Amount Must be AED80")
                                        .show();


                            }  */

                     /*   }else {
                            String splitAmount[]=cartamount.getText().toString().split("AED");



                            Double amount= Double.valueOf((splitAmount[1]));


                            if(amount<40){
                                new SweetAlertDialog(CategoryListActivity.this, SweetAlertDialog.WARNING_TYPE)

                                        .setContentText("Minimum Order Amount Must be AED45")
                                        .show();


                            }else{
                                Intent intent = new Intent(CategoryListActivity.this, OrderDetailsActivity.class);

                                startActivity(intent);
                            }

                        }  */




            }
        });


        getCategoryList();
        mContext = this.getApplicationContext();
        mExpandableView = (ExpandablePlaceHolderView) findViewById(R.id.expandableView);








//        setLoadMoreListener(mExpandableView);
    }

    private void setDatas() {


    }

    private void editOrder() {

        //      System.out.println(Almoski.getInst().getDrycleanList1temp().size());
//        System.out.println(Almoski.getInst().getWashList1temp().size());
        //    System.out.println(Almoski.getInst().getIronList1temp().size());


        ArrayList<data1.Detail.Item> dry = Almoski.getInst().getDrycleanList1temp();
        ArrayList<data1.Detail.Item> wash =Almoski.getInst().getWashList1temp();
        ArrayList<data1.Detail.Item> iron = Almoski.getInst().getIronList1temp();
        if (null != wash || null != dry || null != iron) {

            try {

                JSONArray mainArray = new JSONArray();
                JSONObject object = new JSONObject();


                object.put("email", driverId);
                object.put("orderId", Almoski.getInst().getSelecterOrderId());
                object.put("totalamount", totalamount);
                object.put("remarks", Almoski.getInst().getSelectedOrder().getAdditional());



                //object.put("remarksremarks", binding.edtNote.getText().toString());


                JSONArray jsonArray3 = new JSONArray();

                if (null != drytmp) {

                    for (int i = 0; i < drytmp.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", drytmp.get(i).getItemId());
                        object3.put("ServiceId", 1);
                        object3.put("Qty", drytmp.get(i).getItemcount());
                        object3.put("Price", drytmp.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", drytmp.get(i).getItemName());

                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }
                if (null != wash) {

                    for (int i = 0; i < wash.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", wash.get(i).getItemId());
                        object3.put("ServiceId", 2);
                        object3.put("Qty", wash.get(i).getItemcount());
                        object3.put("Price", wash.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", wash.get(i).getItemName());


                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }
                if (null != iron) {

                    for (int i = 0; i < iron.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", iron.get(i).getItemId());
                        object3.put("ServiceId", 3);
                        object3.put("Qty", iron.get(i).getItemcount());
                        object3.put("Price", iron.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", iron.get(i).getItemName());


                        jsonArray3.put(object3);

                    }

                    //  object.put("Item",jsonArray3);
                }


                object.put("orders", jsonArray3);

                String Data = object.toString();



                StringEntity entity = null;
                final SimpleArcDialog dialog = new SimpleArcDialog(CategoryListActivity.this);
                try {
                    entity = new StringEntity(Data.toString());
                    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    dialog.show();
                } catch (Exception e) {
//Exception
                }

                String url = ApiConstants.BaseUrl + ApiConstants.editorderUrl;

                new AsyncHttpClient().post(null, url, entity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        try {
                            dialog.dismiss();
                            String object = new String(responseBody);
                            JSONObject jsonObject = new JSONObject(object);
                            String result = jsonObject.getString("result");

                            if (result.equals("Data Updated")) {


                              //  Almoski.getInst().setUpdate(true);
                               /* Intent go=new Intent(CategoryListActivity.this, OrderActiivity.class);
                                go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(go);*/
                               finish();
                          /*      new SweetAlertDialog(CategoryListActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                        .setTitleText("Success")
                                        .setContentText("Success")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {


                                                sDialog.dismissWithAnimation();

                                                Almosky.getInst().setIronList(null);
                                                Almosky.getInst().setCartcount(0);
                                                Almosky.getInst().setWashList(null);
                                                Almosky.getInst().setDrycleanList(null);
                                                Almosky.getInst().setCartamount(0);
                                                // Almosky.getInst().setServiceId(0);
                                                Almosky.getInst().setAddress("");
                                                Almosky.getInst().setNisabClub(false);

                                                Intent go = new Intent(OrderConfirmationActivity.this, TabHostActivity.class);
                                                go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(go);


                                            }
                                        })
                                        .show(); */
                                // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            //   Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();

                //  Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();

            }
        }

    }


    private void getOfferAndVatData() {

        RequestParams params = new RequestParams();

        // params.put("email",  appPrefes.getData(PrefConstants.email));

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.general;
        apiCalls.callApiGet(CategoryListActivity.this, dialog, url, 77);


    }
    public static String PerfectDecimal(String str, int MAX_BEFORE_POINT, int MAX_DECIMAL){
        if(str.charAt(0) == '.') str = "0"+str;
        int max = str.length();

        String rFinal = "";
        boolean after = false;
        int i = 0, up = 0, decimal = 0; char t;
        while(i < max){
            t = str.charAt(i);
            if(t != '.' && after == false){
                up++;
                if(up > MAX_BEFORE_POINT) return rFinal;
            }else if(t == '.'){
                after = true;
            }else{
                decimal++;
                if(decimal > MAX_DECIMAL)
                    return rFinal;
            }
            rFinal = rFinal + t;
            i++;
        }return rFinal;
    }

    private double checkCart() {

        try{
            double drycount = 0, dryamount = 0, washcount = 0, washamount = 0, ironcount = 0, ironamount = 0;


            if (null != drytmp) {
                for (int i = 0; i < drytmp.size(); i++) {
                    drycount = drycount + drytmp.get(i).getItemcount();
                    dryamount = dryamount + Double.parseDouble(drytmp.get(i).getTotal());
                }

            }
            if (null != washtmp) {
                for (int i = 0; i < washtmp.size(); i++) {

                    washcount = washcount + washtmp.get(i).getItemcount();
                    washamount = washamount + Double.parseDouble(washtmp.get(i).getTotal());
                }

            }
            if (null != irontmp) {

                for (int i = 0; i < irontmp.size(); i++) {
                    ironcount = ironcount + irontmp.get(i).getItemcount();
                    ironamount = ironamount + Double.parseDouble(irontmp.get(i).getTotal());
                }

            }

            double totalcount = drycount + washcount + ironcount;
            totalamount = dryamount + washamount + ironamount;



     /*   if (totalamount > 0 && totalcount > 0) {

            bottomLayout.setVisibility(View.VISIBLE);
            cartcount = (TextView) findViewById(R.id.tv_cartcount);
            cartamount = (TextView) findViewById(R.id.tv_cartamount);
            cartcount.setText("Your Basket(" + String.valueOf(totalcount).toString() + ")");
            cartamount.setText("AED" + String.valueOf(totalamount).toString());

        } else {

        } */
/*
        int drycount = 0, dryamount = 0, washcount = 0, washamount = 0, ironcount = 0, ironamount = 0;


        if (null != dry) {
            for (int i = 0; i < dry.size(); i++) {
                drycount = drycount + dry.get(i).getItemcount();
                dryamount = dryamount + Integer.parseInt(dry.get(i).getTotal());
            }

        }
        if (null != wash) {
            for (int i = 0; i < wash.size(); i++) {

                washcount = washcount + wash.get(i).getItemcount();
                washamount = washamount + Integer.parseInt(wash.get(i).getTotal());
            }

        }
        if (null != iron) {

            for (int i = 0; i < iron.size(); i++) {
                ironcount = ironcount + iron.get(i).getItemcount();
                ironamount = ironamount + Integer.parseInt(iron.get(i).getTotal());
            }

        }

        int totalcount = drycount + washcount + ironcount;
        int totalamount = dryamount + washamount + ironamount;

        if (totalamount > 0 && totalcount > 0) {

            bottomLayout.setVisibility(View.VISIBLE);
            cartcount = (TextView) findViewById(R.id.tv_cartcount);
            cartamount = (TextView) findViewById(R.id.tv_cartamount);
            cartcount.setText("Your Basket(" + String.valueOf(totalcount).toString() + ")");
            cartamount.setText("AED" + String.valueOf(totalamount).toString());

        } else {

        }

*/
            return totalcount;
        }catch (Exception e){
            e.printStackTrace();
        }
        return totalamount;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != drytmp || null != washtmp || null != irontmp) {
            //  totalamount=checkCart();
            //  editOrder();
        }
    }

    private void setData() {


    }

    private void getCategoryList() {

        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        // params.put("LoginEmail", "admin@gmail.com");

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.categoryUrl;
        apiCalls.callApiPost(CategoryListActivity.this, params, dialog, url, CATEGORIES);
    }




    @Override
    public void getResponse(String response, int requestId) {

        if (requestId == 77) {

            try {

                final OfferAndVatModel offerData;
                Gson gson = new Gson();
                offerData = gson.fromJson(response, OfferAndVatModel.class);

                if(offerData.getStatus()){
                    Almoski.getInst().setNasabRate(offerData.getResult().get(0).getNASABDISCPER());
                    Almoski.getInst().setVatRate(offerData.getResult().get(0).getVATPer());
                }


            } catch (Exception e) {

            }


        }else{
            try {

                Gson gson = new Gson();
                final categorydto catList = gson.fromJson(response, categorydto.class);

                if (0 != catList.getDetail().size()) {
                    for (int i = 0; i < catList.getDetail().size(); i++) {


                        mExpandableView.addView(new HeadingView(mContext, catList.getDetail().get(i).getCategoryName(),catList.getDetail().get(i).getCategoryIcons(),1));
                        for (int j = 0; j < catList.getDetail().get(i).getItems().size(); j++) {
                            mExpandableView.addView(new InfoView(mContext, catList.getDetail().get(i).getItems().get(j)));
                        }
                    }

                }

            } catch (Exception e) {

            }

        }

    }
/*
    private void setLoadMoreListener(ExpandablePlaceHolderView expandableView) {
        mOnScrollListener =
                new PlaceHolderView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        ExpandablePlaceHolderView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                            int totalItemCount = linearLayoutManager.getItemCount();
                            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (!mIsLoadingMore
                                    && !mNoMoreToLoad
                                    && totalItemCount > 0
                                    && totalItemCount == lastVisibleItem + 1) {
                                mIsLoadingMore = true;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {

                                        // do api call to fetch data

                                        // example of loading from file:
                                        for (Feed feed : Utils.loadFeeds(getApplicationContext())) {
                                            mExpandableView.addView(new HeadingView(mContext, feed.getHeading()));
                                            for (Info info : feed.getInfoList()) {
                                                mExpandableView.addView(new InfoView(mContext, info));
                                            }
                                        }
                                        mIsLoadingMore = false;
                                    }
                                });
                            }
                        }
                    }
                };
        expandableView.addOnScrollListener(mOnScrollListener);
    }  */
}