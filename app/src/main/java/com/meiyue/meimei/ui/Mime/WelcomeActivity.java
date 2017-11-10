package com.meiyue.meimei.ui.Mime;


import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.meiyue.meimei.R;
import com.meiyue.meimei.adapter.MyTextAdapter;
import com.meiyue.meimei.base.BaseActivity;
import com.meiyue.meimei.base.BaseRecylerAdapter;
import com.meiyue.meimei.ui.Mime.LoginPage.LoginActivity;
import com.meiyue.meimei.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class WelcomeActivity extends BaseActivity implements OnRefreshListener,OnLoadMoreListener {

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);

//        initApi();
//
//        /**
//         * 网络请求
//         */
//        LoginParams mLoginParams = new LoginParams("13253515580","123456");
//        Subscription subscription =  getMApiWrapper().getUerInfo(mLoginParams)
//                .subscribe(newMySubscriber(new SimpleMyCallBack<LoginBean>() {
//                    // 这个方法根据需要重写 之前已经toast了，如果toast了还要做其他的事情，就重写这个方法
//                    @Override
//                    public void onError(HttpExceptionBean mHttpExceptionBean) {
//                        super.onError(mHttpExceptionBean);
//                        showToast("yoxin");
//                    }
//                    @Override
//                    public void onNext(LoginBean mLogin) {
//                        showToast(mLogin.getMsg());
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        showToast("请求完成");
//                    }
//                }));
//        getMCompositeSubscription().add(subscription);

    }

    @Override
    public void initView() {

        setTitle("美美直聘");
        /**
         * 刷新 recyclerView 点击事件
         */
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        recyclerView = (RecyclerView)findViewById(R.id.swipe_target);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<String> list = new ArrayList<>();
        for(int i= 0;i<50; i++){
            list.add("ad");
        }
        MyTextAdapter adapter = new MyTextAdapter(this, list);
        adapter.setOnItemClickLitener(new BaseRecylerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showShort("点击了第--"+position+"--个");
                skipAct(LoginActivity.class);
            }
        });
        recyclerView.setAdapter(adapter);

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void bindEvent() {

    }


    @Override
    public void onClick(View view) {

    }


}
