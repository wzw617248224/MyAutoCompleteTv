package com.detolv.myautocompletetv.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.detolv.myautocompletetv.R;

import java.util.ArrayList;
import java.util.List;

public class StepsViewIndicator extends View {

    //定义默认的高度
    private int defaultStepIndicatorNum = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

    private float mCompletedLineHeight;//完成线的高度
    private float mCircleRadius;//圆的半径

    private Drawable mCompleteIcon;//完成的默认图片
    private Drawable mALLCompleteIcon;
    private Drawable mNotALLCompleteIcon;//部分完成的默认图片
    private Drawable mDefaultIcon;//默认的背景图
    private float mLeftY;//左上方的Y位置
    private float mRightY;//右下方的位置

    private int mStepNum = 0;//当前有几部流程
    private float mLinePadding;

    private List<Float> mComplectedXPosition;//定义完成时当前view在左边的位置
    private Paint mUnCompletedPaint;//未完成Paint
    private Paint mCompletedPaint;//完成paint
    private Paint mCompletedCirclePaint;//完成paint
    private int mUnCompletedLineColor = ContextCompat.getColor(getContext(), R.color.uncompleted_color);//定义默认未完成线的颜色
    private int mCompletedLineColor = ContextCompat.getColor(getContext(), R.color.completed_color);//定义默认完成线的颜色
    private int mCompletedCirleColor = ContextCompat.getColor(getContext(), R.color.completed_circle_color);//定义默认完成圆的填充颜色

    private int mComplectingPosition;//正在进行position
    private Path mPath;

    private OnDrawIndicatorListener mOnDrawListener;
    private boolean isPlayAnimation;
    private float expandPercent;

    private boolean isPlayTickAnimation;
    private Paint mCompletedTickPaint;
    private int padding;
    private float length;
    private float mCenterX;
    private float mCenterY;

    /**
     * 设置监听
     * @param onDrawListener
     */
    public void setOnDrawListener(OnDrawIndicatorListener onDrawListener) {
        mOnDrawListener = onDrawListener;
    }

    /**
     * get小圆的半径
     *
     * @return
     */
    public float getCircleRadius() {
        return mCircleRadius;
    }

    public StepsViewIndicator(Context context) {
        this(context, null);
    }

