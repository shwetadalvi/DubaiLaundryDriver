package com.dldriver.driver.ui.categoryViews;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dldriver.driver.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;


@Parent
@SingleTop
@Layout(R.layout.feed_heading)
public class HeadingView {

    @View(R.id.headingTxt)
    private TextView headingTxt;

    @View(R.id.catimage)
    private ImageView catimage;

    @View(R.id.toggleIcon)
    private ImageView toggleIcon;

    @Toggle(R.id.toggleView)
    private LinearLayout toggleView;

    @ParentPosition
    private int mParentPosition;

    private Context mContext;
    private String mHeading;
    private String imgname;
    private int type;

    public HeadingView(Context context, String heading, String img, int typ) {
        mContext = context;
        mHeading = heading;
        imgname = img;
        type=typ;

    }





    @Resolve
    private void onResolved() {
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_keyboard_arrow_up_white_24dp));
        headingTxt.setText(mHeading);
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                .skipMemoryCache(false);
      if(type==1) {
          catimage.setVisibility(android.view.View.VISIBLE);
          Glide.with(mContext).load("http://innosyz.com/categoryImage/"+imgname).apply(requestOptions).into(catimage);
     // System.out.println("http://innosyz.com/categoryImage/"+imgname);
      }
    }


    @Expand
    private void onExpand() {
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_keyboard_arrow_down_white_24dp));

    }

    @Collapse
    private void onCollapse() {
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_keyboard_arrow_up_white_24dp));
    }
}
