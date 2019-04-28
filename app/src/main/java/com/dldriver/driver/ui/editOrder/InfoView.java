package com.dldriver.driver.ui.editOrder;

import android.content.Context;
import android.content.Intent;

import android.widget.ImageView;
import android.widget.TextView;

import com.dldriver.driver.R;
import com.dldriver.driver.models.categorydto;
import com.dldriver.driver.ui.ItemDetailsAddActivity;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.squareup.picasso.Picasso;

import androidx.constraintlayout.widget.ConstraintLayout;


@Layout(R.layout.feed_item)
public class InfoView {


    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.item)
    private ConstraintLayout groupView;


    @View(R.id.titleTxt)
    private TextView titleTxt;

    @View(R.id.captionTxt)
    private TextView captionTxt;


    @View(R.id.imageView)
    private ImageView imageView;

    private categorydto.Detail.Item mInfo;
    private Context mContext;

    public InfoView(Context context, categorydto.Detail.Item info) {
        mContext = context;
        mInfo = info;
    }


    @Resolve
    private void onResolved() {
        // titleTxt.setText(mInfo.getItemName());
        // captionTxt.setText("100");

        Picasso.get().load("http://almosky.abrappsworld.com/ItemImages/" + mInfo.getItemImage()).into(imageView);
        titleTxt.setText(mInfo.getItemName());

        //  Glide.with(mContext).load("http://almosky.abrappsworld.com/ItemImages/"+mInfo.getItemImage()).into(imageView);
        //   Glide.with(mContext).load("http://innosyz.com/itemimages/"+mInfo.getItemImage()).into(imageView);
        System.out.println("http://almosky.abrappsworld.com/ItemImages/" + mInfo.getItemImage());


        imageView.setOnClickListener(v -> onClickedView());
        captionTxt.setOnClickListener(v -> onClickedView());
        groupView.setOnClickListener(v -> onClickedView());

    }

    private void onClickedView() {
        Intent intent = new Intent(mContext, ItemDetailsAddActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("catId", mInfo.getCategoryId());
        intent.putExtra("catname", mInfo.getCategoryName());
        intent.putExtra("itemId", mInfo.getItemId());
        intent.putExtra("itemname", mInfo.getItemName());
        intent.putExtra("url", "http://almosky.abrappsworld.com/ItemImages/" + mInfo.getItemImage());
        mContext.startActivity(intent);

    }
}
