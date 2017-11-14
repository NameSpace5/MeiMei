package com.meiyue.meimei.ui.Mime.LoginPage;


import com.meiyue.meimei.RequestParamete.LoginParams;
import com.meiyue.meimei.base.BaseCommonPresenter;
import com.meiyue.meimei.http.SimpleMyCallBack;
import com.meiyue.meimei.response.HttpExceptionBean;
import com.meiyue.meimei.response.LoginBean;
import com.meiyue.meimei.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class LoginPresenter extends BaseCommonPresenter<LoginContract.View> implements LoginContract.Presenter{

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login() {
        LoginParams mLoginParams = new LoginParams("13253515580", "123456");
        Map map = new HashMap();
        map.put("username","13253515580");
        map.put("password","123456");
        Subscription subscription =  mApiWrapper.getUerInfo(mLoginParams)
                .subscribe(newMySubscriber(new SimpleMyCallBack<LoginBean>() {
                    // 这个方法根据需要重写 之前已经toast了，如果toast了还要做其他的事情，就重写这个方法
                    @Override
                    public void onError(HttpExceptionBean mHttpExceptionBean) {
                        super.onError(mHttpExceptionBean);
                        ToastUtils.showShort("出错啦");
                    }
                    @Override
                    public void onNext(LoginBean mLogin) {
                        view.loginSuccess();
                    }
                }));
        mCompositeSubscription.add(subscription);
    }
}
