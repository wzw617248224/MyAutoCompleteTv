package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.detolv.myautocompletetv.R;

/**
 * Created by zewei_wang on 2017/8/16.
 */

public class CheckBoxActivity extends Activity {
    public static void navigateTo(Activity activity) {
        Intent intent = new Intent(activity, CheckBoxActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_checkbox);


    }
}
