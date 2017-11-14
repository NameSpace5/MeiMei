package com.meiyue.meimei

import android.os.Bundle
import android.view.View
import com.meiyue.meimei.base.BaseActivity
import com.meiyue.meimei.ui.Mime.IndexPage.IndexActivity
import com.meiyue.meimei.ui.Mime.LoginPage.LoginPresenter


class MainActivity : BaseActivity<LoginPresenter>(),View.OnClickListener{
    override fun onClick(p0: View) {
       when(p0.id){
           R.id.menu_index_home ->
                   skipAct(IndexActivity::class.java)
       }
    }

    override fun initView() {
        title = "首页"
        showBackBtn(true)

    }


    override fun bindEvent() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        }
    }

