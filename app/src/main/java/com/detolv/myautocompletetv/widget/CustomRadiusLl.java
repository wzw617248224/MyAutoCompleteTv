package com.detolv.myautocompletetv.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.detolv.myautocompletetv.R;

/**
 * Created by zewei_wang on 2017/11/13.
 */

public class CustomRadiusLl extends LinearLayout {
    private static final int DIRECTION_RIGHT = 0;
    private static final int DIRECTION_LEFT = 1;
    private final int mDirection;
    private int mLeftTopRadius;
    private int mRightTopRadius;
    private int mLeftBottomRadius;
    private int mRightBottomRadius;

    private boolean mLeftTopInside;
    private boolean mRightTopInside;
    private boolean mLeftBottomInside;
    private boolean mRightBottomInside;
    private Path mLinePath = new Path();
    private Paint mLinePaint = new Paint();
    private Path mPath = new Path();
    private RectF oval = new RectF();

    private Drawable mDrawable;
    private float mStrokeWidth = 6f;

    public CustomRadiusLl(Context context) {
        this(context, null);
    }

    public CustomRadiusLl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRadiusLl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomRadiusLl, defStyleAttr, 0);

        mLeftTopRadius = a.getDimensionPixelSize(R.styleable.CustomRadiusLl_leftTopRadius, 0);
        mRightTopRadius = a.getDimensionPixelSize(R.styleable.CustomRadiusLl_rightTopRadius, 0);
        mLeftBottomRadius = a.getDimensionPixelSize(R.styleable.CustomRadiusLl_leftBottomRadius, 0);
        mRightBottomRadius = a.getDimensionPixelSize(R.styleable.CustomRadiusLl_rightBottomRadius, 0);

        mLeftTopInside = a.getBoolean(R.styleable.CustomRadiusLl_leftTopInside, false);
        mRightTopInside = a.getBoolean(R.styleable.CustomRadiusLl_rightTopInside, false);
        mLeftBottomInside = a.getBoolean(R.styleable.CustomRadiusLl_leftBottomInside, false);
        mRightBottomInside = a.getBoolean(R.styleable.CustomRadiusLl_rightBottomInside, false);

        mDirection = a.getInt(R.styleable.CustomRadiusLl_divideLineDirection, -1);

        mDrawable = a.getDrawable(R.styleable.CustomRadiusLl_bgDrawable);

        a.recycle();
    }

    private void generatePath(int width, int height) {
        if (mLeftTopInside){
            setOvalByXY(0, 0, mLeftTopRadius);
            mPath.arcTo(oval, 90f, -90f, false);
        }else {
            setOvalByXY(mLeftTopRadius, mLeftTopRadius, mLeftTopRadius);
            mPath.arcTo(oval, 180f, 90f, false);
        }
        if (mRightTopInside){
            setOvalByXY(width, 0, mRightTopRadius);
            mPath.arcTo(oval, 180f, -90f, false);
        }else {
            setOvalByXY(width - mRightTopRadius, mRightTopRadius, mRightTopRadius);
            mPath.arcTo(oval, -90f, 90f, false);
        }
        if (mRightBottomInside){
            setOvalByXY(width, height, mRightBottomRadius);
            mPath.arcTo(oval, -90f, -90f, false);
        }else {
            setOvalByXY(width - mRightBottomRadius, height - mRightBottomRadius, mRightBottomRadius);
            mPath.arcTo(oval, 0f, 90f, false);
        }
        if (mLeftBottomInside){
            setOvalByXY(0, height, mLeftBottomRadius);
            mPath.arcTo(oval, 0f, -90f, false);
        }else {
            setOvalByXY(mLeftBottomRadius, height - mLeftBottomRadius, mLeftBottomRadius);
            mPath.arcTo(oval, 90f, 90f, false);
        }
        mPath.close();
    }

    private void setOvalByXY(int centerX, int centerY, int radius) {
        oval.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        generatePath(width, height);
        mDrawable.setBounds(0, 0, width, height);
        canvas.clipPath(mPath);
        mDrawable.draw(canvas);

        generateLinePath(width, height, mStrokeWidth);
        canvas.drawPath(mLinePath, mLinePaint);
        super.dispatchDraw(canvas);
    }

    private void generateLinePath(int width, int height, float strokeWidth) {
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStrokeWidth(strokeWidth);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeCap(Paint.Cap.BUTT);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{4, 8}, 0));
        if (mDirection == DIRECTION_RIGHT){
            mLinePath.moveTo(width, mRightTopRadius);
            mLinePath.rLineTo(0, height - 2 * mRightTopRadius);
        }else {
            mLinePath.moveTo(0, mLeftTopRadius);
            mLinePath.rLineTo(0, height - 2 * mLeftTopRadius);
        }
    }
}
