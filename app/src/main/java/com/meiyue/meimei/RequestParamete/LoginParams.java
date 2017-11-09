package com.meiyue.meimei.RequestParamete;

/**
 * 登录请求参数
 * Created by Android on 2015-12-23 023.
 */
public class LoginParams {

    public String username;
    public String password;
    public String sign;

    public LoginParams(String username, String password) {
        this.username = username;
        this.password = password;
        this.sign = "6cc899f8621062cffc65ad8f5ec0fc30";
    }
}
