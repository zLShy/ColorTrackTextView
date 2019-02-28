package com.custom.day3;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager manager;
    TextView mSensorTv;
    private CircleView mCicleView;
    private ColorTrackTextView mColorTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorTv = (TextView) findViewById(R.id.sensor_tv);
        mColorTv = (ColorTrackTextView) findViewById(R.id.color_tv);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        HashMap hashMap

        mCicleView = (CircleView) findViewById(R.id.circle_view);
        mCicleView.setmStepMax(5000);
        ValueAnimator animator =  ValueAnimator.ofInt(0,3500);
        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentSteps = (int) animation.getAnimatedValue();
                mCicleView.setmCurrentSteps(currentSteps);
            }
        });

        animator.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        StringBuilder sb = new StringBuilder();
        sb.append("X方向上的加速度：");
        sb.append(values[0]);
        sb.append("\nY方向上的加速度：");
        sb.append(values[1]);
        sb.append("\nZ方向上的加速度：");
        sb.append(values[2]);
        mSensorTv.setText(sb.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //左到右
    public void leftToRight(View view) {

        mColorTv.setmDrection(ColorTrackTextView.Drections.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                mColorTv.setmProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }
    public void rightToLeft(View view) {
        mColorTv.setmDrection(ColorTrackTextView.Drections.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                mColorTv.setmProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }
}
