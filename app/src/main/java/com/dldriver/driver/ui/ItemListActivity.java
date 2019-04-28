package com.dldriver.driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dldriver.driver.Almoski;
import com.dldriver.driver.BaseActivity;
import com.dldriver.driver.NetworkResponses;
import com.dldriver.driver.R;
import com.dldriver.driver.databinding.ActivityItemListBinding;
import com.dldriver.driver.interactors.OrderInteracterImpl;
import com.dldriver.driver.models.BaseError;
import com.dldriver.driver.models.CategoryItems;
import com.dldriver.driver.models.PriceDetails;
import com.dldriver.driver.ui.categoryViews.HeadingView;
import com.dldriver.driver.ui.categoryViews.InfoView;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import androidx.databinding.DataBindingUtil;

public class ItemListActivity extends BaseActivity implements NetworkResponses<CategoryItems> {

    private static final String TAG = "ItemListActivity";
    private ExpandablePlaceHolderView mExpandableView;
    private ExpandablePlaceHolderView.OnScrollListener mOnScrollListener;
    private ActivityItemListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_list);
        OrderInteracterImpl orderInteracter = new OrderInteracterImpl();
        orderInteracter.fetchCategories(this);
        orderInteracter.fetchPriceDetails(new NetworkResponses<PriceDetails>() {

            @Override public void success(PriceDetails response) {
                for (PriceDetails.Detail detail : response.getDetail()) {
                    switch (detail.getServiceId()) {
                        case 1://dryclean
                            Almoski.getInst().setDrycleanpriceList(response);
                            break;
                        case 2://wash+iron
                            Almoski.getInst().setWashironpriceList(response);
                            break;
                        case 3://iron
                            Almoski.getInst().setIroningpriceList(response);
                            break;
                    }
                }
            }

            @Override public void failed(PriceDetails failedResonse) {

            }

            @Override public void error(BaseError baseError) {

            }
        });
        mBinding.bottomLayout.setOnClickListener(v->onClickedBottomBar());
    }

    private void onClickedBottomBar() {
        Intent intent = new Intent(this, OrderConfirmationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override protected void onResume() {
        super.onResume();
        if (Almoski.getInst().getDrycleanList() != null || Almoski.getInst().getWashList() != null | Almoski.getInst().getIronList() != null) {
            mBinding.bottomLayout.setVisibility(View.VISIBLE);
            setUpFooter();
        }
    }

    private void setUpFooter() {
        int cartCount = 0;
        float cartTotalAmount = 0;
    //    Log.d("Inside :", "Inside setUpOrderColumn1 setUpFooter getWashList :" + Almoski.getInst().getWashList().size());
        if (Almoski.getInst().getWashList() != null) {
            for (int i = 0; i < Almoski.getInst().getWashList().size(); i++) {
                cartCount = cartCount + Almoski.getInst().getWashList().get(i).getItemcount();
                cartTotalAmount = cartTotalAmount + Float.parseFloat(Almoski.getInst().getWashList().get(i).getTotal());
               // cartTotalAmount = cartTotalAmount + Float.parseFloat(Almoski.getInst().getWashList().get(i).getTotal());
            }
        }
      //  Log.d("Inside :", "Inside setUpOrderColumn1 setUpFooter getIronList :" + Almoski.getInst().getIronList().size());
        if (Almoski.getInst().getIronList() != null) {
            for (int i = 0; i < Almoski.getInst().getIronList().size(); i++) {
                cartCount = cartCount + Almoski.getInst().getIronList().get(i).getItemcount();
                cartTotalAmount = cartTotalAmount + Float.parseFloat(Almoski.getInst().getIronList().get(i).getTotal());
            }
        }
     //   Log.d("Inside :", "Inside setUpOrderColumn1 setUpFooter getDrycleanList :" + Almoski.getInst().getDrycleanList().size());
        if (Almoski.getInst().getDrycleanList() != null) {
            for (int i = 0; i < Almoski.getInst().getDrycleanList().size(); i++) {
                cartCount = cartCount + Almoski.getInst().getDrycleanList().get(i).getItemcount();
                cartTotalAmount = cartTotalAmount + Float.parseFloat(Almoski.getInst().getDrycleanList().get(i).getTotal());
            }
        }
        //mBinding.tvCartcount.setText("Your Bucket (" + cartCount+")");
     //  mBinding.tvCartamount.setText("AED" + cartTotalAmount);

    }

    @Override public void success(CategoryItems response) {

        Log.d(TAG, "success: ");
        if (0 != response.getDetail().size()) {
            for (int i = 0; i < response.getDetail().size(); i++) {
                mBinding.expandableView.addView(new HeadingView(this, response.getDetail().get(i).getCategoryName(), response.getDetail().get(i).getCategoryIcons(), 1));
                for (int j = 0; j < response.getDetail().get(i).getItems().size(); j++) {
                    mBinding.expandableView.addView(new InfoView(this, response.getDetail().get(i).getItems().get(j)));
                }
            }

        }
    }

    @Override public void failed(CategoryItems failedResonse) {
        Log.d(TAG, "failed: ");
    }

    @Override public void error(BaseError baseError) {
        Log.d(TAG, "error: ");
    }
}
