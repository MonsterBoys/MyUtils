package com.lock.lifesensexu.myapputils.viewPagerModels;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lock.lifesensexu.myapputils.R;


public class ViewPagerTransFormerActivity extends AppCompatActivity {
private Context mContext=ViewPagerTransFormerActivity.this;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    int[] imgRes={R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_trans_former);
        initView();
        setDatas();
    }

    private void setDatas() {
        mViewPager.setPageMargin(40);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(false,new AlphaPageTransformer());
        mViewPager.setAdapter(mAdapter=new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView=new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(imgRes[position]);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View)object);

            }

            @Override
            public int getCount() {
                return imgRes.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
    }
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
    }


}
