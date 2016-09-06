package com.lock.lifesensexu.myapputils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lock.lifesensexu.myapputils.viewPagerModels.ViewPagerTransFormerActivity;
import com.lock.lifesensexu.myapputils.viewPagerModels.ViewPagerUseActivity;

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
    }
    @Override
    protected void initHeader() {
        setHeaderBackground(R.color.main_blue);
        setHeader_LeftImage(R.mipmap.btn_back);
    }

}
