package com.dldriver.driver.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dldriver.driver.Almoski;
import com.dldriver.driver.BaseActivity;
import com.dldriver.driver.NetworkResponses;
import com.dldriver.driver.R;
import com.dldriver.driver.databinding.ActivityOrderConfirmationBinding;
import com.dldriver.driver.interactors.OrderInteracterImpl;
import com.dldriver.driver.models.BaseError;
import com.dldriver.driver.models.CategoryItems;
import com.dldriver.driver.models.NewOrderItem;
import com.dldriver.driver.models.Response;
import com.dldriver.driver.ui.adapter.DryCleanRecyclerViewAdapter;
import com.dldriver.driver.ui.adapter.IroningRecyclerViewAdapter;
import com.dldriver.driver.ui.adapter.WashIronRecyclerViewAdapter;
import com.dldriver.driver.utils.ApiConstants;
import com.dldriver.driver.utils.PrefUtils;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import static com.dldriver.driver.utils.Constants.DEFAULT_PREFERENCE;
import static com.dldriver.driver.utils.Constants.LOGIN_PREFERENCE_DRVER_ID;

public class OrderConfirmationActivity extends BaseActivity implements NetworkResponses<Response> {


    private ActivityOrderConfirmationBinding mBinding;
private  int order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_confirmation);
        mBinding.textAddress.setText(Almoski.getInst().getAddress());
        mBinding.textPickUpDate.setText(Almoski.getInst().getPickupDate());
        mBinding.textPickUpTime.setText(Almoski.getInst().getPickupTime());
        mBinding.textDeliveryDate.setText(Almoski.getInst().getDelDate());
        mBinding.textDeliveryTime.setText(Almoski.getInst().getDelTime());
        mBinding.btnPlaceOrder.setOnClickListener(v -> onClickedPlaceOrder());
        setUpRecyclers();
        calculateTotal();

