package com.lock.lifesensexu.myapputils.view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lock.lifesensexu.myapputils.R;


/**
 * Created by HomeDa on 16/2/23.
 */
public class PullZoomListView extends ListView {

    /*头部View 的容器*/
    private LinearLayout mHeaderContainer;
    /*头部View 的图片*/
    private ImageView mHeaderImg;
    /*屏幕的高度*/
    private int mScreenHeight;
    /*屏幕的宽度*/
    private int mScreenWidth;

    private int mHeaderHeight;

    /*无效的点*/
    private static final int INVALID_POINTER = -1;
    /*滑动动画执行的时间*/
    private static final int MIN_SETTLE_DURATION = 200; // ms
    /*定义了一个时间插值器，根据ViewPage控件来定义的*/
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    /*记录上一次手指触摸的点*/
    private float mLastMotionX;
    private float mLastMotionY;

    /*当前活动的点Id,有效的点的Id*/
    protected int mActivePointerId = INVALID_POINTER;
    /*开始滑动的标志距离*/
    private int mTouchSlop;

    private float mScale;
    private float mLastScale;

    private final float mMaxScale = 2.0f;

    private boolean isNeedCancelParent;

    private OnScrollListener mScrollListener;

    private final float REFRESH_SCALE = 1.20F;

    private OnRefreshListener mRefreshListener;

    private onLoadMoreListener onLoadMoreListener;
    private boolean loadMoreEnadle;
    private boolean isLoading;
    private int mFootViewHeight; // header view's height
    private LinearLayout mFootViewContent;

    private Context context;

    private View topView;
    private PinnedListViewFooter mFooterView;
    private int mHeaderImgHeight;
 //   private int mmvpHeight;
 //   private View mvp;

    public PullZoomListView(Context context) {
        super(context);
//        init(context);
    }

    public PullZoomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(context);
    }

    public PullZoomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init(context);
    }

    public void init(final Context context,View view,final ImageView imageView) {
        this.context = context;
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mHeaderContainer=(LinearLayout) view;
        mHeaderContainer.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderHeight = mHeaderContainer.getMeasuredHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        mHeaderImg=imageView;
        mHeaderImg.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderImgHeight = mHeaderImg.getMeasuredHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        addHeaderView(mHeaderContainer);
        super.setOnScrollListener(new InternalScrollerListener());
        mFooterView = new PinnedListViewFooter(context);
        addFooterView(mFooterView);
        mFootViewContent = (LinearLayout) mFooterView
                .findViewById(R.id.xlistview_header_content);
        mFootViewContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mFootViewHeight = mFootViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }

    public void addView(View view) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//        int margin = UiUtils.dipToPx(context, 8);
