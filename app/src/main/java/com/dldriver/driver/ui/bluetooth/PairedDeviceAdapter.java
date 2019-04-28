package com.dldriver.driver.ui.bluetooth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dldriver.driver.R;
import com.dldriver.driver.models.BtDevice;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PairedDeviceAdapter extends RecyclerView.Adapter<PairedDeviceAdapter.ViewHolder> {
    List<BtDevice> mBtDeviceList;
    Context mContext;
    public PairedDeviceAdapter(List<BtDevice> btDeviceList) {
        mBtDeviceList = btDeviceList;
    }

    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.bluetooth_device_item,parent,false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mBtDeviceList.get(position));
    }

    @Override public int getItemCount() {
        return mBtDeviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,deviceStatus;
        ImageView deviceType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.deviceName);
            deviceStatus = itemView.findViewById(R.id.deviceStatus);
            deviceType = itemView.findViewById(R.id.deviceType);
        }
        public void bind(BtDevice btDevice){
            name.setText(btDevice.getDeviceName());
            if (btDevice.getDeviceStatus()==1){
                deviceStatus.setText(mContext.getString(R.string.connected));
            }
        }
    }
}
