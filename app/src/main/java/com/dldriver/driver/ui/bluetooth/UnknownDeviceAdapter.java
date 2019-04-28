package com.dldriver.driver.ui.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dldriver.driver.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UnknownDeviceAdapter extends RecyclerView.Adapter<UnknownDeviceAdapter.ViewHolder> {
    List<BluetoothDevice> mBtDeviceList;
    Context mContext;

    public UnknownDeviceAdapter(List<BluetoothDevice> btDeviceList) {
        mBtDeviceList = btDeviceList;
    }

    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.bluetooth_device_item, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mBtDeviceList.get(position));
    }

    @Override public int getItemCount() {
        return mBtDeviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, deviceStatus;
        ImageView deviceType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.deviceName);
            deviceStatus = itemView.findViewById(R.id.deviceStatus);
            deviceType = itemView.findViewById(R.id.deviceType);
        }

        public void bind(BluetoothDevice btDevice) {
            name.setText(btDevice.getName());
            deviceStatus.setVisibility(View.GONE);
//            if (btDevice.getDeviceStatus() == 1) {
//                deviceStatus.setText(mContext.getString(R.string.connected));
//            }
            itemView.setOnClickListener(v -> {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("Select Bluetooth device");
                alertDialog.setMessage("Are you sure to select "+btDevice.getName()+" as the printer ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", (dialog, which) -> {
                    mOnItemClickedListener.onClickedBluetoothDevice(btDevice);
                    dialog.dismiss();
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", (dialog, which) -> {
                    dialog.dismiss();
                });
                alertDialog.setOnShowListener(dialog -> {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackground(null);
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackground(null);
                });
                alertDialog.show();
            });
        }
    }

    private OnItemClicked mOnItemClickedListener;

    public void setOnItemClickedListener(OnItemClicked onItemClickedListener){
        mOnItemClickedListener = onItemClickedListener;
    }

    interface OnItemClicked {
        void onClickedBluetoothDevice(BluetoothDevice btDevice);
    }
}
