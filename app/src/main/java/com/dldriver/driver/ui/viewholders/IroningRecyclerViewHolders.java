package com.dldriver.driver.ui.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dldriver.driver.Almoski;
import com.dldriver.driver.R;
import com.dldriver.driver.models.CategoryItems;
import com.dldriver.driver.ui.OrderConfirmationActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class IroningRecyclerViewHolders extends RecyclerView.ViewHolder {

//    UserActionCountItemBinding binding;
public TextView dryitem,drycount,dryamount;
    CategoryItems.Detail.Item itm;
    public OrderConfirmationActivity _activty;

    public IroningRecyclerViewHolders(View itemView, Context context, OrderConfirmationActivity activity) {
        super(itemView);
        _activty=activity;
//        binding = DataBindingUtil.bind(itemView);
        dryitem = itemView.findViewById(R.id.tv_dryitem);
        dryamount = itemView.findViewById(R.id.tv_damount);
        ImageView minus = itemView.findViewById(R.id.minus);
        ImageView plus = itemView.findViewById(R.id.plus);
        drycount = itemView.findViewById(R.id.count);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countValue = Integer.parseInt(drycount.getText().toString());
                if (countValue > 0) {
                    countValue = countValue - 1;
                    drycount.setText("" + countValue);

                    itm.setItemcount(countValue);
                    updateData(itm, countValue);
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countValue = Integer.parseInt(drycount.getText().toString());
                countValue = countValue + 1;
                drycount.setText("" + countValue);
                itm.setItemcount(countValue);
                updateData(itm, countValue);
            }
        });
    }


    private void updateData(CategoryItems.Detail.Item itm, int count) {

        List<CategoryItems.Detail.Item> drycleanList= Almoski.getInst().getIronList();


        for(int i=0;i<drycleanList.size();i++) {
            if (drycleanList.get(i).getItemId().equals(itm.getItemId())) {
                Almoski.getInst().getIronList().get(i).setItemcount(count);
                Almoski.getInst().getIronList().get(i).setTotal(String.valueOf(Integer.parseInt(Almoski.getInst().getIronList().get(i).getAmount())*count));
                dryamount.setText(Almoski.getInst().getIronList().get(i).getTotal());
                _activty.updateTotal();
            }
        }

    }

    public void bind(CategoryItems.Detail.Item item) {
        itm=item;
    }


}
