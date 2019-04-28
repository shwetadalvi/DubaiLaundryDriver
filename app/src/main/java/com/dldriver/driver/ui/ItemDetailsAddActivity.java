package com.dldriver.driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dldriver.driver.Almoski;
import com.dldriver.driver.BaseActivity;
import com.dldriver.driver.R;
import com.dldriver.driver.databinding.ActivityItemDetailsAddBinding;
import com.dldriver.driver.models.CategoryItems;
import com.dldriver.driver.models.PriceDetails;
import com.dldriver.driver.models.priceListdto;
import com.dldriver.driver.utils.ApiConstants;
import com.dldriver.driver.utils.api.ApiCalls;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;

public class ItemDetailsAddActivity extends BaseActivity {

    private ActivityItemDetailsAddBinding mBinding;
    private int mCategoryId;
    private String mCategoryName;
    private int mItemId;
    private String mItemName;
    private boolean isAddedToBackStack;
    ArrayList<priceListdto.Detail> priceDetails;
    private ApiCalls apiCalls;
    SimpleArcDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_details_add);
        apiCalls=new ApiCalls();
        dialog=new SimpleArcDialog(this);
        initViews();
        mBinding.dryCleanPlus.setOnClickListener(v -> onDryCleanPlusClicked());
        mBinding.dryCleanMinus.setOnClickListener(v -> onDryCleanMinusClicked());
        mBinding.washIronPlus.setOnClickListener(v -> onWashIronPlusClicked());
        mBinding.washIronMinus.setOnClickListener(v -> onWashIronMinusClicked());
        mBinding.ironingPlus.setOnClickListener(v -> onIroningPlusClicked());
        mBinding.ironingMinus.setOnClickListener(v -> onIroningMinusClicked());
        mBinding.addToBasketId.setOnClickListener(v -> onAddToBasketClicked());
    }

    private void initViews() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mCategoryId = (int) intent.getExtras().get("catId");
            mCategoryName = (String) intent.getExtras().get("catname");
            mItemId = (int) intent.getExtras().get("itemId");
            mItemName = (String) intent.getExtras().get("itemname");
            String imageUrl = (String) intent.getExtras().get("url");
            Glide.with(this).load(imageUrl).into(mBinding.ivDetail);
        }
       // showPrice(mItemId);
       getPriceList();
        hasSameItemExistInList();
    }
    private void getPriceList() {
        // if (!validate(view.getResources())) return;
        RequestParams params = new RequestParams();

        // params.put("LoginEmail", "admin@gmail.com");

        // params.put(ApiConstants.uid, appPrefes.getData(PrefConstants.uid));
        // params.put(ApiConstants.uid, 1);
        //  params.put(ApiConstants.status, "Pending");


        String url = ApiConstants.getPriceListUrl;
        apiCalls.callApiPost(ItemDetailsAddActivity.this, params, dialog, url, 13);

    }
 @Override
    public void getResponse(String response, int requestId) {

        try {
            Gson gson = new Gson();

            final priceListdto priceList = gson.fromJson(response, priceListdto.class);
            priceListdto dto=new priceListdto();
            priceListdto.Detail detaildto=dto. new Detail();

            priceDetails=priceList.getDetail();

            Almoski.getInst().setItempriceList(priceDetails);


            if(getAmount(mItemId,1)!=0){
                mBinding.tvDryPrice.setText(String.valueOf(getAmount(mItemId,1)));
            }else{
                mBinding.dryCleanPlus.setEnabled(false);
                mBinding.dryCleanMinus.setEnabled(false);
            }
            if(getAmount(mItemId,2)!=0){
                mBinding.tvWashPrice.setText(String.valueOf(getAmount(mItemId,2)));
            }else{
                mBinding.washIronPlus.setEnabled(false);
                mBinding.washIronMinus.setEnabled(false);
            }

            if(getAmount(mItemId,3)!=0){
                mBinding.tvIronPrice.setText(String.valueOf(getAmount(mItemId,3)));
            }else{
                mBinding.ironingPlus.setEnabled(false);
                mBinding.ironingMinus.setEnabled(false);
            }



        }catch (Exception e){

        }
    }
    private void showPrice(int itemId) {
        for (PriceDetails.Detail detail : Almoski.getInst().getIroningpriceList().getDetail()) {
            for (PriceDetails.Detail.Item item : detail.getItems()) {
                if (item.getItemId() == itemId) {
                    if (Almoski.getInst().getDeliveryType().equals(item.getDeliveryType())) {
                        getPrice(item, detail.getServiceId());
                    } else {
                        getPrice(item, detail.getServiceId());
                    }
                }
            }
        }
    }
    private double getAmount(int itemId,int serviceId) {

        System.out.println("Item"+itemId+"Service"+serviceId);


        double amount=0;
        for(int i=0;i<Almoski.getInst().getItempriceList().size();i++){
            if(serviceId==Almoski.getInst().getItempriceList().get(i).getServiceId()){
                ArrayList<priceListdto.Detail.Item> curntlist=Almoski.getInst().getItempriceList().get(i).getItems();

                String type=Almoski.getInst().getDeliveryType();
                System.out.println("find type"+type);

                for(int j=0; j<curntlist.size(); j++){
                    // if(Almosky.getInst().getDeliveryType().equals("normal")) {
                    if(type.equalsIgnoreCase("normal")) {

                        String ctype = curntlist.get(j).getDeliveryType();
                        //  System.out.println("find curent" + ctype);
                        if (itemId == curntlist.get(j).getItemId() && curntlist.get(j).getDeliveryType().equals("NORMAL")) {
                            amount = Double.parseDouble(curntlist.get(j).getPrice());
                            System.out.println("normalamounc"+amount);

                            return amount;
                        }
                    }

                    //  if(Almosky.getInst().getDeliveryType().equals("fast")){
                    else{
                        String cctype=curntlist.get(j).getDeliveryType();
                        //  System.out.println("find curentfast"+cctype);
                        if(itemId == curntlist.get(j).getItemId() && !curntlist.get(j).getDeliveryType().equals("NORMAL")){
                            amount= Double.parseDouble(curntlist.get(j).getPrice());
                            System.out.println("fastamounc"+amount);

                            return amount;
                        }

                    }





                }



            }



        }

        return amount;
    }
    void getPrice(PriceDetails.Detail.Item item, int serviceId) {
        switch (serviceId) {
            case 1://dryclean
                if (item.getPrice() == 0) {
                    mBinding.dryCleanPlus.setEnabled(false);
                    mBinding.dryCleanMinus.setEnabled(false);
                } else {
                    mBinding.tvDryPrice.setText("" + item.getPrice());
                }
                break;
            case 2://wash+iron
                if (item.getPrice() == 0) {
                    mBinding.washIronMinus.setEnabled(false);
                    mBinding.washIronPlus.setEnabled(false);
                } else {
                    mBinding.tvWashPrice.setText("" + item.getPrice());
                }
                break;
            case 3://iron
                if (item.getPrice() == 0) {
                    mBinding.ironingPlus.setEnabled(false);
                    mBinding.ironingMinus.setEnabled(false);
                } else {
                    mBinding.tvIronPrice.setText("" + item.getPrice());
                }
                break;
        }
    }

    private void onAddToBasketClicked() {
        isAddedToBackStack = true;
        onBackPressed();
    }

    private void onIroningPlusClicked() {
        int count = Integer.parseInt(mBinding.ironingCount.getText().toString());
        count = count + 1;
        mBinding.ironingCount.setText("" + count);
        update(count, "ironing", mItemName, mItemId);
    }

    private void onIroningMinusClicked() {
        int count = Integer.parseInt(mBinding.ironingCount.getText().toString());
        if (count > 0) {
            count = count - 1;
            mBinding.ironingCount.setText("" + count);
            update(count, "ironing", mItemName, mItemId);
        }
    }

    private void onWashIronMinusClicked() {
        int count = Integer.parseInt(mBinding.washIronCount.getText().toString());
        if (count > 0) {
            count = count - 1;
            mBinding.washIronCount.setText("" + count);
            update(count, "washIron", mItemName, mItemId);
        }
    }

    private void onWashIronPlusClicked() {
        int count = Integer.parseInt(mBinding.washIronCount.getText().toString());
        count = count + 1;
        mBinding.washIronCount.setText("" + count);
        update(count, "washIron", mItemName, mItemId);
    }

    private void onDryCleanMinusClicked() {
        int count = Integer.parseInt(mBinding.dryCleanCount.getText().toString());
        if (count > 0) {
            count = count - 1;
            mBinding.dryCleanCount.setText("" + count);
            update(count, "dryClean", mItemName, mItemId);
        }
    }

    private void onDryCleanPlusClicked() {
        int count = Integer.parseInt(mBinding.dryCleanCount.getText().toString());
        count = count + 1;
        mBinding.dryCleanCount.setText("" + count);
        update(count, "dryClean", mItemName, mItemId);
    }

    private void update(int count, String service, String itemName, int itemId) {
        Log.e("Item :" ,"Inside Update :"+service);
        switch (service) {
            case "washIron":
                addItemToWashIronList(count, itemName, itemId);
                break;
            case "dryClean":
                addItemToDryCleanList(count, itemName, itemId);
                break;
            case "ironing":

                addItemToIroningList(count, itemName, itemId);
                break;
        }
    }

    private void addItemToIroningList(int count, String itemName, int itemId) {


        List<CategoryItems.Detail.Item> ironingList = Almoski.getInst().getIronList();

        int isPresent = 0;
        if (ironingList != null) {
            for (int i = 0; i < ironingList.size(); i++) {
                if (ironingList.get(i).getItemId().equals(itemId)) {

                    // drycleanList.get(i).setItemcount(count);
                    Almoski.getInst().getIronList().get(i).setItemcount(count);
                    //int amount= Integer.parseInt(Almosky.getInst().getDrycleanList().get(i).getTotalAmount());
                    float amount = getTotalAmount(Integer.parseInt(mBinding.ironingCount.getText().toString()), Float.parseFloat(mBinding.tvIronPrice.getText().toString()));
                    Almoski.getInst().getIronList().get(i).setTotal(String.format("%.2f", amount));
                    isPresent = 1;
                }
            }
            if (isPresent == 0) {
                ArrayList<CategoryItems.Detail.Item> iroLst = new ArrayList<>();

                CategoryItems categoryItems = new CategoryItems();
                CategoryItems.Detail detailobj = categoryItems.new Detail();
                detailobj.setType("ironing");

                CategoryItems.Detail.Item itemObj = detailobj.new Item();

                itemObj.setItemId(itemId);
                itemObj.setAmount(String.format("%.2f", Float.parseFloat(mBinding.tvIronPrice.getText().toString())));
                itemObj.setItemName(itemName);
                itemObj.setItemcount(count);


                itemObj.setTotal(String.format("%.2f", getTotalAmount(Integer.parseInt(mBinding.ironingCount.getText().toString()), Float.parseFloat(mBinding.tvIronPrice.getText().toString()))));
                iroLst.add(itemObj);

                if (null != ironingList) {
                    ironingList.add(itemObj);
                }
                Almoski.getInst().setIronList(ironingList);
            }
        } else {

            CategoryItems obj = new CategoryItems();
            ArrayList<CategoryItems.Detail.Item> iroList = new ArrayList<>();
            try {
                CategoryItems.Detail detailobj = obj.new Detail();
                detailobj.setType("ironing");

                CategoryItems.Detail.Item itemObj = detailobj.new Item();

                itemObj.setItemId(itemId);
                itemObj.setAmount(String.format("%.2f", Float.parseFloat(mBinding.tvIronPrice.getText().toString())));
                itemObj.setItemName(itemName);
                itemObj.setItemcount(count);

                itemObj.setTotal(String.format("%.2f", getTotalAmount(Integer.parseInt(mBinding.ironingCount.getText().toString()), Float.parseFloat(mBinding.tvIronPrice.getText().toString()))));
                iroList.add(itemObj);

                Almoski.getInst().setIronList(iroList);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void addItemToDryCleanList(int count, String itemName, int itemId) {

        List<CategoryItems.Detail.Item> dryCleanList = Almoski.getInst().getDrycleanList();
        int isPresent = 0;
        if (dryCleanList != null) {
            for (int i = 0; i < dryCleanList.size(); i++) {
                if (dryCleanList.get(i).getItemId().equals(itemId)) {
                    // drycleanList.get(i).setItemcount(count);
                    Almoski.getInst().getDrycleanList().get(i).setItemcount(count);
                    //int amount= Integer.parseInt(Almosky.getInst().getDrycleanList().get(i).getTotalAmount());
                    float amount = getTotalAmount(Integer.parseInt(mBinding.dryCleanCount.getText().toString()), Float.parseFloat(mBinding.tvDryPrice.getText().toString()));
                    Almoski.getInst().getDrycleanList().get(i).setTotal(String.format("%.2f", amount));
                    isPresent = 1;
                }
            }
            if (isPresent == 0) {
                ArrayList<CategoryItems.Detail.Item> dryList = new ArrayList<>();

                CategoryItems categoryItems = new CategoryItems();
                CategoryItems.Detail detailobj = categoryItems.new Detail();
                detailobj.setType("dryClean");

                CategoryItems.Detail.Item itemObj = detailobj.new Item();

                itemObj.setItemId(itemId);
                itemObj.setAmount(String.format("%.2f", Float.parseFloat(mBinding.tvDryPrice.getText().toString())));
                itemObj.setItemName(itemName);
                itemObj.setItemcount(count);


                itemObj.setTotal(String.format("%.2f", getTotalAmount(Integer.parseInt(mBinding.dryCleanCount.getText().toString()), Float.parseFloat(mBinding.tvDryPrice.getText().toString()))));
                dryList.add(itemObj);

                if (null != dryCleanList) {
                    dryCleanList.add(itemObj);
                }
                Almoski.getInst().setDrycleanList(dryCleanList);
            }
        } else {
            CategoryItems obj = new CategoryItems();
            ArrayList<CategoryItems.Detail.Item> drylst = new ArrayList<>();
            try {
                CategoryItems.Detail detailobj = obj.new Detail();
                detailobj.setType("dryClean");

                CategoryItems.Detail.Item itemObj = detailobj.new Item();

                itemObj.setItemId(itemId);
                itemObj.setAmount(String.format("%.2f", Float.parseFloat(mBinding.tvDryPrice.getText().toString())));
                itemObj.setItemName(itemName);
                itemObj.setItemcount(count);

                itemObj.setTotal(String.format("%.2f", getTotalAmount(Integer.parseInt(mBinding.dryCleanCount.getText().toString()), Float.parseFloat(mBinding.tvDryPrice.getText().toString()))));
                drylst.add(itemObj);

                Almoski.getInst().setDrycleanList(drylst);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void addItemToWashIronList(int count, String itemName, int itemId) {

        List<CategoryItems.Detail.Item> washIronList = Almoski.getInst().getWashList();
        int isPresent = 0;
        if (washIronList != null) {
            for (int i = 0; i < washIronList.size(); i++) {
                if (washIronList.get(i).getItemId().equals(itemId)) {
                    // drycleanList.get(i).setItemcount(count);
                    Almoski.getInst().getWashList().get(i).setItemcount(count);
                    //int amount= Integer.parseInt(Almosky.getInst().getDrycleanList().get(i).getTotalAmount());
                    float amount = getTotalAmount(Integer.parseInt(mBinding.washIronCount.getText().toString()), Float.parseFloat(mBinding.tvWashPrice.getText().toString()));
                    Almoski.getInst().getWashList().get(i).setTotal(String.format("%.2f", amount));
                    isPresent = 1;
                }
            }
            if (isPresent == 0) {
                ArrayList<CategoryItems.Detail.Item> washIron = new ArrayList<>();

                CategoryItems categoryItems = new CategoryItems();
                CategoryItems.Detail detailobj = categoryItems.new Detail();
                detailobj.setType("washIron");

                CategoryItems.Detail.Item itemObj = detailobj.new Item();

                itemObj.setItemId(itemId);
                itemObj.setAmount(String.format("%.2f", Float.parseFloat(mBinding.tvWashPrice.getText().toString())));
                itemObj.setItemName(itemName);
                itemObj.setItemcount(count);


                itemObj.setTotal(String.format("%.2f", getTotalAmount(Integer.parseInt(mBinding.washIronCount.getText().toString()), Float.parseFloat(mBinding.tvWashPrice.getText().toString()))));
                washIron.add(itemObj);

                if (null != washIronList) {
                    washIronList.add(itemObj);
                }
                Almoski.getInst().setWashList(washIronList);
            }
        } else {
            CategoryItems obj = new CategoryItems();
            ArrayList<CategoryItems.Detail.Item> washIron = new ArrayList<>();
            try {
                CategoryItems.Detail detailobj = obj.new Detail();
                detailobj.setType("washIron");

                CategoryItems.Detail.Item itemObj = detailobj.new Item();

                itemObj.setItemId(itemId);
                itemObj.setAmount(String.format("%.2f", Float.parseFloat(mBinding.tvWashPrice.getText().toString())));
                itemObj.setItemName(itemName);
                itemObj.setItemcount(count);

                itemObj.setTotal(String.format("%.2f", getTotalAmount(Integer.parseInt(mBinding.washIronCount.getText().toString()), Float.parseFloat(mBinding.tvWashPrice.getText().toString()))));
                washIron.add(itemObj);

                Almoski.getInst().setWashList(washIron);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    void hasSameItemExistInList() {
        if (Almoski.getInst().getWashList() != null) {
            for (CategoryItems.Detail.Item item : Almoski.getInst().washList) {
                if (mItemId == item.getItemId()) {
                    setCounts("wash", item.getItemcount());
                }
            }
        }
        if (Almoski.getInst().getIronList() != null) {
            for (CategoryItems.Detail.Item item : Almoski.getInst().ironList) {
                if (mItemId == item.getItemId()) {
                    setCounts("iron", item.getItemcount());
                }
            }
        }
        if (Almoski.getInst().getDrycleanList() != null) {
            for (CategoryItems.Detail.Item item : Almoski.getInst().drycleanList) {
                if (mItemId == item.getItemId()) {
                    setCounts("dry", item.getItemcount());
                }
            }
        }
    }

    private void setCounts(String category, int itemcount) {
        switch (category) {
            case "wash":
                mBinding.washIronCount.setText(""+itemcount);
                break;
            case "iron":
                mBinding.ironingCount.setText(""+itemcount);
                break;
            case "dry":
                mBinding.dryCleanCount.setText(""+itemcount);
                break;
        }
    }

    @Override public void onBackPressed() {
        if (!isAddedToBackStack) {
           // clearItems();
        }
        super.onBackPressed();
    }

    private void clearItems() {
        Almoski.getInst().setIronList(null);
        Almoski.getInst().setWashList(null);
        Almoski.getInst().setDrycleanList(null);
    }

    float getTotalAmount(int qty, float price) {
        return qty * price;
    }
}
