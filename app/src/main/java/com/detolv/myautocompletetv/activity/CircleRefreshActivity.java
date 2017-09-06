package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.detolv.myautocompletetv.R;
import com.detolv.myautocompletetv.utils.ToastUtil;
import com.detolv.myautocompletetv.widget.CircleRefreshLayout;

import butterknife.BindView;

public class CircleRefreshActivity extends BaseActivity {

    @BindView(R.id.refresh_layout)
    CircleRefreshLayout mRefreshLayout;

    public static void navigateTo(Activity activity) {
        Intent intent = new Intent(activity, CircleRefreshActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_circle_refresh);
        bindActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void findWidget() {

    }

    @Override
    protected void initWidget() {

    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {
                ToastUtil.showToast("刷新完成");
            }

            @Override
            public void refreshing() {
                ToastUtil.showToast("刷新中...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefreshing();
                    }
                }, 2000L);
            }
        });
    }

    @Override
    protected void onClickView(View v) {

    }
}
