package com.meiyue.meimei

import android.os.Bundle
import android.view.View
import com.meiyue.meimei.base.BaseActivity
import com.meiyue.meimei.login.LoginPresenter
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : BaseActivity<LoginPresenter>() {
    // 数据源
    private val res = intArrayOf(R.drawable.img_index_home,
            R.drawable.img_index_message,
            R.drawable.img_index_circle,
            R.drawable.img_index_mine,
            R.drawable.img_index_guidance)
    private val name = arrayOf("首页", "消息", "圈子", "我的", "新手引导")
    override fun onClick(p0: View?) {

    }

    override fun initView() {
        title = "首页"
        showBackBtn(true)
        left_menu.alpha = 0.8f

    }


    override fun bindEvent() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        }
    }
