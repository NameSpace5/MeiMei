package com.meiyue.meimei.ui.Mime.LoginPage;


import com.meiyue.meimei.base.BasePresenter;
import com.meiyue.meimei.base.BaseView;

/**
 * Created by baixiaokang on 16/4/29.
 */
public interface LoginContract {
    interface View extends BaseView {
        void loginSuccess();
    }

    interface Presenter extends BasePresenter {
        void login();
    }
}
