package com.detolv.myautocompletetv.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by zewei_wang on 2017/9/13.
 */

public class BesselView extends View {
    private int PULL_HEIGHT;
    private int mWidth;
    private int mHeight;
    private int mOffsetHeight;
    private Paint mPaint;
    private Path mPath;

    public BesselView(Context context) {
        this(context, null, 0);
    }

    public BesselView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BesselView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        PULL_HEIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, context.getResources().getDisplayMetrics());

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(0xff8b90af);

        mPath = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            mWidth = getWidth();
            mHeight = getHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, PULL_HEIGHT);
        mPath.quadTo(mWidth / 2, mOffsetHeight / 2, mWidth, PULL_HEIGHT);
        canvas.drawPath(mPath, mPaint);
    }

    public void setOffsetHeight(int mOffsetHeight) {
        this.mOffsetHeight = mOffsetHeight;
    }
}
