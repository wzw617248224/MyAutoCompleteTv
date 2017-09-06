package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.detolv.myautocompletetv.R;

import butterknife.BindView;

/**
 * Created by zewei_wang on 2017/8/16.
 */

public class ScrollViewActivity extends BaseActivity {

    @BindView(R.id.container_ll)
    LinearLayout mContainerLl;

    public static void navigateTo(Activity activity) {
        Intent intent = new Intent(activity, ScrollViewActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_scroll_view);
        bindActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void findWidget() {

    }

    @Override
    protected void initWidget() {
        int max = 13;
        for (int i = 1; i < max; i++){
            LinearLayout scrollViewItem = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.scroll_view_item_layout, null);
            TextView tittleTv = (TextView) scrollViewItem.findViewById(R.id.title_tv);
            tittleTv.setText(i+"");
            mContainerLl.addView(scrollViewItem);
        }

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onClickView(View v) {

    }

}
