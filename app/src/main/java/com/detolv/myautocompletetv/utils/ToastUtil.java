package com.detolv.myautocompletetv.utils;

import android.app.Activity;
import android.widget.Toast;

import com.detolv.myautocompletetv.ApplicationContext;

/**
 * Created by detolv on 8/13/17.
 */

public class ToastUtil {
    public static void showToast(String message) {
        Toast.makeText(ApplicationContext.getContext(), message, Toast.LENGTH_SHORT).show();
    }
    public static void showToastOnUIThread(Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ApplicationContext.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
