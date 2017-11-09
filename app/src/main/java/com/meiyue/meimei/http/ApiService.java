package com.meiyue.meimei.http;

import com.meiyue.meimei.RequestParamete.LoginParams;
import com.meiyue.meimei.response.Login;
import com.meiyue.meimei.response.LoginBean;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by Sunflower on 2015/11/4.
 */
public interface ApiService {


    /**
     * 获取个人信息
     */
    @POST("account/v1/login")
    Observable<Login> getPersonalInfo(@Body LoginParams mLoginParams);

    @FormUrlEncoded
    @POST("/index.php/Api/Index/login")
    Observable<LoginBean> login(@Field("username") String username, @Field("password") String password, @Field("sign") String sign);

}
