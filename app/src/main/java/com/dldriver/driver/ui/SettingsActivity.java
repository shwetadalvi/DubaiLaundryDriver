package com.dldriver.driver.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.dldriver.driver.ui.fragment.SettingsFragment;
import com.dldriver.driver.utils.Constants;
import com.dldriver.driver.utils.PrefUtils;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.ui.ScanningActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.dldriver.driver.utils.Constants.DEFAULT_PREFERENCE;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_PREF_PRINTER = "pref_printer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean
                (SettingsActivity.KEY_PREF_PRINTER, false);
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "WOOHOOO", Toast.LENGTH_SHORT).show();
            //Printer is ready now
        }
        if (requestCode == 12) {
            String deviceName = data.getExtras().getString("deviceName");
            String deviceMac = data.getExtras().getString("deviceMac");
            PrefUtils prefUtils = new PrefUtils(this);
            prefUtils.putStringPreference(DEFAULT_PREFERENCE, Constants.PREFERENCE_BLUETOOTH_PRINTER_NAME,deviceName);
            prefUtils.putStringPreference(DEFAULT_PREFERENCE, Constants.PREFERENCE_BLUETOOTH_PRINTER_ADDRESS,deviceMac);
            Printooth.INSTANCE.setPrinter(deviceName,deviceMac);
        }
    }
}