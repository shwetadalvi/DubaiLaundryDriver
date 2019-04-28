package com.dldriver.driver.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.dldriver.driver.utils.Constants.DEFAULT_PREFERENCE;

public class PrefUtils {
    private final Context context;
    private SharedPreferences sharedPreferences;

    public PrefUtils(Context context) {
        this.context = context;
    }

    private SharedPreferences getPreference(String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void putStringPreference(String preference, String name, String value) {
        sharedPreferences = getPreference(preference);
        sharedPreferences.edit().putString(name, value).apply();
    }

    public void putBooleanPreference(String preference, String name, boolean value) {
        sharedPreferences = getPreference(preference);
        sharedPreferences.edit().putBoolean(name, value).apply();
    }

    public Boolean getBooleanPreference(String preference, String name) {
        sharedPreferences = getPreference(preference);
        return sharedPreferences.getBoolean(name, false);
    }

    public String getStringPreference(String preference, String name) {
        sharedPreferences = getPreference(preference);
        return sharedPreferences.getString(name, "");
    }

    public String getDefaultSharedPreferenceString(String  name,String defaultValue){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("list_preference_1","0");
    }

    public String getToken(String name) {
        sharedPreferences = getPreference("token");
        return "Bearer " + sharedPreferences.getString(name, "");
    }

    public boolean getLoggedInState(String name) {
        return getPreference(DEFAULT_PREFERENCE).getBoolean(name, false);
    }

    public void changeLoggedInState(String name, boolean loginState) {
        getPreference(DEFAULT_PREFERENCE).edit().putBoolean(name, loginState).apply();
    }

    public void deletePref(String preference, String name){
        getPreference(preference).edit().remove(name).apply();
    }

}