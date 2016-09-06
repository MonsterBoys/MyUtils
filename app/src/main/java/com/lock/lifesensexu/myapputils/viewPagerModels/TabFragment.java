package com.lock.lifesensexu.myapputils.viewPagerModels;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by lifesensexu on 16/9/6.
 */
public class TabFragment extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        TextView tv = new TextView(getActivity());
        tv.setTextSize(60);
        Random r = new Random();
        tv.setBackgroundColor(Color.argb(r.nextInt(120), r.nextInt(255),
                r.nextInt(255), r.nextInt(255)));
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);
        return tv;

    }

    public static TabFragment newInstance(String title)
    {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
}
