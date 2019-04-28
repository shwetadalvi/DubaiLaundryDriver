package com.dldriver.driver.ui.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.dldriver.driver.R;
import com.dldriver.driver.models.Item;
import com.dldriver.driver.models.Service;
import com.dldriver.driver.ui.viewholders.OrderItemViewHolder;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;


public class GroupAdapter extends StatelessSection {

    String title;
    List<String> list;
    private List<Service> mList;

    public GroupAdapter(List<Service> list) {
        // call constructor with layout resources for this Section header, footer and items
        super(SectionParameters.builder()
                .headerResourceId(R.layout.service_header_layout)
                .itemResourceId(R.layout.items_layout)
                .build());

        mList = list;
    }

    @Override
    public int getContentItemsTotal() {
        return mList.size(); // number of items of this section
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section

        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderItemViewHolder footerHolder = (OrderItemViewHolder) holder;
        List<Item> listItem = mList.get(position).getItems();
        for (int j = 0; j < listItem.size(); j++) {
            footerHolder.bind(listItem.get(j).getOrderItems());
        }
    }
/*
    @Override public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new ServiceHeaderViewHolder(view);
    }

    @Override public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        ServiceHeaderViewHolder viewHolder = (ServiceHeaderViewHolder) holder;
        viewHolder.bind(mList.get(holder.getAdapterPosition()).getServiceName());

    }*/
}