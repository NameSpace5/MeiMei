package com.meiyue.meimei.view;

import android.content.Context;
import android.util.AttributeSet;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class RefreshHeaderView extends android.support.v7.widget.AppCompatTextView implements SwipeRefreshTrigger, SwipeTrigger {

    public RefreshHeaderView(Context context) {
        super(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onRefresh() {
        setText("REFRESHING");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onSwipe(int i, boolean b) {
        if (!b) {
            if (i <= -getHeight()) {
                setText("RELEASE TO LOAD MORE");
            } else {
                setText("SWIPE TO LOAD MORE");
            }
        } else {
            setText("LOAD MORE RETURNING");
        }
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void complete() {
        setText("COMPLETE");
    }

    @Override
    public void onReset() {
        setText("");
    }
}
