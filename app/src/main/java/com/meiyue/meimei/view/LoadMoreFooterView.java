package com.meiyue.meimei.view;

import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class LoadMoreFooterView extends AppCompatTextView implements SwipeTrigger, SwipeLoadMoreTrigger {
    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
        setText("LOADING MORE");
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
        setText("LOADING MORE");
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

