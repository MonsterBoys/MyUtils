package com.lock.lifesensexu.myapputils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.lock.lifesensexu.myapputils.viewPagerModels.ViewPagerTransFormerActivity;
import com.lock.lifesensexu.myapputils.viewPagerModels.ViewPagerUseActivity;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCenterView(R.layout.activity_main);
        TextView to_viewPager = (TextView) findViewById(R.id.to_viewPager);
        TextView to_viewPager_use_activity = (TextView) findViewById(R.id.to_viewPager_use_activity);

        to_viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewPagerTransFormerActivity.class));
            }
        });
        to_viewPager_use_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewPagerUseActivity.class));
            }
        });
        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.yourCircularProgressbar);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.main_pull_wrong));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.text_green));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.activity_vertical_margin));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.activity_vertical_margin));
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(65, animationDuration); // Default duration = 1500ms
    }
    @Override
    protected void initHeader() {
        setHeaderBackground(R.color.main_blue);
        setHeader_LeftImage(R.mipmap.btn_back);
    }

}
