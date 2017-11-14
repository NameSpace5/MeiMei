package com.meiyue.meimei.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.meiyue.meimei.R;
import com.meiyue.meimei.ui.Mime.LoginPage.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    //数据源
    public List<Integer> mimageResList = new ArrayList<Integer>();
    public ViewPager mview;
    public Button mbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mview = (ViewPager) findViewById(R.id.view_pager);
        mbutton = (Button) findViewById(R.id.button);
        mview.setOnPageChangeListener(this);

        mimageResList.add(R.drawable.img_guide_1);
        mimageResList.add(R.drawable.img_guide_2);

        //给viewpager添加适配器
        MyPagerAdapter mAdapter = new MyPagerAdapter(this);
        mview.setAdapter(mAdapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //如果是最后一个界面，显示按钮
        if (position == mimageResList.size() - 1) {
            mbutton.setVisibility(View.VISIBLE);
            mbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                    finish();
                }
            });
        } else {
            mbutton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyPagerAdapter extends PagerAdapter {

        public MyPagerAdapter(GuideActivity guideActivity) {

        }

        /**
         * 界面个数
         *
         * @return
         */
        @Override
        public int getCount() {
            return mimageResList.size();
        }

        /**
         * 初始化界面
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            ImageView minageView = new ImageView(GuideActivity.this);
            minageView.setImageResource(mimageResList.get(position));
            minageView.setScaleType(ImageView.ScaleType.FIT_XY);
            minageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            container.addView(minageView);
            return minageView;
        }

        /**
         * 销毁界面
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /**
         * view和object是否为同一对象
         *
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
