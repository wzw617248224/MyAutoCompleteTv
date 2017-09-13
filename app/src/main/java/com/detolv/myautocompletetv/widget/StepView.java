package com.detolv.myautocompletetv.widget;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.detolv.myautocompletetv.R;

import java.util.List;

public class StepView extends LinearLayout implements StepsViewIndicator.OnDrawIndicatorListener {

    private RelativeLayout mTextContainer;
    private StepsViewIndicator mStepsViewIndicator;
    private List<StepVo> stepVos;
    private int mComplectingPosition;
    private int mUnComplectedTextColor = ContextCompat.getColor(getContext(), R.color.uncompleted_text_color);//定义默认未完成文字的颜色;
    private int mComplectedTextColor = ContextCompat.getColor(getContext(), R.color.completed_text_color);//定义默认完成文字的颜色;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.repayment_saving_card_step_view_layout, this);
        mStepsViewIndicator = (StepsViewIndicator) rootView.findViewById(R.id.steps_indicator);
        mStepsViewIndicator.setOnDrawListener(this);
        mTextContainer = (RelativeLayout) rootView.findViewById(R.id.rl_text_container);
        mTextContainer.removeAllViews();
    }

    /**
     * 设置显示的文字
     *
     * @param texts
     * @return
     */
    public StepView setStepViewTexts(List<StepVo> texts) {
        stepVos = texts;
        mStepsViewIndicator.setStepNum(stepVos.size());
        return this;
    }

    /**
     * 设置正在进行的position
     *
     * @param complectingPosition
     * @return
     */
    public StepView setStepsViewIndicatorComplectingPosition(int complectingPosition) {
        mComplectingPosition = complectingPosition;
        mStepsViewIndicator.setComplectingPosition(complectingPosition);
        return this;
    }

    /**
     * 设置未完成文字的颜色
     *
     * @param unComplectedTextColor
     * @return
     */
    public StepView setStepViewUnComplectedTextColor(int unComplectedTextColor) {
        mUnComplectedTextColor = unComplectedTextColor;
        return this;
    }

    /**
     * 设置完成文字的颜色
     *
     * @param complectedTextColor
     * @return
     */
    public StepView setStepViewComplectedTextColor(int complectedTextColor) {
        this.mComplectedTextColor = complectedTextColor;
        return this;
    }

    /**
     * 设置StepsViewIndicator未完成线的颜色
     *
     * @param unCompletedLineColor
     * @return
     */
    public StepView setStepsViewIndicatorUnCompletedLineColor(int unCompletedLineColor) {
        mStepsViewIndicator.setUnCompletedLineColor(unCompletedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator完成线的颜色
     *
     * @param completedLineColor
     * @return
     */
    public StepView setStepsViewIndicatorCompletedLineColor(int completedLineColor) {
        mStepsViewIndicator.setCompletedLineColor(completedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator默认图片
     *
     * @param defaultIcon
     */
    public StepView setStepsViewIndicatorDefaultIcon(Drawable defaultIcon) {
        mStepsViewIndicator.setDefaultIcon(defaultIcon);
        return this;
    }

    /**
     * 设置StepsViewIndicator已完成图片
     *
     * @param completeIcon
     */
    public StepView setStepsViewIndicatorCompleteIcon(Drawable completeIcon) {
        mStepsViewIndicator.setCompleteIcon(completeIcon);
        return this;
    }

    public void startPlayAnimation() {
        mStepsViewIndicator.onPlayAnimation();
    }

    @Override
    public void ondrawIndicator() {
        List<Float> complectedXPosition = mStepsViewIndicator.getComplectedXPosition();

        if (stepVos != null) {
            for (int i = 0; i < stepVos.size(); i++) {
                View containerView = LayoutInflater.from(getContext()).inflate(R.layout.repayment_saving_card_step_view_item, null);
                TextView titleView = (TextView) containerView.findViewById(R.id.title_view);
                TextView timeView = (TextView) containerView.findViewById(R.id.time_view);
                titleView.setText(stepVos.get(i).getTitle());
                if (!stepVos.get(i).getTime().equals("")){
                    timeView.setText(stepVos.get(i).getTime());
                }

                int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                containerView.measure(width, height);

                containerView.setX(complectedXPosition.get(i) - mStepsViewIndicator.getCircleRadius() - containerView.getMeasuredWidth()/2 + 12);//这里的-10是将文字进行调整居中，稍后再动态修改

                if (i <= mComplectingPosition) {
                    titleView.setTextColor(mComplectedTextColor);
                } else {
                    titleView.setTextColor(mUnComplectedTextColor);
                }

                mTextContainer.addView(containerView);
            }
        }
    }
}
