package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.detolv.myautocompletetv.R;

public class BesselViewActivity extends BaseActivity {

    public static void navigateTo(Activity activity) {
        Intent intent = new Intent(activity, BesselViewActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bessel_view);
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

    }

    @Override
    protected void onClickView(View v) {

    }
}
