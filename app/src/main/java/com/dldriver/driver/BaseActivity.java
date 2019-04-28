package com.dldriver.driver;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.dldriver.driver.ui.MyCustomDialog;
import com.dldriver.driver.utils.ConnectionReceiver;
import com.dldriver.driver.utils.PrefUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.dldriver.driver.utils.Constants.DEFAULT_PREFERENCE;
import static com.dldriver.driver.utils.Constants.REGISTRATION_TOKEN_PREFERENCE;


public class BaseActivity extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener {

    private SimpleArcDialog mDialog;
    private static View rootView;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = getWindow().getDecorView().getRootView();
        mDialog = new SimpleArcDialog(this);
        showProgress("Getting System Data");
        FirebaseMessaging.getInstance().subscribeToTopic("Laundry");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    String token = task.getResult().getToken();
                    PrefUtils prefUtils = new PrefUtils(this);
                    if (prefUtils.getStringPreference(DEFAULT_PREFERENCE, REGISTRATION_TOKEN_PREFERENCE).isEmpty()) {
                        prefUtils.putStringPreference(DEFAULT_PREFERENCE, REGISTRATION_TOKEN_PREFERENCE, token);
                    }
                    hideProgress();
                });
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    public boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static void showSnackBar(View view, String message, int length) {
        if (view == null) {
            view = rootView;
        }
        Snackbar.make(view, message, length).show();
    }

    public void showProgress(String message) {
        mDialog.setConfiguration(new ArcConfiguration(this));
        mDialog.setCanceledOnTouchOutside(false);
        if (!message.isEmpty())
            mDialog.setTitle(message);
        mDialog.show();
    }

    public void hideProgress() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override public void onBackPressed() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        super.onBackPressed();
    }
    public void getResponse(String response, int requestId) {

    }

    public Dialog openAlertDialogue(String messageText) {
        final MyCustomDialog builder = new MyCustomDialog(BaseActivity.this, messageText);
        final AlertDialog dialog = builder.setNegativeButton("OK", (dialogInterface, i) -> {


        }).create();
        dialog.setOnShowListener(arg0 -> dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(BaseActivity.this.getResources().getColor(R.color.green)));

        dialog.show();

        dialog.show();
        return dialog;
    }

    @Override public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            noNwAlert = openAlertDialogue("Check internet connection");
            //show a No Internet Alert or Dialog

        } else {
            if (null != noNwAlert)
                noNwAlert.dismiss();
            // dismiss the dialog or refresh the activity
        }
    }

    private Dialog noNwAlert;
}
