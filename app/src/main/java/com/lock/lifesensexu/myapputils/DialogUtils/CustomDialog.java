package com.lock.lifesensexu.myapputils.DialogUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.lock.lifesensexu.myapputils.R;

import java.lang.reflect.Method;


public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int width, int height, int layou) {

        super(context, R.style.Theme_dialog);
        // set content
        setContentView(layou);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        params.width = width;
        params.height = height;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    private CustomDialog(Context context, int width, int height, int layou,
                         int gravity) {

        super(context, R.style.Theme_dialog);
        // set content
        setContentView(layou);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        window.setAttributes(params);
    }

    public CustomDialog(Context context, int layou) {

        super(context, R.style.Theme_dialog);

//        // Full Screen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//// No Titlebar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set content
        setContentView(layou);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    public static CustomDialog createBigDialog(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels * 0.85);
        int h = (int) (dm.heightPixels * 0.55);

        return new CustomDialog(context, w, h, layou);
    }

    public static CustomDialog createStandardDialog(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels * 0.85);

        int h = (int) (dm.heightPixels * 0.55);
        return new CustomDialog(context, w, h, layou);
    }

    public static CustomDialog createStandardDialog2(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels * 0.85);
        return new CustomDialog(context, w, LayoutParams.WRAP_CONTENT, layou);
    }

    public static CustomDialog createMicroDialog(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels * 0.70);
        int h = (int) (dm.heightPixels * 0.40);

        return new CustomDialog(context, w, h, layou);
    }

    public static CustomDialog createMenuDialog(Context context, int layou) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels);
        int h = (int) (LayoutParams.WRAP_CONTENT);

        return new CustomDialog(context, w, h, layou, Gravity.BOTTOM);
    }

    public static BlurDialog createMaxDialog(final Activity context, int layout) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels);
        int h = (int) (dm.heightPixels);

        BlurDialog dialog = new BlurDialog(context, layout);//CustomDialog(context, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, layou, Gravity.CENTER);
//        dialog.setDialogView(context, layout);
//        context.getWindow().getDecorView().setSystemUiVisibility()
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });
        return dialog;
    }

    public static int getDpi(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        int height = 0;
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            height = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    public static int[] getScreenWH(Context poCotext) {
        WindowManager wm = (WindowManager) poCotext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return new int[]{width, height};
    }

    public static int getVrtualBtnHeight(Context poCotext) {
        int location[] = getScreenWH(poCotext);
        int realHeiht = getDpi((Activity) poCotext);
        int virvalHeight = realHeiht - location[1];
        return virvalHeight;
    }

    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

    private double getw(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    private double geth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    private double getScreenSize(Context context) {
        double dps = Math.sqrt(Math.pow(getw(context), 2)
                + Math.pow(geth(context), 2));
        return dps / (160 * getDensity(context));
    }

    public void unCancelShow() {
        this.setCancelable(false);
        this.show();
    }

    public void cancelDismiss() {
        if (isShowing()) {
            this.setCancelable(true);
            this.dismiss();
        }
    }
}

