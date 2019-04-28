package com.dldriver.driver.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.dldriver.driver.R;
import com.mazenrashed.printooth.ui.ScanningActivity;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import static com.mazenrashed.printooth.ui.ScanningActivity.SCANNING_FOR_PRINTER;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        ListPreference listPreference = (ListPreference) findPreference("list_preference_1");
        if(listPreference.getValue()==null) {
            // to ensure we don't get a null value
            // set first value by default
            listPreference.setValueIndex(0);
        }
        listPreference.setSummary(listPreference.getEntry());
        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            preference.setSummary(listPreference.getEntries()[Integer.parseInt(newValue.toString())]);
            if (newValue.equals("0")){
                startActivityForResult(new Intent(getContext(), ScanningActivity.class),SCANNING_FOR_PRINTER);
//                startActivityForResult(new Intent(getContext(), BluetoothActionActivity.class), 12);
            }
            return true;
        });
    }
}
