package com.dldriver.driver.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dldriver.driver.R;

public class ServiceHeaderViewHolder extends RecyclerView.ViewHolder {
    private TextView mHeader;

    public ServiceHeaderViewHolder(View view) {
        super(view);

    }
    public void bind(String header){
        mHeader = itemView.findViewById(R.id.service);
      mHeader.setText(header);
    }
}
