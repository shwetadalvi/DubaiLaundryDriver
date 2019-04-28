package com.dldriver.driver.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dldriver.driver.R;
import com.dldriver.driver.models.Item;
import com.dldriver.driver.models.Service;
import com.dldriver.driver.ui.viewholders.OrderItemViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Service> mServiceList;
    String currentService;

    public OrderItemAdapter(List<Service> addressList) {
        mServiceList = addressList;
    }

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new OrderItemViewHolder(layoutInflater.inflate(R.layout.items_layout, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        currentService = mServiceList.get(position).getServiceName();
        OrderItemViewHolder footerHolder = (OrderItemViewHolder) holder;
        List<Item> listItem = mServiceList.get(position).getItems();
        for (int j = 0; j < listItem.size(); j++) {
            footerHolder.bind(listItem.get(j).getOrderItems());
        }

    }

    @Override public int getItemCount()
    {
        return mServiceList.size();
    }
}
