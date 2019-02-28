package com.custom.day3;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private LinearLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] mTitles = {"直播","视频","图片","段子","新闻","足球"};
    private List<ColorTrackTextView> mColorTvs= new ArrayList<ColorTrackTextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        mTabLayout = (LinearLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        initIndrection();
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PagerFragment.getInstance(mTitles[position]);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                ColorTrackTextView mLeft = mColorTvs.get(position);
                mLeft.setmDrection(ColorTrackTextView.Drections.RIGHT_TO_LEFT);
                mLeft.setmProgress(1-positionOffset);
                try {
                    ColorTrackTextView mRight = mColorTvs.get(position+1);
                    mRight.setmDrection(ColorTrackTextView.Drections.LEFT_TO_RIGHT);
                    mRight.setmProgress(positionOffset);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndrection() {
        for (int i=0;i<mTitles.length;i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setText(mTitles[i]);
            colorTrackTextView.setmChangePaint(Color.RED);
            colorTrackTextView.setLayoutParams(params);
            mTabLayout.addView(colorTrackTextView);
            mColorTvs.add(colorTrackTextView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
