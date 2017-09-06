package com.detolv.myautocompletetv.utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.detolv.myautocompletetv.ApplicationContext;
import com.detolv.myautocompletetv.R;

public class DisplayUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static float dp2px(float density, float dp) {
        return dp * density + 0.5f;
    }

    public static int dip2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据文字长度，缩放大小
     *
     * @param tv
     */
    public static void fixTextSize(TextView tv, int maxLen, float scaleSize) {
        float textLen = tv.getText().toString().length();
        if (textLen == maxLen) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, scaleSize);
        } else if (textLen > maxLen) {
            float newScaleSize = scaleSize - (textLen - maxLen);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, newScaleSize);
        }
    }

    public static void setTextSize(TextView tv, float scaleSize) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, scaleSize);
    }

    /**
     * 得到密度0.75,1.0,1.5
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return density;
    }

    public static float getAspectRatio() {
        Context context = ApplicationContext.getContext();
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return width / (float) height;
    }

    public static int getScreenWidth(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static int getScreenHeight(Context context) {
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    private int getViewYOnScreen(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }

    public static int floatToInt(float f) {
        return (int) Math.round(f + 0.5);
    }

}