//        layoutParams.setMargins(margin, margin, margin, margin);
        view.setLayoutParams(layoutParams);
        mHeaderContainer.addView(view);
    }

    public void setmHeaderImg(ImageView img){
        this.mHeaderImg=img;
        img.setBackgroundColor(context.getResources().getColor(R.color.group_headbackground));
        mHeaderContainer.addView(mHeaderImg);
        addHeaderView(mHeaderContainer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                /*计算 x，y 的距离*/
                int index = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                if (mActivePointerId == INVALID_POINTER)
                    break;
                mLastMotionX = MotionEventCompat.getX(ev, index);
                mLastMotionY = MotionEventCompat.getY(ev, index);
                // 结束动画
                abortAnimation();
                mLastScale = (this.mHeaderContainer.getBottom() / this.mHeaderHeight);
                isNeedCancelParent = true;
  //              Log.e("jiale", "ACTION_DOWN       index "+index +" mActivePointerId:"+mActivePointerId +" mLastScale"+mLastScale +" mLastMotionY"+mLastMotionY);
                break;
            case MotionEvent.ACTION_MOVE:
                int indexMove = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, indexMove);
 //               Log.e("jiale", "ACTION_DOWN      mHeaderContainer.getBottom()"+mHeaderContainer.getBottom() +" mHeaderHeight:"+mHeaderHeight +" mLastScale"+mLastScale +" mLastMotionY"+mLastMotionY);
                if (mActivePointerId == INVALID_POINTER) {
                    /*这里相当于松手*/
                    finishPull();
                    isNeedCancelParent = true;
                } else {
                    if (mHeaderContainer.getBottom() >= mHeaderHeight && ev.getRawY()-mLastMotionY>0) {
                        ViewGroup.LayoutParams params = this.mHeaderImg
                                .getLayoutParams();
//                        ViewGroup.LayoutParams mvp_params = this.mvp
//                                .getLayoutParams();
                        final float y = MotionEventCompat.getY(ev, indexMove);
                        float dy = y - mLastMotionY;
                        float f = ((y - this.mLastMotionY + this.mHeaderContainer
                                .getBottom()) / this.mHeaderHeight - this.mLastScale)
                                / 2.0F + this.mLastScale;
                        if ((this.mLastScale <= 1.0D) && (f <= this.mLastScale)) {
                            params.height = this.mHeaderImgHeight;
                            this.mHeaderImg.setLayoutParams(params);
                            Log.e("jiale", "onTouchEvent: 1" );
                            return super.onTouchEvent(ev);
                        }
                        /*这里设置紧凑度*/
                        dy = dy * 0.5f * (mHeaderImgHeight * 1.0f / params.height);
                        mLastScale = (dy + params.height) * 1.0f / mHeaderImgHeight;
                        mScale = clamp(mLastScale, 1.0f, mMaxScale);
//                        Log.v("zgy", "=======mScale=====" + mLastScale+",f = "+f);

                        params.height = (int) (mHeaderImgHeight * mScale);
                        mHeaderImg.setLayoutParams(params);
 //                       mvp.setPadding(0,params.height,0,0);
                        mLastMotionY = y;
                        Log.e("jiale", "onTouchEvent: 2" );
                        if (isNeedCancelParent) {
                            isNeedCancelParent = false;
                            MotionEvent motionEvent = MotionEvent.obtain(ev);
                            motionEvent.setAction(MotionEvent.ACTION_CANCEL);
                            Log.e("jiale", "onTouchEvent: 3" );
                            super.onTouchEvent(motionEvent);
                        }
                        Log.e("jiale", "onTouchEvent: 4" );
                        return true;
                    }
                //    mLastMotionY = MotionEventCompat.getY(ev, indexMove);

                }
          //      Log.e("jiale", "ACTION_MOVE    mActivePointerId:"+mActivePointerId +" mLastScale"+mLastScale +" mLastMotionY"+mLastMotionY);
                break;
            case MotionEvent.ACTION_UP:
                if (!isNeedCancelParent) {
                    finishPull();
                }

                break;
            case MotionEvent.ACTION_POINTER_UP:

                int pointUpIndex = MotionEventCompat.getActionIndex(ev);
                int pointId = MotionEventCompat.getPointerId(ev, pointUpIndex);
                if (pointId == mActivePointerId) {
                    /*松手执行结束拖拽操作*/
                    finishPull();
                }

                break;

        }
        Log.e("jiale", "onTouchEvent: 5" );
        return super.onTouchEvent(ev);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    private void abortAnimation() {

    }

    private void finishPull() {
        mActivePointerId = INVALID_POINTER;
        if (mHeaderContainer.getBottom() > mHeaderHeight) {
//            Log.v("zgy", "===super====onTouchEvent========");
            if (mScale > REFRESH_SCALE) {
                if (mRefreshListener != null && !isLoading) {
                    mRefreshListener.onRefresh();
                }
            }
            pullBackAnimation();
        }
    }

    private void pullBackAnimation() {
        ValueAnimator pullBack = ValueAnimator.ofFloat(mScale, 1.0f);
        pullBack.setInterpolator(sInterpolator);
        pullBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = mHeaderImg.getLayoutParams();
                params.height = (int) (mHeaderImgHeight * value);
                mHeaderImg.setLayoutParams(params);
            }
        });
        pullBack.setDuration((long) (MIN_SETTLE_DURATION * mScale));
        pullBack.start();

    }


    /**
     * 通过事件和点的 id 来获取点的索引
     *
     * @param ev
     * @param id
     * @return
     */
    private int getPointerIndex(MotionEvent ev, int id) {
        int activePointerIndex = MotionEventCompat.findPointerIndex(ev, id);
        if (activePointerIndex == -1)
            mActivePointerId = INVALID_POINTER;
        return activePointerIndex;
    }


    public void setOnRefreshListener(OnRefreshListener l) {
        mRefreshListener = l;
    }

    public LinearLayout getHeaderContainer() {
        return mHeaderContainer;
    }

    public ImageView getHeaderImageView() {
        return this.mHeaderImg;
    }


    private float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    private class InternalScrollerListener implements OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            if (mScrollListener != null) {
                mScrollListener.onScrollStateChanged(view, scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            float diff = mHeaderHeight- mHeaderContainer.getBottom();
            if ((diff > 0.0F) && (diff < mHeaderHeight)) {
                int i = (int) (0.06D * diff);
                mHeaderImg.scrollTo(0, -i);
            } else if (mHeaderImg.getScrollY() != 0) {
                mHeaderImg.scrollTo(0,0);
            }
            if (mScrollListener != null) {
                mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            if (topView != null && firstVisibleItem == 0) {
                if (diff > mHeaderHeight / 2) {
                    diff = diff - mHeaderHeight / 2;
                    int f = Math.min((int) (2.55f * diff), 255);
                    topView.getBackground().setAlpha(f);
                } else {
                    topView.getBackground().setAlpha(0);
                }
            }

            //自动加载更多
            if (onLoadMoreListener != null && loadMoreEnadle && totalItemCount > visibleItemCount && !isLoading) {
                int lastVisibleItem = firstVisibleItem + visibleItemCount;
                if (totalItemCount == lastVisibleItem) {
                    mFootViewHeight = mFootViewContent.getHeight();
                    isLoading=true;
                    mFooterView.setVisiableHeight(mFootViewHeight);
                    mFooterView.setState(2);
                    onLoadMoreListener.onLoadMore();
                }
            }
        }
    }

    public void stopLoadMore() {
        isLoading=false;
        mFooterView.setState(0);
        mFooterView.resetHintTextView();
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface onLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreEnadle(boolean loadMoreEnadle) {
        this.loadMoreEnadle = loadMoreEnadle;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setOnLoadMoreListener(PullZoomListView.onLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void computeRefresh() {
        if (mActivePointerId != INVALID_POINTER) {

        }
    }

    public void setTopView(View topView) {
        this.topView = topView;
    }
}
