package com.meiyue.meimei;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableRow;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SlidingMenu slidingMenu =  new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);


        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);


        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
//        slidingMenu.setShadowWidthRes(400);
//        slidingMenu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffset(400);
        slidingMenu.setMenu(R.layout.layout_menu);

         TableRow row = (TableRow) findViewById(R.id.menu_index_home);
        row.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_index_home:
                Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
                break;
        }
        Toast.makeText(this,"qqqqqqqqq",Toast.LENGTH_SHORT).show();
    }
}
