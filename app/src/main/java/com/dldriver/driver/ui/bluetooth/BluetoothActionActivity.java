package com.dldriver.driver.ui.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.dldriver.driver.BaseActivity;
import com.dldriver.driver.R;
import com.dldriver.driver.models.BtDevice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.dldriver.driver.utils.Constants.REQUEST_CODE_ENABLE_BLUETOOTH;

public class BluetoothActionActivity extends BaseActivity implements UnknownDeviceAdapter.OnItemClicked {
    List<BluetoothDevice> mBtDeviceList = new ArrayList<>();
    BluetoothAdapter mBluetoothAdapter;
    Intent mIntent = new Intent();
    RecyclerView unKnownDevices;
    RecyclerView pairedDevices;
    private UnknownDeviceAdapter mUnknownDeviceAdapter;
    private List<BtDevice> mPairedDeviceList = new ArrayList<>();
    private PairedDeviceAdapter mPairedDeviceAdapter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        pairedDevices = findViewById(R.id.pairedDevicesRecycler);
        unKnownDevices = findViewById(R.id.devicesFoundRecycler);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            mIntent.setData(Uri.parse("Device not support bluetooth"));
            setResult(RESULT_CANCELED, mIntent);
            finish();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_CODE_ENABLE_BLUETOOTH);
            } else {
                setUpListeners();
            }
        }
    }


    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ENABLE_BLUETOOTH) {
            setUpListeners();
        }
    }

    Set<BluetoothDevice> mBluetoothDeviceSet = new HashSet<>();

    private void setUpListeners() {

    }

    private void setUpRecyclers() {
        mUnknownDeviceAdapter = new UnknownDeviceAdapter(mBtDeviceList);
        mUnknownDeviceAdapter.setOnItemClickedListener(this);
        unKnownDevices.setAdapter(mUnknownDeviceAdapter);
    }

    private List<BluetoothDevice> removeDuplicate(List<BluetoothDevice> deviceList, BluetoothDevice btDevice) {
        if (deviceList.size() > 1) {
            for (int i = 1; i < deviceList.size(); i++) {
                BluetoothDevice btDevic = deviceList.get(i);
                if (btDevic.equals(btDevice)) {
                    deviceList.remove(i);
                }
            }
        }
        return deviceList;
    }

    private List<BluetoothDevice> setToList(Set<BluetoothDevice> set) {
        return new ArrayList<>(set);
    }


    @Override public void onClickedBluetoothDevice(BluetoothDevice btDevice) {
//        mBluetooth.onStop();
        mIntent.putExtra("deviceName", btDevice.getName());
        mIntent.putExtra("deviceMac", btDevice.getAddress());
        setResult(RESULT_OK, mIntent);
        finish();
    }

    private static final String TAG = "BluetoothActionActivity";
}