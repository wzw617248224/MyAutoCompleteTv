package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.detolv.myautocompletetv.R;

public class CustomLlActivity extends Activity {

    public static void navigateTo(Activity activity){
        Intent intent = new Intent(activity, CustomLlActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_ll);
    }
}
