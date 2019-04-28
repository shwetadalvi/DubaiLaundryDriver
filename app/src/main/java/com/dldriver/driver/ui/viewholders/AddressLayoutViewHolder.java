package com.dldriver.driver.ui.viewholders;

import android.util.Log;
import android.view.View;

import com.dldriver.driver.contracts.MyNewOrderContracts;
import com.dldriver.driver.databinding.AddressLayoutItemBinding;
import com.dldriver.driver.models.Address;

import androidx.recyclerview.widget.RecyclerView;

public class AddressLayoutViewHolder extends RecyclerView.ViewHolder {
    AddressLayoutItemBinding mAddressLayoutItemBinding;
    private MyNewOrderContracts.IMyNewOrderView mMyNewOrderView;

    public AddressLayoutViewHolder(AddressLayoutItemBinding binding, MyNewOrderContracts.IMyNewOrderView myNewOrderView) {
        super(binding.getRoot());
        mAddressLayoutItemBinding = binding;
        mMyNewOrderView = myNewOrderView;
    }

    public void bindAddress(Address address) {
        mAddressLayoutItemBinding.setAddress(address);
        if (address.getTimeSlot() != null & !address.getTimeSlot().equalsIgnoreCase("00:00:00")) {

            if (address.getTimeSlot() != null || address.getTimeSlot().isEmpty()) {
                Log.d("Inside :","Inside TimeSlot "+address.getTimeSlot());
                if (!address.getTimeSlot().equals("@")) {
                    String[] dateTime = address.getTimeSlot().split("@");
                    String date = dateTime[0].substring(0, dateTime[0].lastIndexOf(" "));
                    String time = dateTime[1];
                    mAddressLayoutItemBinding.timeSlot.setText(time);
                    mAddressLayoutItemBinding.date.setText(date);
                }
            }
        }
        mAddressLayoutItemBinding.setOnClick(mMyNewOrderView);
    }

    public void disableView() {
        itemView.setAlpha(0.3f);
        itemView.setVisibility(View.GONE);
    }
}
