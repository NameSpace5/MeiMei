package com.meiyue.meimei.ui.Mime.LoginPage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.meiyue.meimei.R
import com.meiyue.meimei.base.BaseActivity


/**
 * Created by Administrator on 2016/1/14.
 */
class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test)

        //采用mvp 模式 只有acitivity要初始化api  ------情况4
        initApi()
        //创建 Presenter 如果不采用mvp 就不要创建
//        this.createPresenter(LoginPresenter(this))

//        val mLoginParams = LoginParams("13253515580", "123456")
//        val map:MutableMap<String,String> = mutableMapOf()
//        map.put("username", "13253515580")
//        map.put("password", "123456")
//        val subscription = mApiWrapper!!.getUerInfo(mLoginParams)
//                .subscribe(newMySubscriber<Any>(object : SimpleMyCallBack<LoginBean>() {
//                    // 这个方法根据需要重写 之前已经toast了，如果toast了还要做其他的事情，就重写这个方法
//                    override fun onError(mHttpExceptionBean: HttpExceptionBean) {
//                        super.onError(mHttpExceptionBean)
//                        ToastUtils.showShort("出错啦")
//                    }
//
//                    override fun onNext(mLogin: LoginBean) {
//                       loginSuccess()
//                    }
//                }))
    }

    override fun initView() {
//        Toast.makeText(this,"11111",Toast.LENGTH_SHORT).show()
    }

    override fun bindEvent() {

    }


    override fun onClick(view: View) {

    }

    override fun hideLoading() {
        Toast.makeText(this,"22222",Toast.LENGTH_SHORT).show()
    }

    override fun loginSuccess() {
        Toast.makeText(this,"33333",Toast.LENGTH_SHORT).show()
    }
}
