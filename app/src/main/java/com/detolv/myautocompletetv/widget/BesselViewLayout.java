package com.detolv.myautocompletetv.widget;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zewei_wang on 2017/9/13.
 */

public class BesselViewLayout extends FrameLayout {
    private static final String TAG = "BesselView";
    private float mTouchStartX;
    private float mTouchStartY;
    private float mTouchCurX;
    private float mTouchCurY;
    private BesselView mBesselView;

    public BesselViewLayout(Context context) {
        this(context, null, 0);
    }

    public BesselViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BesselViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget();
    }

    private void initWidget() {
        this.post(new Runnable() {
            @Override
            public void run() {
                addBesselView();
            }
        });
    }

    private void addBesselView() {
        mBesselView = new BesselView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        mBesselView.setLayoutParams(params);
        addViewInternal(mBesselView);
    }

    private void addViewInternal(@NonNull View child) {
        super.addView(child);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartY = ev.getY();
                mTouchStartX = ev.getX();
                mTouchCurX = mTouchStartX;
                mTouchCurY = mTouchStartY;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, event.toString());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                mTouchCurX = mTouchStartX;
                mTouchCurY = mTouchStartY;
                return true;
            case MotionEvent.ACTION_MOVE:
                mTouchCurX = event.getX();
                mTouchCurY = event.getY();
                float dx = mTouchCurX - mTouchStartX;
                float dy = mTouchCurY - mTouchStartY;

                mBesselView.setOffsetWdith((int) mTouchCurX);
                mBesselView.setOffsetHeight((int) mTouchCurY);
                mBesselView.invalidate();

                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
