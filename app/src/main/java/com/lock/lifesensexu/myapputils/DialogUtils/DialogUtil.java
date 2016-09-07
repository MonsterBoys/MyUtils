package com.lock.lifesensexu.myapputils.DialogUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lock.lifesensexu.myapputils.R;

/**
 * Created by lifesensexu on 16/9/7.
 */
public class DialogUtil {
    public static DialogUtil dialogUtil;
    private PopupWindow mPopupWindow = null;
    private Dialog dialog;
    private View view;
    public static DialogUtil getInstance() {
        if (dialogUtil == null) {
            dialogUtil = new DialogUtil();
        }
        return dialogUtil;
    }

    public View getPopView() {
        return view;
    }

    public Dialog getDialog() {
        return dialog;
    }
    public void showBottomPop(Activity context, int layoutId,int view_layoutId) {
        // 一个自定义的布局，作为显示的内容
        view = LayoutInflater.from(context).inflate(
                layoutId, null);
        LinearLayout layout_pic = (LinearLayout) view.findViewById(view_layoutId);
        layout_pic.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_bottom_in));

        mPopupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
//        mPopupWindow.setAnimationStyle(R.style.popupwindow_anim);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        View parentView = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        mPopupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 提示框，只有一个按钮
     * @param context
     * @param title
     * @param content
     * @param btnText
     * @param onClickListener
     * @param isCancelable
     */
    public void showTipsDialog(Context context, String title, String content, String btnText, View.OnClickListener onClickListener, boolean isCancelable) {
        dismissProcessDialog();
        dialog = CustomDialog.createStandardDialog(context, R.layout.dialog_tips);
        TextView title_tv = (TextView) dialog.findViewById(R.id.dhof_title_tv);
        TextView dhof_subTitle_tv = (TextView) dialog.findViewById(R.id.dhof_subTitle_tv);
        TextView dhof_confirm_tv = (TextView) dialog.findViewById(R.id.dhof_confirm_tv);
        dialog.findViewById(R.id.umeng_update_id_cancel).setVisibility(View.GONE);
        dialog.findViewById(R.id.update_btn_line).setVisibility(View.GONE);


        title_tv.setText(title);
        dhof_subTitle_tv.setText(content);
        dhof_confirm_tv.setText(btnText);
        dhof_confirm_tv.setOnClickListener(onClickListener);
        dialog.setCancelable(isCancelable);
        dialog.show();

    }

    /**
     * 提示框，有二个按钮
     * @param context
     * @param title
     * @param content
     * @param btnText
     * @param onClickListener
     * @param isCancelable
     */
    public void showTipsTwoDialog(Context context, String title, String content, String btnText, View.OnClickListener onClickListener, boolean isCancelable) {
        dismissProcessDialog();
        dialog = CustomDialog.createStandardDialog(context, R.layout.dialog_tips);
        TextView title_tv = (TextView) dialog.findViewById(R.id.dhof_title_tv);
        TextView dhof_subTitle_tv = (TextView) dialog.findViewById(R.id.dhof_subTitle_tv);
        TextView dhof_confirm_tv = (TextView) dialog.findViewById(R.id.dhof_confirm_tv);
        title_tv.setText(title);
        dhof_subTitle_tv.setText(content);
        dhof_confirm_tv.setText(btnText);
        dhof_confirm_tv.setOnClickListener(onClickListener);
        dialog.setCancelable(isCancelable);

        dialog.findViewById(R.id.umeng_update_id_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissProcessDialog();
            }
        });
        dialog.show();

    }
    /**
     * @param context
     * @param isCancelable 设置为false，按返回键不能退出。默认为true。
     */
    public void showProcessDialog(Context context, boolean isCancelable, int layoutId) {
        dismissProcessDialog();
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = (int) (dm.widthPixels * 0.5);
        int h = (int) (dm.heightPixels * 0.2);
//        dialog = new CustomDialog(context, w, h, R.layout.dialog_hint_loading);
        dialog = CustomDialog.createStandardDialog(context, layoutId);
        dialog.setCancelable(isCancelable);
        dialog.show();
    }
    public void dismissProcessDialog() {
        Log.e("Dialog ", "dismissProcessDialog");
        if (dialog != null) {
            boolean isDismiss = false;
            if (dialog.isShowing() && dialog.getContext() != null
                    && dialog.getContext() instanceof Activity) { // 3重保护
                Activity activity = (Activity) dialog.getContext();
                if (!activity.isFinishing()) { // 依附的activity未结束
                    dialog.dismiss();
                    isDismiss = true;
                }
            }
            if (!isDismiss) {// 如果没经过上述流程，则try catch 处理
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        dialog = null;
    }
}
