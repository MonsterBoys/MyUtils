package com.lock.lifesensexu.myapputils;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseActivity extends FragmentActivity {
    protected View layout_header;
    private RelativeLayout layout_all;
    private LinearLayout Layout_center;
    private TextView tv_title;
    private TextView tv_left;
    private TextView tv_right;
    private RelativeLayout layout_left;
    private LinearLayout layout_right;
    private ImageView iv_left;
    private ImageView iv_right;
    private RelativeLayout layout_more;
    private RelativeLayout layout_statistics;
    private ImageView iv_more;
    protected abstract void initHeader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initHeaderView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //增加沉浸通知栏效果
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (layout_header != null) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) layout_header.getLayoutParams();

                layoutParams.height += UiUtils.getStatusBarHeight(this);
//                padding 标题栏高度
                layout_header.setPadding(0, UiUtils.getStatusBarHeight(this), 0, 0);
//                layoutParams.setMargins(0, UiUtils.getStatusBarHeight(this), 0, 0);
                layout_header.setLayoutParams(layoutParams);
            }
        }
    }
    private void initHeaderView() {
        boolean result = true;
        layout_all = (RelativeLayout) findViewById(R.id.layout_all);
        Layout_center = (LinearLayout) findViewById(R.id.layout_center);
        layout_header = findViewById(R.id.layout_header);

        if (layout_header != null) {
            tv_title = (TextView) findViewById(R.id.tv_title);
            tv_left = (TextView) findViewById(R.id.tv_left);
            tv_right = (TextView) findViewById(R.id.tv_right);
            layout_left = (RelativeLayout) findViewById(R.id.layout_left);
            layout_right = (LinearLayout) findViewById(R.id.layout_right);
            iv_left = (ImageView) findViewById(R.id.iv_left);
            iv_right = (ImageView) findViewById(R.id.iv_right);
            layout_more = (RelativeLayout) findViewById(R.id.layout_more);
            layout_statistics = (RelativeLayout) findViewById(R.id.layout_statistics);
            iv_more = (ImageView) findViewById(R.id.iv_more);
            layout_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
    /**
     * 设置中间的显示内容
     *
     * @param layout
     */
    public void setCenterView(int layout) {
        Layout_center.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        View addView = inflater.inflate(layout, null);
        Layout_center.addView(addView, new ViewGroup.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        initHeaderView();
        initHeader();
    }

    protected void setHeader_Title(int resid) {
        if (resid > 0) {

            tv_title.setText(resid);
        }

    }

    protected void setHeader_Title(String text) {
        if (tv_title != null) {
            tv_title.setText(text);
        }
    }

    protected void setHeader_Title_Color(int color) {
        if (tv_title != null) {
            tv_title.setTextColor(color);
        }
    }

    protected void setHeader_LeftImage(int resid) {
        if (iv_left != null) {
            iv_left.setVisibility(View.VISIBLE);
            if (resid > 0) {
                iv_left.setImageResource(resid);
                layout_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        finish();
                        onBackPressed();

                    }
                });
            } else {
                iv_left.setImageBitmap(null);
            }
        }
    }

    protected void hideHeader_leftImage() {
        if (iv_left != null) {
            iv_left.setVisibility(View.GONE);
        }
    }

    protected void setHeader_LeftText(String text) {
        if (tv_left != null) {
            tv_left.setVisibility(View.VISIBLE);
            tv_left.setText(text);
        }
    }

    protected void setHeader_LeftClickListener(View.OnClickListener listener) {
        if (layout_left != null) {
            layout_left.setOnClickListener(listener);
        }
    }

    protected void setHeader_RightImage(int resid) {
        if (iv_right != null) {
            iv_right.setVisibility(View.VISIBLE);
            if (resid > 0) {
                iv_right.setImageResource(resid);
            }
        }
    }

    protected void setHeader_RightText(int resid) {
        if (tv_right != null) {
            tv_right.setVisibility(View.VISIBLE);
            if (resid > 0) {
                tv_right.setText(resid);
            }
        }
    }

    protected void setHeader_RightText(String text) {
        if (tv_right != null) {
            tv_right.setVisibility(View.VISIBLE);
            if (text != null) {
                tv_right.setText(text);
            }
        }
    }

    protected void setHeader_RightClickListener(View.OnClickListener listener) {
        if (layout_right != null) {
            layout_right.setOnClickListener(listener);
        }
    }


    protected void setHeaderVisibility(int visibility) {
        if (layout_header != null) {

            layout_header.setVisibility(visibility);
        }
    }

    protected void setHeaderBackground(int colorId) {
        layout_header.setBackgroundColor(getResources().getColor(colorId));
    }

    }

