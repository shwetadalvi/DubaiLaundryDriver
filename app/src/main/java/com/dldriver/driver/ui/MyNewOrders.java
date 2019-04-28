package com.dldriver.driver.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dldriver.driver.BaseActivity;
import com.dldriver.driver.R;
import com.dldriver.driver.contracts.MyNewOrderContracts;
import com.dldriver.driver.databinding.ActivityMyNewOrdersBinding;
import com.dldriver.driver.models.Address;
import com.dldriver.driver.room.AppDatabase;
import com.dldriver.driver.ui.adapter.AddressAdapter;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

public class MyNewOrders extends BaseActivity implements MyNewOrderContracts.IMyNewOrderView {

    private ActivityMyNewOrdersBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_new_orders);
        showProgress("");


        AppDatabase database = AppDatabase.getInstance(this);
        LiveData<List<Address>> newOrderAddressList = database.mAddressDao().getNewAddresses();
        newOrderAddressList.observe(this, this::setUpNewOrderAddressRecycler);
        mBinding.backButton.setOnClickListener(view-> onBackPressed());
    }

    private void setUpNewOrderAddressRecycler(List<Address> addresses) {
        if(addresses.size()==0){
            mBinding.emptyOrder.setVisibility(View.VISIBLE);
        }else {
            AddressAdapter addressAdapter = new AddressAdapter(addresses, this,this);
            mBinding.myNewOrderAddresses.setAdapter(addressAdapter);
        }
        hideProgress();
    }

    @Override public void onClickedAddress(Address address) {
        startActivity(new Intent(this,OrderActiivity.class).putExtra("orderId",address.getOrderId()));
    }

}
