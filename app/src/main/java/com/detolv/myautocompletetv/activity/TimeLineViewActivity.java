package com.detolv.myautocompletetv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.detolv.myautocompletetv.R;
import com.detolv.myautocompletetv.widget.StepView;
import com.detolv.myautocompletetv.widget.StepVo;

import java.util.ArrayList;
import java.util.List;

public class TimeLineViewActivity extends Activity {
    List<StepVo> voList = new ArrayList<>();
    private static final String TAG = "TimeLineViewActivity";

    private StepView stepView;

    public static void navigateTo(Activity activity) {
        Intent intent = new Intent(activity, TimeLineViewActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line_view);

        voList.add(new StepVo("", ""));
        voList.add(new StepVo("", ""));
        voList.add(new StepVo("", ""));
        stepView = (StepView) findViewById(R.id.step_view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stepView.setStepViewTexts(voList);
                stepView.setStepsViewIndicatorComplectingPosition(2);
                stepView.startPlayAnimation(500L);
            }
        }, 21L);

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
    }
}
