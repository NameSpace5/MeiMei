package com.meiyue.meimei.http;


import com.meiyue.meimei.RequestParamete.LoginParams;
import com.meiyue.meimei.response.LoginBean;

import rx.Observable;

/**
 *  Api类的包装
 */
public class ApiWrapper extends Api {

    public Observable<LoginBean> getUerInfo(LoginParams params) {
        return applySchedulers(getService().login(params.username,params.password,params.sign));
    }


}
