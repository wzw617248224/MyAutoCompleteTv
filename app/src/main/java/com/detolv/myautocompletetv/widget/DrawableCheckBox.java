package com.detolv.myautocompletetv.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.detolv.myautocompletetv.R;

/**
 * Created by zewei_wang on 2017/8/16.
 */

public class DrawableCheckBox extends CheckBox {
    public static final int LEFT = 1, TOP = 2, RIGHT = 3, BOTTOM = 4;

    private int mHeight, mWidth;

    private Drawable mDrawable;

    private int mLocation;
    private boolean hasDrawed;
    private int textSize = 46;

    public DrawableCheckBox(Context context) {
        super(context);
    }

    public DrawableCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawableCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.DrawableCheckBox);

        mWidth = a.getDimensionPixelSize(R.styleable.DrawableCheckBox_drawable_width, 0);
        mHeight = a.getDimensionPixelSize(R.styleable.DrawableCheckBox_drawable_height, 0);
        mDrawable = a.getDrawable(R.styleable.DrawableCheckBox_drawable_src);
        mLocation = a.getInt(R.styleable.DrawableCheckBox_drawable_location, LEFT);

        a.recycle();
        //绘制Drawable宽高,位置
        drawDrawable(mDrawable, mWidth, mHeight, textSize);
    }

    private void drawDrawable(Drawable drawable, int width, int height, int textSize) {
        if (drawable != null) {
            //设置drawable对象的大小
            int offsetVertical = (textSize - height)/2;
            drawable.setBounds(0, offsetVertical, width, height + offsetVertical);
            switch (mLocation) {
                case LEFT:
                    this.setCompoundDrawables(drawable, null, null, null);
                    break;
                case TOP:
                    this.setCompoundDrawables(null, drawable, null, null);
                    break;
                case RIGHT:
                    this.setCompoundDrawables(null, null, drawable, null);
                    break;
                case BOTTOM:
                    this.setCompoundDrawables(null, null, null, drawable);
                    break;
            }
            hasDrawed = true;
        }
    }
}