    public StepsViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsViewIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init
     */
    private void init() {
        mPath = new Path();

        mComplectedXPosition = new ArrayList<>();//初始化

        mUnCompletedPaint = new Paint();
        mUnCompletedPaint.setAntiAlias(true);
        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mUnCompletedPaint.setStyle(Paint.Style.STROKE);
        mUnCompletedPaint.setStrokeWidth(4);

        mCompletedPaint = new Paint();
        mCompletedPaint.setAntiAlias(true);
        mCompletedPaint.setColor(mCompletedLineColor);
        mCompletedPaint.setStyle(Paint.Style.FILL);

        mCompletedCirclePaint = new Paint();
        mCompletedCirclePaint.setAntiAlias(true);
        mCompletedCirclePaint.setColor(mCompletedCirleColor);
        mCompletedCirclePaint.setStyle(Paint.Style.FILL);

        mCompletedTickPaint = new Paint();
        mCompletedTickPaint.setAntiAlias(true);
        mCompletedTickPaint.setStrokeWidth(4f);
        mCompletedTickPaint.setColor(mCompletedLineColor);
        mCompletedTickPaint.setStrokeCap(Paint.Cap.ROUND);
        mCompletedTickPaint.setStyle(Paint.Style.STROKE);

        //已经完成线的宽高
        mCompletedLineHeight = 0.1f * defaultStepIndicatorNum;
        //圆的半径
        mCircleRadius = 0.275f * defaultStepIndicatorNum;
        //线与线之间的间距
        mLinePadding = 5.425f * defaultStepIndicatorNum;

        mCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.repayment_saving_card_completed_icon);
        mDefaultIcon = ContextCompat.getDrawable(getContext(), R.drawable.repayment_saving_card_default_icon);
        mALLCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.repayment_all_complete_icon);
        mNotALLCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.repayment_not_all_complete_icon);
        padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6.5f, getResources().getDisplayMetrics());
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = defaultStepIndicatorNum * 2;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        int height = defaultStepIndicatorNum * 2;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取中间的高度
        mCenterY = 0.5f * getHeight();
        //获取左上方Y的位置，获取该点的意义是为了方便画矩形左上的Y位置
        mLeftY = mCenterY - (mCompletedLineHeight / 2);
        //获取右下方Y的位置，获取该点的意义是为了方便画矩形右下的Y位置
        mRightY = mCenterY + mCompletedLineHeight / 2;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCompletedPaint.setStyle(Paint.Style.FILL);
        //-----------------------画线---------------------------------------------------------------
        for (int i = 0; i < mComplectedXPosition.size() - 1; i++) {
            //前一个ComplectedXPosition
            final float preComplectedXPosition = mComplectedXPosition.get(i);
            //后一个ComplectedXPosition
            final float afterComplectedXPosition = mComplectedXPosition.get(i + 1);

            if (i < mComplectingPosition)//判断在完成之前的所有点
            {
                //判断在完成之前的所有点，画完成的线，这里是矩形
                canvas.drawRect(preComplectedXPosition + mCircleRadius - 10, mLeftY, afterComplectedXPosition - mCircleRadius + 10, mRightY, mCompletedPaint);
            } else {
                mPath.moveTo(preComplectedXPosition + mCircleRadius, mCenterY);
                mPath.lineTo(afterComplectedXPosition - mCircleRadius, mCenterY);
                canvas.drawPath(mPath, mUnCompletedPaint);
            }
        }

        //-----------------------画图标--------------------------------------------------------------
        for (int i = 0; i < mComplectedXPosition.size(); i++) {
            float currentComplectedXPosition = mComplectedXPosition.get(i);
            int left = (int) (currentComplectedXPosition - mCircleRadius);
            int top = (int) (mCenterY - mCircleRadius);
            int right = (int) (currentComplectedXPosition + mCircleRadius);
            int bottom = (int) (mCenterY + mCircleRadius);
            if (i != mStepNum - 1){
                if (i <= mComplectingPosition) {
                    drawIconWithBounds(canvas, mCompleteIcon, left, top, right, bottom);
                } else {
                    drawIconWithBounds(canvas, mDefaultIcon, left, top, right, bottom);
                }
            }
        }

        if (!mComplectedXPosition.isEmpty()) {
            int positonIndex = mStepNum - 1;
            mCenterX = mComplectedXPosition.get(positonIndex);
            int left = (int) (mCenterX - mCircleRadius) - padding;
            int top = (int) (mCenterY - mCircleRadius) - padding;
            int right = (int) (mCenterX + mCircleRadius) + padding;
            int bottom = (int) (mCenterY + mCircleRadius) + padding;
            if (mComplectingPosition == positonIndex && isPlayAnimation) {
                //最后一步已完成 显示完成按钮
                float strokeWidth = 3f;
                mCompletedPaint.setStrokeWidth(strokeWidth);
                mCompletedPaint.setStyle(Paint.Style.STROKE);
                float radius = (mCircleRadius + padding) * expandPercent;
                canvas.drawCircle(mCenterX, mCenterY, radius, mCompletedPaint);
                canvas.drawCircle(mCenterX, mCenterY, radius - strokeWidth/2, mCompletedCirclePaint);
                //先展示圆，后画勾，这里做下判断
                if (isPlayTickAnimation) {
                    canvas.drawPath(mPath, mCompletedTickPaint);
                }
            } else if (mComplectingPosition != positonIndex){
                drawIconWithBounds(canvas, mNotALLCompleteIcon, left, top, right, bottom);
            }else {
                drawIconWithBounds(canvas, mALLCompleteIcon, left, top, right, bottom);
            }
        }
        mOnDrawListener.ondrawIndicator();
    }

    private void drawIconWithBounds(Canvas canvas, Drawable drawable, int left, int top, int right, int bottom) {
        drawable.setBounds(left, top, right, bottom);
        drawable.draw(canvas);
    }

    private Path generatePath(float mCenterX, float mCenterY, float mStaticRadius) {
        Path path = new Path();
        path.moveTo(mCenterX - mStaticRadius/2, mCenterY);
        path.lineTo((25/56f) * mStaticRadius * 2 + mCenterX - mStaticRadius, (37/56f) * mStaticRadius * 2 + mCenterY - mStaticRadius);
        path.lineTo((40/56f) * mStaticRadius * 2 + mCenterX - mStaticRadius, (22/56f) * mStaticRadius * 2 + mCenterY - mStaticRadius);
        return path;
    }

    /**
     * 得到所有圆点所在的位置
     *
     * @return
     */
    public List<Float> getComplectedXPosition() {
        return mComplectedXPosition;
    }

    private void caculateXPostion() {
        init();
        for (int i = 0; i < mStepNum; i++) {
            //先计算全部最左边的padding值（getWidth()-（圆形直径+两圆之间距离）*2）
            float paddingLeft = (getWidth() - mStepNum * mCircleRadius * 2 - (mStepNum - 1) * mLinePadding) / 2;
            mComplectedXPosition.add(paddingLeft + mCircleRadius + i * mCircleRadius * 2 + i * mLinePadding);
        }
    }

    /**
     * 设置流程步数
     *
     * @param stepNum 流程步数
     */
    public void setStepNum(int stepNum) {
        this.mStepNum = stepNum;
        caculateXPostion();
        invalidate();
    }

    /**
     * 设置正在进行position
     *
     * @param complectingPosition
     */
    public void setComplectingPosition(int complectingPosition) {
        this.mComplectingPosition = complectingPosition;
        caculateXPostion();
        invalidate();
    }

    /**
     * 设置未完成线的颜色
     *
     * @param unCompletedLineColor
     */
    public void setUnCompletedLineColor(int unCompletedLineColor) {
        this.mUnCompletedLineColor = unCompletedLineColor;
    }

    /**
     * 设置已完成线的颜色
     *
     * @param completedLineColor
     */
    public void setCompletedLineColor(int completedLineColor) {
        this.mCompletedLineColor = completedLineColor;
    }

    /**
     * 设置默认图片
     *
     * @param defaultIcon
     */
    public void setDefaultIcon(Drawable defaultIcon) {
        this.mDefaultIcon = defaultIcon;
    }

    /**
     * 设置已完成图片
     *
     * @param completeIcon
     */
    public void setCompleteIcon(Drawable completeIcon) {
        this.mCompleteIcon = completeIcon;
    }

    public void onPlayAnimation() {
        isPlayAnimation = true;
        ObjectAnimator expandAnimator = ObjectAnimator.ofFloat(this, "expandPercent", 0.0f, 1.0f);
        expandAnimator.setInterpolator(new OvershootInterpolator());
        expandAnimator.setDuration(500L);

        ObjectAnimator tickAnimator = ObjectAnimator.ofFloat(this, "tickPercent", 0.0f, 1.0f);
        tickAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        tickAnimator.setDuration(350L);
        tickAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //确定勾号三个点的坐标
                isPlayTickAnimation = true;
                mPath = generatePath(mCenterX, mCenterY, mCircleRadius + padding);
                PathMeasure measure = new PathMeasure(mPath, false);
                length = measure.getLength();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(expandAnimator, tickAnimator);
        animatorSet.start();
    }

    public void setExpandPercent(float expandPercent) {
        this.expandPercent = expandPercent;
        invalidate();
    }

    public void setTickPercent(float tickPercent) {
        if (length != 0){
            mCompletedTickPaint.setPathEffect(createPathEffect(length, tickPercent));
            invalidate();
        }
    }

    private PathEffect createPathEffect(float pathLength, float phase) {
        return new DashPathEffect(new float[]{pathLength, pathLength},
                pathLength - phase * pathLength);
    }

    /**
     * 设置对view监听
     */
    public interface OnDrawIndicatorListener {
        void ondrawIndicator();
    }
}
