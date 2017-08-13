package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.os.Bundle;

import com.detolv.myautocompletetv.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.AutoTextView)
    void onAutoTextViewClick() {
        AutoTextViewActivity.navigateTo(this);
    }

    @OnClick(R.id.MoneyTextView)
    void onMoneyTextViewClick() {
        MoneyTv_EtActivity.navigateTo(this);
    }

    @OnClick(R.id.NanoHttpServer)
    void onNanoHttpServerClick() {
        HttpActivity.navigateTo(this);
    }
}
