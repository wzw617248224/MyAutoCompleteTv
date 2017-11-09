package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.detolv.myautocompletetv.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageViewActivity extends Activity {

    @BindView(R.id.change_btn)
    Button mChangeBtn;

    @BindView(R.id.image_view)
    ImageView mImg;

    private boolean mBool;

    public static void navigateTo(Activity activity){
        Intent intent = new Intent(activity, ImageViewActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
        initImgView(mBool);
    }

    @OnClick(R.id.change_btn)
    void onImageViewClick() {
        mBool = initImgView(mBool);
    }

    private boolean initImgView(boolean bool) {
        if (bool){
            mImg.setImageDrawable(getTintedDrawable(this, R.mipmap.ic_launcher, R.color.transparent));
        }else {
            mImg.setImageDrawable(getTintedDrawable(this, R.mipmap.ic_launcher, R.color.true_half_transparent_white));
        }
        bool = !bool;
        return bool;
    }

    public static Drawable getTintedDrawable(@NonNull final Context context,
                                             @DrawableRes int drawableRes, @ColorRes int colorRes) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableRes);
        drawable = DrawableCompat.wrap(drawable);
        drawable.setColorFilter(context.getResources().getColor(colorRes), PorterDuff.Mode.LIGHTEN);
        return drawable;
    }
}
