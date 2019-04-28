package com.dldriver.driver.ui.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dldriver.driver.R;
import com.dldriver.driver.interfaces.ClickListeners;
import com.dldriver.driver.models.Remarks;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RemarksAdapter extends RecyclerView.Adapter<RemarksAdapter.ViewHolder> {

    List<Remarks> RemarksList = new ArrayList<>();
    Context mContext;
    private ClickListeners.getRemarkList itemClick;
    private View mView;


    public RemarksAdapter(List<Remarks> RemarksList, Context context, ClickListeners.getRemarkList itemClick) {
        this.RemarksList = RemarksList;
        mContext = context;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.remarks_item, viewGroup, false);
        return new ViewHolder(mView);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //viewHolder.bind(RemarksList.get(i));

        RelativeLayout rowTime = viewHolder.itemView.findViewById(R.id.rowTime);
        TextView txtRemark = viewHolder.itemView.findViewById(R.id.txtRemark);
        ImageView imageCheck = viewHolder.itemView.findViewById(R.id.imageCheck);
        txtRemark.setText(RemarksList.get(i).getRemarks());
     /*   if(RemarksList.get(i).getValue() == 0)
            imageCheck.setImageResource(R.drawable.check_icon);
        else
            imageCheck.setImageResource(R.drawable.uncheck_icon);*/

        rowTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(RemarksList.get(i).getValue() == 0){
                    imageCheck.setImageResource(R.drawable.check_icon);
                    RemarksList.get(i).setValue(1);
                }else {
                    imageCheck.setImageResource(R.drawable.uncheck_icon);
                    RemarksList.get(i).setValue(0);
                }
                itemClick.onClickedRemarkItem(RemarksList);
              /*  if(_activity.equals("pickup")){
                    Almosky.getInst().setPickuptime(_timeArray.get(position));
                }
                if(_activity.equals("delivery")){
                    Almosky.getInst().setDeliverytime(_timeArray.get(position));
                }*/

                notifyDataSetChanged();
            }
        });
    }

    @Override public int getItemCount() {
        return RemarksList.size();
    }


    public int getRemarksId(int position) {

        return RemarksList.get(position).getId();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView remarkListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remarkListItem = itemView.findViewById(R.id.txtRemark);
        }

        public void bind(Remarks Remarks) {
            remarkListItem.setText(Remarks.getRemarks());
            itemView.setOnClickListener(view -> itemClick.onClickedRemarkItem(RemarksList));
        }
    }
}
