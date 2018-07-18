package com.example.wz.myscrollapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wz on 2018/7/18.
 */

public class CustomView extends LinearLayout{

    private final static String TAG="CustomView";

    private ViewDragHelper mViewDragHelper;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewDragHelper=ViewDragHelper.create(this,1.0f,new MyViewDragHelperCallBack());
    }

    class MyViewDragHelperCallBack extends  ViewDragHelper.Callback{

        /**
         *   这个是返回被横向移动的子控件child的左坐标left，和移动距离dx，我们可以根据这些值来返回child的新的left。
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            Log.e(TAG, "clampViewPositionHorizontal: "+"left:="+left+"dx:="+dx);
            if (getPaddingLeft() >left)
            {
                return getPaddingLeft();
            }

            if (getWidth()-child.getWidth()<left)
            {
                return getWidth()-child.getWidth();
            }

            return left;
        }

        /**
         * 换成了垂直方向的移动和top坐标。

         如果有垂直移动，这个也必须重写，要不默认返回0，也不能移动了。
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.e(TAG, "clampViewPositionVertical: "+"top:="+top+"dy:="+dy );
            if (getPaddingTop() > top)
            {
                return getPaddingTop();
            }

            if (getHeight()-child.getHeight()<top)
            {
                return  getHeight()-child.getHeight();
            }

            return top;
        }

        /**
         * 这个用来控制垂直移动的边界范围，单位是像素。
         * @param child
         * @return
         */
        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }
        /**
         * 和上面一样，就是是横向的边界范围。
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }

        /**
         * 判断是否是这个View 返回true就是 可以移动
         * @param child
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 当releasedChild被释放的时候，xvel和yvel是x和y方向的加速度
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        /**
         * changedView的位置发生变化时调用，我们可以在这里面控制View的显示位置和移动
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            switch (state) {
                case ViewDragHelper.STATE_DRAGGING:  // 正在被拖动
                    break;
                case ViewDragHelper.STATE_IDLE:  // view没有被拖拽或者 正在进行fling/snap
                    break;
                case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置
                    break;
            }
            super.onViewDragStateChanged(state);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_DOWN:
                mViewDragHelper.cancel(); // 相当于调用 processTouchEvent收到ACTION_CANCEL
                break;
        }

        /**
         * 检查是否可以拦截touch事件
         * 如果onInterceptTouchEvent可以return true 则这里return true
         */
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 处理拦截到的事件
         * 这个方法会在返回前分发事件
         */
        mViewDragHelper.processTouchEvent(event);
        return true;
    }


}
