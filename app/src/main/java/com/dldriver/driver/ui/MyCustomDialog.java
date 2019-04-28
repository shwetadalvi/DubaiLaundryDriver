package com.dldriver.driver.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dldriver.driver.R;

import androidx.appcompat.app.AlertDialog;


public class MyCustomDialog extends AlertDialog.Builder {

    public MyCustomDialog(Context context, String message) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDialog = inflater.inflate(R.layout.dialog_custom, null, false);

        TextView messageTv = (TextView) viewDialog.findViewById(R.id.text);
        messageTv.setText(message);

        this.setCancelable(false);

        this.setView(viewDialog);

    }
}