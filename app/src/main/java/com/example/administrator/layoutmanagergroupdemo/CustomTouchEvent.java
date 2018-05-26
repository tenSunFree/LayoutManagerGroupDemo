package com.example.administrator.layoutmanagergroupdemo;

import android.app.Activity;
import android.view.MotionEvent;

/**
 * 模拟点击屏幕、滑动屏幕等操作
 */
public class CustomTouchEvent {

    /**
     * 模仿手机点击屏幕事件
     * @param x X坐标
     * @param y Y坐标
     * @param activity 传进去的活动对象
     */
    public static void setFingerClick(int x, int y, Activity activity){
        MotionEvent evenDownt = MotionEvent.obtain(System.currentTimeMillis(),
                System.currentTimeMillis() + 100, MotionEvent.ACTION_DOWN, x, y, 0);
        activity.dispatchTouchEvent(evenDownt);
        MotionEvent eventUp = MotionEvent.obtain(System.currentTimeMillis(),
                System.currentTimeMillis() + 100, MotionEvent.ACTION_UP, x, y, 0);
        activity.dispatchTouchEvent(eventUp);
        evenDownt.recycle();
        eventUp.recycle();
    }
}
