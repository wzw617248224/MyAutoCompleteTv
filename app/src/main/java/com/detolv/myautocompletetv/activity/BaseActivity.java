package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by zewei_wang on 2017/8/31.
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener {
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(mActivity);
        findWidget();
        initWidget();
        setListener();
    }

    protected void bindActivity(Activity activity){
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        onClickView(v);
    }

    protected abstract void findWidget();
    protected abstract void initWidget();
    protected abstract void setListener();
    protected abstract void onClickView(View v);

}
