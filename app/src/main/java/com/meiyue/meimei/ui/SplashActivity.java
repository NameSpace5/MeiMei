package com.meiyue.meimei.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.meiyue.meimei.R;
import com.meiyue.meimei.ui.Mime.LoginPage.LoginActivity;
import com.meiyue.meimei.utils.SPUtils;

public class SplashActivity extends AppCompatActivity {

    private static final int msg_jump = 0;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case msg_jump:
                    //从sp中获取标志
                    //第一次：引导界面
                    //第二次：
                    boolean isFirst = (boolean) SPUtils.get(SplashActivity.this,"isFirst", true);
                    if(isFirst){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                        SPUtils.put(SplashActivity.this,"isFirst", false);
                    }else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.sendEmptyMessageDelayed(msg_jump, 2000);
    }
}
