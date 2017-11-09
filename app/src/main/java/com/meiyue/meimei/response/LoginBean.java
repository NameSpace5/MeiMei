package com.meiyue.meimei.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class LoginBean implements Serializable {

    /**
     * status : 1
     * msg : 登录成功！
     * result : {"user_id":"1844","lever":"2","tuan_id":0}
     */

    private int status;
    private String msg;
    private Result result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        /**
         * user_id : 1844
         * lever : 2
         * tuan_id : 0
         */

        private String user_id;
        private String lever;
        private int tuan_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getLever() {
            return lever;
        }

        public void setLever(String lever) {
            this.lever = lever;
        }

        public int getTuan_id() {
            return tuan_id;
        }

        public void setTuan_id(int tuan_id) {
            this.tuan_id = tuan_id;
        }
    }

    @Override
    public String toString() {
        return "Login{" +
                "user_id='" + result.getUser_id() + '\'' +
                ", token='" + status + '\'' +
                ", ttl=" +result.getLever() +
                ", continue_url='" + msg + '\'' +
                '}';
    }
}
