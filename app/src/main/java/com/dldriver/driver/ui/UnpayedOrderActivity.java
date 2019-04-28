package com.dldriver.driver.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import android.os.Bundle;
import android.view.View;

import com.dldriver.driver.BaseActivity;
import com.dldriver.driver.R;
import com.dldriver.driver.contracts.MyNewOrderContracts;
import com.dldriver.driver.databinding.ActivityUnpayedOrderBinding;
import com.dldriver.driver.models.Address;
import com.dldriver.driver.room.AppDatabase;
import com.dldriver.driver.ui.adapter.AddressAdapter;
import com.dldriver.driver.utils.PrefUtils;

import java.util.List;

import static com.dldriver.driver.utils.Constants.DEFAULT_PREFERENCE;
import static com.dldriver.driver.utils.Constants.LOGIN_PREFERENCE_DRVER_ID;

public class UnpayedOrderActivity extends BaseActivity implements MyNewOrderContracts.IMyNewOrderView {

    private ActivityUnpayedOrderBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_unpayed_order);
        showProgress("Fetching Data");
        mBinding.backButton.setOnClickListener(view->onBackPressed());
        AppDatabase database = AppDatabase.getInstance(this);
        LiveData<List<Address>> newOrderAddressList = database.mAddressDao().getAddressesWithoutPayment();
        newOrderAddressList.observe(this, this::setUpNewOrderAddressRecycler);
    }

    private void setUpNewOrderAddressRecycler(List<Address> addresses) {
        if(addresses.size()==0){
            mBinding.emptyOrder.setVisibility(View.VISIBLE);
        }else {
            AddressAdapter addressAdapter = new AddressAdapter(addresses, this,this);
            mBinding.customerAddress.setAdapter(addressAdapter);
        }
        hideProgress();
    }

    @Override public void onClickedAddress(Address address) {
        int driverId = Integer.parseInt(new PrefUtils(this).getStringPreference(DEFAULT_PREFERENCE, LOGIN_PREFERENCE_DRVER_ID));
        PaymentStatusDialog newFragment = PaymentStatusDialog.newInstance(null, Integer.parseInt(address.getOrderId()), driverId);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }
}