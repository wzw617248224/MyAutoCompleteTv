package com.detolv.myautocompletetv.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.detolv.myautocompletetv.R;
import com.detolv.myautocompletetv.utils.DisplayUtils;

/**
 * 上下可拖动的ScrollView
 */
public class DragScrollView extends FrameLayout {
    private View mInner;// 子View
    private float y;// 点击时y坐标
    private float y1;// 点击时y坐标
    private float y2;// 抬起时y坐标
    private float movePercent;
    private float maxMove;
    private Rect mNormal = new Rect();// 矩形(这里只是个形式，只是用于判断是否需要动画.)
    private boolean isCount = false;// 是否开始计算
    private boolean isMoveing = false;// 是否开始移动.
    private int mScrollInTopAre = 0;
    private int maxInner_move_H = 50;
    private  boolean isPointDirctionUp=false;
    /** 是否在动画中 */
    private boolean isInnerAnimatorRunning = false;
    public static final int RESISTANCE=4;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        maxMove = getMeasuredHeight() * 2 / 3;
    }

    public DragScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScrollInTopAre = (int) getResources().getDimension(R.dimen.dimen_100_dip);
        maxInner_move_H = DisplayUtils.dip2px(context, maxInner_move_H);
    }

    /***
     * 根据 XML 生成视图工作完成.该函数在生成视图的最后调用，在所有子视图添加完之后. 即使子类覆盖了 onFinishInflate
     * 方法，也应该调用父类的方法，使该方法得以执行.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            mInner = getChildAt(0);
        }
    }

    /**
     * touch 事件处理
     **/
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        int deltaY ;
        isMoveing = false;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                y1 = ev.getY();
                y = ev.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isMoveing = false;
                y = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;// 按下时的y坐标
                float nowY = ev.getY();// 时时y坐标
                deltaY = (int) (nowY - preY);// 滑动距离
                if (deltaY > 0) {
                    isPointDirctionUp = false;
                } else {
                    isPointDirctionUp = true;
                }
                y = nowY;
                break;
        }

        if (isMoveing) {
            return true;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
        if (mInner != null) {
            commOnTouchEvent(ev);
        }
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /***
     * 触摸事件
     *
     * @param ev
     */
    public void commOnTouchEvent(MotionEvent ev) {
        if (isInnerAnimatorRunning) {
            return;
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                y2 = ev.getY();
                y = 0;
                isMoveing = false;
                // 手指松开.
                if (isNeedAnimation()) {
                    animation();
                }
                break;
            /***
             * 排除出第一次移动计算，因为第一次无法得知y坐标， 在MotionEvent.ACTION_DOWN中获取不到，
             * 因为此时是MyScrollView的touch事件传递到到了LIstView的孩子item上面.所以从第二次计算开始.
             * 然而我们也要进行初始化，就是第一次移动的时候让滑动距离归0. 之后记录准确了就正常执行.
             */
            case MotionEvent.ACTION_MOVE:

                final float preY = y;// 按下时的y坐标
                float nowY = ev.getY();// 时时y坐标
                int deltaY = (int) (nowY - preY);// 滑动距离
                if (!isCount) {
                    deltaY = 0; // 在这里要归0.
                }
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                // 初始化头部矩形
                if (mNormal.isEmpty()) {
                    // 保存正常的布局位置
                    mNormal.set(mInner.getLeft(), mInner.getTop(), mInner.getRight(), mInner.getBottom());
                }

                // 移动布局
                int  innerMoveHeight = deltaY / RESISTANCE;
                mInner.layout(mInner.getLeft(), mInner.getTop() + innerMoveHeight, mInner.getRight(), mInner.getBottom() +innerMoveHeight);
                movePercent = (nowY - y1) / maxMove;
                isCount = true;
                y = nowY;
                break;
        }
    }

    /***
     * 回缩动画
     */
    /***
     * 回缩动画
     */
    public void animation() {
        // 开启移动动画
        final int endValue = mNormal.top;
        ValueAnimator innerAnimator = ValueAnimator.ofInt(mInner.getTop(), endValue);
        innerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                mInner.layout(mInner.getLeft(), value, mInner.getRight(), value + mInner.getMeasuredHeight());
            }
        });

        innerAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isInnerAnimatorRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isInnerAnimatorRunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        innerAnimator.setDuration(200);
        innerAnimator.start();
        mNormal.setEmpty();
        /** 动画执行 **/


    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !mNormal.isEmpty();
    }
}