order_id = Almoski.getInst().getOrderId();
    }


    private void onClickedPlaceOrder() {
        NewOrderItem newOrderItem = new NewOrderItem();
        newOrderItem.setEmail(Almoski.getInst().getEmail());
        newOrderItem.setPickdate(Almoski.getInst().getPickupDate());
        newOrderItem.setPicktime(Almoski.getInst().getPickupTime());
        newOrderItem.setDeldate(Almoski.getInst().getDelDate());
        newOrderItem.setDeltime(Almoski.getInst().getDelTime());
        newOrderItem.setItemAmount(finalOrderTotal);
        newOrderItem.setRemarks("EASY ORDER ID : " + Almoski.getInst().getOrderId());
        newOrderItem.setAddressId(Almoski.getInst().getAddressId());
        newOrderItem.setOrderId(Almoski.getInst().getOrderId());
        newOrderItem.setTotalamount(orderTotal);
        newOrderItem.setNasabDiscountAmount(nasabDiscount);
        newOrderItem.setVatAmount(vat);
        List<NewOrderItem.Order> orderList = parseOrders();
        newOrderItem.setOrders(orderList);
        OrderInteracterImpl orderInteracter = new OrderInteracterImpl();


                if(Almoski.getInst().getDeliveryType().equalsIgnoreCase("normal")){
                    postEditedOrder();
                }
                else{

                    if(Almoski.getInst().isOffer()){
                        String splitAmount[]=mBinding.subtotalPrice.getText().toString().split("AED");
                        Double amount=Double.valueOf((splitAmount[1]));
                        double vat=(amount*0.05);
                        double subtotal= amount+vat;

                        double discount=(subtotal*0.3);
                        double discountAmount=subtotal-(subtotal*0.3);



                        if(amount<80){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
                            builder1.setMessage("Minimum Order Amount Must be AED80");
                            builder1.setCancelable(false);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();


                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();


                        }else {

                                            postEditedOrder();

                        }

                    }else{


                        String splitAmount[]=mBinding.subtotalPrice.getText().toString().split("AED");
                        Log.e("Order ", "order confirm subtotalPrice " + mBinding.subtotalPrice.getText().toString());
                        Log.e("Order ", "order confirm splitAmount " + splitAmount[0]);
                        Double amount=Double.valueOf((splitAmount[1]));
                        double vat=(amount*0.05);
                        double subtotal= amount+vat;

                        double discount=(subtotal*0.3);
                        double discountAmount=subtotal-(subtotal*0.3);



                        if(amount<45){

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
                            builder1.setMessage("Minimum Order Amount Must be AED45");
                            builder1.setCancelable(false);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();


                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();



                        }else {

                                            postEditedOrder();

                        }
                    }

                }

        // orderInteracter.makeNewOrder(newOrderItem, this);
    }

    private void postEditedOrder() {

        List<CategoryItems.Detail.Item> dry = Almoski.getInst().getDrycleanList();
        List<CategoryItems.Detail.Item> wash = Almoski.getInst().getWashList();
        List<CategoryItems.Detail.Item> iron = Almoski.getInst().getIronList();
        int del_type = 1;
        if (Almoski.getInst().getDeliveryType().equalsIgnoreCase("normal"))
            del_type = 1;
        else
            del_type = 2;

        int driverId = driverId = Integer.parseInt(new PrefUtils(this).getStringPreference(DEFAULT_PREFERENCE, LOGIN_PREFERENCE_DRVER_ID));
        if (null != wash || null != dry || null != iron) {

            try {

                JSONArray mainArray = new JSONArray();
                JSONObject object = new JSONObject();


                object.put("email", driverId);
                object.put("orderId", Almoski.getInst().getOrderId());
                object.put("pickdate", Almoski.getInst().getPickupDate());
                object.put("picktime", Almoski.getInst().getPickupTime());
                 object.put("deldate", Almoski.getInst().getDelDate());
                // object.put("delDate", "2014-12-5");
                object.put("deltime", Almoski.getInst().getDelTime());
                //object.put("delTime", "6:30:00");
                object.put("totalamount", orderTotal);

                object.put("addressId", Almoski.getInst().getAddressId());
                object.put("deliveryType", del_type);
                object.put("itemAmount", finalOrderTotal);
                object.put("nasabDiscountAmount", nasabDiscount);
                object.put("vatAmount", vat);
                //object.put("remarksremarks", binding.edtNote.getText().toString());


                JSONArray jsonArray3 = new JSONArray();
/*                Log.e("Order ", "order confirm response11 " + Almoski.getInst().getServiceListt().size());
if(Almoski.getInst().getServiceListt() != null){
    for (int i = 0; i < Almoski.getInst().getServiceListt().size(); i++) {
int service_id = 1;
        if(Almoski.getInst().getServiceListt().get(i).getServiceName().equalsIgnoreCase("Dry Clean"))
            service_id = 1;
        if(Almoski.getInst().getServiceListt().get(i).getServiceName().equalsIgnoreCase("Wash + Iron"))
            service_id = 2;
        if(Almoski.getInst().getServiceListt().get(i).getServiceName().equalsIgnoreCase("Ironing"))
            service_id = 3;
        for(int j = 0;j < Almoski.getInst().getServiceListt().get(i).getItems().size();j++) {
            JSONObject object3 = new JSONObject();
            object3.put("ItemId", Almoski.getInst().getServiceListt().get(i).getItems().get(j).getOrderItems().getItemName());
            object3.put("ServiceId", service_id);
            object3.put("Qty", Almoski.getInst().getServiceListt().get(i).getItems().get(j).getOrderItems().getQuantity());
            object3.put("Price", Almoski.getInst().getServiceListt().get(i).getItems().get(j).getOrderItems().getPrice());
            //  object3.put("slno", "1");
            object3.put("Item_Name", Almoski.getInst().getServiceListt().get(i).getItems().get(j).getOrderItems().getItemName());

            jsonArray3.put(object3);
        }

    }
}*/
                if (null != dry) {

                    for (int i = 0; i < dry.size(); i++) {

                        JSONObject object3 = new JSONObject();
                        object3.put("ItemId", dry.get(i).getItemId());
                        object3.put("ServiceId", 1);
                        object3.put("Qty", dry.get(i).getItemcount());
                        object3.put("Price", dry.get(i).getAmount());
                        //  object3.put("slno", "1");
                        object3.put("Item_Name", dry.get(i).getItemName());

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
                Log.e("Order ", "order confirm response " + Data);
                StringEntity entity = null;
                final SimpleArcDialog dialog = new SimpleArcDialog(OrderConfirmationActivity.this);
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

                            Log.e("Order ", "order confirm response " + result);
                            if (result.equalsIgnoreCase("Data Updated")) {
                                Toast.makeText(getApplicationContext(), "Order Updated", Toast.LENGTH_SHORT).show();


                                Almoski.getInst().setIronList(null);
                                Almoski.getInst().setCartcount(0);
                                Almoski.getInst().setWashList(null);
                                Almoski.getInst().setDrycleanList(null);
                                Almoski.getInst().setCartamount(0);
                                // Almosky.getInst().setServiceId(0);
                                Almoski.getInst().setAddress("");
                                Almoski.getInst().setNisabClub(false);

                              /*  Intent go = new Intent(OrderConfirmationActivity.this, NextOrderActivity.class);
                                go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(go);*/
                                nextOrder();

                               /* new SweetAlertDialog(OrderConfirmationActivity.this, SweetAlertDialog.NORMAL_TYPE)
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

                                                Intent go = new Intent(OrderConfirmationActivity.this, NextOrderActivity.class);
                                                go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(go);


                                            }
                                        })
                                        .show();*/
                                // Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(), "msg", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void nextOrder() {
Intent in = new Intent(OrderConfirmationActivity.this,OrderActiivity.class);
in.putExtra("orderId",String.valueOf(order_id));
startActivity(in);

      //  startActivity(new Intent(this, OrderActiivity.class).putExtra("orderId", Almoski.getInst().getOrderId()));


    }


    private List<NewOrderItem.Order> parseOrders() {
        List<NewOrderItem.Order> orderList = new ArrayList<>();
        if (Almoski.getInst().getIronList() != null) {//3
            for (int i = 0; i < Almoski.getInst().getIronList().size(); i++) {
                NewOrderItem.Order order = new NewOrderItem().new Order();
                order.setItemId(Almoski.getInst().getIronList().get(i).getItemId());
                order.setServiceId(3);
                order.setQty(Almoski.getInst().getIronList().get(i).getItemcount());
                order.setItemName(Almoski.getInst().getIronList().get(i).getItemName());
                order.setPrice(Float.valueOf(Almoski.getInst().getIronList().get(i).getAmount()));
                orderList.add(order);

            }
        }
        if (Almoski.getInst().getDrycleanList() != null) {//1
            for (int i = 0; i < Almoski.getInst().getDrycleanList().size(); i++) {
                NewOrderItem.Order order = new NewOrderItem().new Order();
                order.setItemId(Almoski.getInst().getDrycleanList().get(i).getItemId());
                order.setServiceId(1);
                order.setQty(Almoski.getInst().getDrycleanList().get(i).getItemcount());
                order.setItemName(Almoski.getInst().getDrycleanList().get(i).getItemName());
                order.setPrice(Float.valueOf(Almoski.getInst().getDrycleanList().get(i).getAmount()));
                orderList.add(order);
            }
        }
        if (Almoski.getInst().getWashList() != null) {//2
            for (int i = 0; i < Almoski.getInst().getWashList().size(); i++) {
                NewOrderItem.Order order = new NewOrderItem().new Order();
                order.setItemId(Almoski.getInst().getWashList().get(i).getItemId());
                order.setServiceId(2);
                order.setQty(Almoski.getInst().getWashList().get(i).getItemcount());
                order.setItemName(Almoski.getInst().getWashList().get(i).getItemName());
                order.setPrice(Float.valueOf(Almoski.getInst().getWashList().get(i).getAmount()));
                orderList.add(order);
            }
        }
        return orderList;
    }


    private void setUpRecyclers() {

        if (Almoski.getInst().getIronList() != null) {

            IroningRecyclerViewAdapter ironingRecyclerViewAdapter = new IroningRecyclerViewAdapter(OrderConfirmationActivity.this, Almoski.getInst().getIronList(), this);
            mBinding.ironingRecyclerView.setNestedScrollingEnabled(false);
            mBinding.ironingRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mBinding.ironingRecyclerView.setAdapter(ironingRecyclerViewAdapter);

        } else {
            mBinding.ironingLayout.setVisibility(View.GONE);

        }
        if (Almoski.getInst().getWashList() != null) {

            WashIronRecyclerViewAdapter washIronRecyclerViewAdapter = new WashIronRecyclerViewAdapter(OrderConfirmationActivity.this, Almoski.getInst().getWashList(), this);
            mBinding.washIronRecyclerView.setNestedScrollingEnabled(false);
            mBinding.washIronRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mBinding.washIronRecyclerView.setAdapter(washIronRecyclerViewAdapter);

        } else {
            mBinding.washIronHeaderLayout.setVisibility(View.GONE);

        }
        if (Almoski.getInst().getDrycleanList() != null) {

            DryCleanRecyclerViewAdapter adapter = new DryCleanRecyclerViewAdapter(OrderConfirmationActivity.this, Almoski.getInst().getDrycleanList(), this);
            mBinding.dryCleanRecyclerView.setNestedScrollingEnabled(false);
            mBinding.dryCleanRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mBinding.dryCleanRecyclerView.setAdapter(adapter);

        } else {
            mBinding.dryCleanHeaderLayout.setVisibility(View.GONE);

        }
    }

    float orderTotal = 0, vat = 0, finalOrderTotal = 0, nasabDiscount = 0;

    private void calculateTotal() {

        if (Almoski.getInst().getIronList() != null) {
            for (int i = 0; i < Almoski.getInst().getIronList().size(); i++) {
                orderTotal = orderTotal + Float.parseFloat(Almoski.getInst().getIronList().get(i).getTotal());
            }
        }
        if (Almoski.getInst().getDrycleanList() != null) {
            for (int i = 0; i < Almoski.getInst().getDrycleanList().size(); i++) {
                orderTotal = orderTotal + Float.parseFloat(Almoski.getInst().getDrycleanList().get(i).getTotal());
            }
        }
        if (Almoski.getInst().getWashList() != null) {
            for (int i = 0; i < Almoski.getInst().getWashList().size(); i++) {
                orderTotal = orderTotal + Float.parseFloat(Almoski.getInst().getWashList().get(i).getTotal());
            }
        }
        vat = calculateVat(orderTotal);
        finalOrderTotal = vat + orderTotal;
        mBinding.totalPrice.setText("AED" + String.format("%.2f", orderTotal));
        mBinding.vattotalPrice.setText("AED" + String.format("%.2f", vat));


        if (Almoski.getInst().isNisabClub()) {
            nasabDiscount = calculateNasabDiscount(finalOrderTotal);
            finalOrderTotal = finalOrderTotal - nasabDiscount;
            mBinding.tvDiscountNisab.setText("AED" + String.format("%.2f", nasabDiscount));
        }
        mBinding.subtotalPrice.setText("AED" + String.format("%.2f", finalOrderTotal));
    }

    private float calculateNasabDiscount(float orderTotal) {
        return orderTotal * 30 / 100;
    }

    private float calculateVat(float orderTotal) {
        return orderTotal * 5 / 100;
    }

    public void updateTotal() {

        float drycount = 0, dryamount = 0, washcount = 0, washamount = 0, ironcount = 0, ironamount = 0;
        List<CategoryItems.Detail.Item> dry = Almoski.getInst().getDrycleanList();
        List<CategoryItems.Detail.Item> wash = Almoski.getInst().getWashList();
        List<CategoryItems.Detail.Item> iron = Almoski.getInst().getIronList();


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

        float totalcount = drycount + washcount + ironcount;
        float totalamount = dryamount + washamount + ironamount;
        double vat = (totalamount * 0.05);
        double subtotal = Double.valueOf(totalamount) + vat;

        double discount = (subtotal * 0.3);
        double discountAmount = subtotal - (subtotal * 0.3);

        if (totalcount == 0) {

            mBinding.totalPrice.setText("0" + "AED");
            //mBinding.totalCount.setText("0");
            mBinding.vattotalPrice.setVisibility(View.INVISIBLE);

            mBinding.subtotalPrice.setText("0" + "AED");


        } else if (totalamount > 0 && totalcount > 0) {
            mBinding.totalPrice.setText(String.valueOf(totalamount).toString() + "AED");
            // mBinding.totalCount.setText(String.valueOf(totalcount));
            mBinding.vattotalPrice.setText(PerfectDecimal(String.valueOf((vat)), 2, 2) + "AED");

            if (Almoski.getInst().isNisabClub()) {

                mBinding.lytDiscount.setVisibility(View.VISIBLE);
                mBinding.tvDiscountNisab.setText(PerfectDecimal(String.valueOf(discount).toString(), 2, 2));
                mBinding.subtotalPrice.setText(PerfectDecimal(String.valueOf(discountAmount).toString(), 2, 2) + "AED");

            } else {
                mBinding.subtotalPrice.setText(String.valueOf(subtotal).toString() + "AED");
            }


        } else {

        }

    }

    public static String PerfectDecimal(String str, int MAX_BEFORE_POINT, int MAX_DECIMAL) {
        if (str.charAt(0) == '.') str = "0" + str;
        int max = str.length();

        String rFinal = "";
        boolean after = false;
        int i = 0, up = 0, decimal = 0;
        char t;
        while (i < max) {
            t = str.charAt(i);
            if (t != '.' && after == false) {
                up++;
                if (up > MAX_BEFORE_POINT) return rFinal;
            } else if (t == '.') {
                after = true;
            } else {
                decimal++;
                if (decimal > MAX_DECIMAL)
                    return rFinal;
            }
            rFinal = rFinal + t;
            i++;
        }
        return rFinal;
    }


    @Override
    public void success(Response response) {
        showSnackBar(getWindow().getDecorView(), "order placed", 1000);
    }

    @Override
    public void failed(Response failedResonse) {
        Log.d("Sdsad", "failed: ");
    }

    @Override
    public void error(BaseError baseError) {
        Log.d("Sdsad", "error: ");
    }
}
