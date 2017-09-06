package com.detolv.myautocompletetv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.detolv.myautocompletetv.R;

public class DragActivity extends Activity {
    public static void navigateTo(Activity activity) {
        Intent intent = new Intent(activity, DragActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
    }

}
