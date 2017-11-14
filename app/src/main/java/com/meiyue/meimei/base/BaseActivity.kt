package com.meiyue.meimei.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Preconditions`.checkNotNull
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu
import com.meiyue.meimei.R
import com.meiyue.meimei.http.Api
import com.meiyue.meimei.http.ApiWrapper
import com.meiyue.meimei.http.SimpleMyCallBack
import com.meiyue.meimei.response.HttpExceptionBean
import com.meiyue.meimei.utils.ToastUtils
import com.meiyue.meimei.widget.dialog.DialogLoading
import kotlinx.android.synthetic.main.activity_base_title_layout.*
import kotlinx.android.synthetic.main.layout_menu.*
import kotlinx.android.synthetic.main.title_layout.*
import retrofit2.HttpException
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.io.IOException

/**
 * Activity  基类
 * @author Administrator
 */
abstract class BaseActivity<T : BaseCommonPresenter<*>> : AppCompatActivity(), View.OnClickListener {

    private lateinit var mContext: AppCompatActivity
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    protected var mCompositeSubscription: CompositeSubscription? = null
    /**
     * 加载对话框
     */
    protected var loading: DialogLoading? = null
    /**
     * 来自哪个 页面
     */
    private lateinit var fromWhere: String
    /**
     * Api类的包装 对象
     */
    protected var mApiWrapper: ApiWrapper? = null


    var presenter: T? = null


    /**
     * 获取状态栏高度
     */
    private val statusBarHeight: Int
        get() {
            var result = 0
            val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resId > 0) {
                result = resources.getDimensionPixelSize(resId)
            }
            return result
        }

    val apiWrapper: ApiWrapper
        get() {
            if (mApiWrapper == null) {
                mApiWrapper = ApiWrapper()
            }
            return mApiWrapper!!
        }

    val compositeSubscription: CompositeSubscription
        get() {
            if (mCompositeSubscription == null) {
                mCompositeSubscription = CompositeSubscription()
            }
            return mCompositeSubscription as CompositeSubscription
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base_title_layout)
        //设置全屏
        setStatusColor()

        //设置slidingMenu侧滑
        setSlidingMenu()

        // 设置不能横屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        mContext = this
        //Activity管理
        ActivityPageManager.getInstance().addActivity(this)
        //设置键盘遮挡界面
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    fun setSlidingMenu(){

        //初始化抽屉

        val menu = SlidingMenu(this)
        menu.mode = SlidingMenu.LEFT
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT)
        menu.behindOffset = 400
        menu.touchModeAbove = SlidingMenu.TOUCHMODE_FULLSCREEN
        menu.setMenu(R.layout.layout_menu)
        menu_index_home.setOnClickListener(this)
        menu_index_circle.setOnClickListener(this)
        menu_index_guidance.setOnClickListener(this)
        menu_index_message.setOnClickListener(this)
        menu_index_mine.setOnClickListener(this)

    }
    override fun setContentView(view: View) {
        main_content_layout.removeAllViews()
        main_content_layout.addView(view)
        onContentChanged()
        //初始化页面
        init()
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        main_content_layout.removeAllViews()
        main_content_layout.addView(view, params)
        onContentChanged()
        init()
    }

    override fun setContentView(layoutResID: Int) {
        main_content_layout.removeAllViews()
        View.inflate(this, layoutResID, main_content_layout)
        onContentChanged()
        init()
    }

    override fun setTitle(title: CharSequence?) {
        tv_main_bar_title.text = title
    }

    override fun setTitle(titleId: Int) {
        tv_main_bar_title.id = titleId
    }
    /**
     * 设置状态栏统一色
     */
    private fun setStatusColor() {

        val window = window
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val mContentView = findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup


        //Android 5.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.colorPrimary)
            //设置状态栏颜色
            val mChildView = mContentView.getChildAt(0)
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                ViewCompat.setFitsSystemWindows(mChildView, true)
                //Android 5.0-
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val statusBarHeight = statusBarHeight
                val statusColor = resources.getColor(R.color.colorPrimary)

                var mTopView: View? = mContentView.getChildAt(0)
                if (mTopView != null && mTopView.layoutParams != null &&
                        mTopView.layoutParams.height == statusBarHeight) {
                    //避免重复添加 View
                    mTopView.setBackgroundColor(statusColor)
                    return
                }
                //使 ChildView 预留空间
                if (mTopView != null) {
                    ViewCompat.setFitsSystemWindows(mTopView, true)
                }

                //添加假 View
                mTopView = View(this)
                val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
                mTopView.setBackgroundColor(statusColor)
                mContentView.addView(mTopView, 0, lp)
            }
        }

    }

    /**
     * 初始化页面
     */
    fun init() {
        initFromWhere()
        initView()
        bindEvent()
    }

    /**
     * 初始化 Api  根据需要初始化
     */
    fun initApi() {
        //创建 CompositeSubscription 对象 使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅。
        mCompositeSubscription = CompositeSubscription()
        // 构建 ApiWrapper 对象
        mApiWrapper = ApiWrapper()
    }

    /**
     * 初始化view
     */
    abstract fun initView()


    /**
     * 绑定事件
     */
    abstract fun bindEvent()


    /**
     * 创建相应的 presenter
     */
    fun createPresenter(presenter: T?) {
        if (presenter != null) {
            this.presenter = presenter
        }

    }

    protected fun initFromWhere() {
        if (null != intent.extras) {
            if (intent.extras!!.containsKey("fromWhere")) {
                fromWhere = intent.extras!!.getString("fromWhere")!!.toString()
            }
        }
    }

    /**
     * 创建观察者  这里对观察着 过滤一次，过滤出我们想要的信息，错误的信息toast
     *
     * @param onNext
     * @param <T>
     * @return
    </T> */
    fun <T> newMySubscriber(onNext: SimpleMyCallBack<Any>): Subscriber<T> {
        return object : Subscriber<T>() {
            override fun onCompleted() {
                hideLoadingDialog()
                onNext.onCompleted()
            }

            override fun onError(e: Throwable) {
                if (e is Api.APIException) {
                    ToastUtils.showShort(e.msg)
                } else if (e is HttpException) {
                    val body = e.response().errorBody()
                    try {
                        val json = body!!.string()
                        val gson = Gson()
                        val mHttpExceptionBean = gson.fromJson(json, HttpExceptionBean::class.java)
                        if (mHttpExceptionBean != null && mHttpExceptionBean.message != null) {
                            ToastUtils.showShort(mHttpExceptionBean.message)
                            onNext.onError(mHttpExceptionBean)
                        }
                    } catch (IOe: IOException) {
                        IOe.printStackTrace()
                    }

                }
                //                e.printStackTrace();
                hideLoadingDialog()
            }

            override fun onNext(t: T) {
                if (!mCompositeSubscription!!.isUnsubscribed) {
                    onNext.onNext(t)
                }
            }

        }
    }

    /**
     * 将 Fragment添加到Acitvtiy
     *
     * @param fragment
     * @param frameId
     */
    protected fun addFragmentToActivity(fragment: Fragment, frameId: Int) {
        checkNotNull(fragment)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }


    /**
     * 显示一个Toast信息
     */
    fun showToast(content: String?) {
        if (content != null) {
            ToastUtils.showShort(content)
        }
    }

    fun showLoadingDialog() {
        if (loading == null) {
            loading = DialogLoading(this)
        }
        loading!!.show()
    }

    fun hideLoadingDialog() {
        if (loading != null) {
            loading!!.dismiss()
        }

    }

    /**
     * 跳转页面
     *
     * @param clazz
     */
    fun skipAct(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        intent.putExtra("fromWhere", javaClass.simpleName)
        startActivity(intent)
    }

    fun skipAct(clazz: Class<*>, bundle: Bundle) {
        val intent = Intent(this, clazz)
        intent.putExtras(bundle)
        intent.putExtra("fromWhere", javaClass.simpleName)
        startActivity(intent)
    }

    fun skipAct(clazz: Class<*>, bundle: Bundle, flags: Int) {
        val intent = Intent(this, clazz)
        intent.putExtra("fromWhere", javaClass.simpleName)
        intent.flags = flags
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        //Acitvity 释放子view资源
        ActivityPageManager.unbindReferences(main_content_layout)
        ActivityPageManager.getInstance().removeActivity(this)
        //一旦调用了 CompositeSubscription.unsubscribe()，这个CompositeSubscription对象就不可用了,
        // 如果还想使用CompositeSubscription，就必须在创建一个新的对象了。
        if (mCompositeSubscription != null) {
            mCompositeSubscription!!.unsubscribe()
        }
        //解绑 presenter
        if (presenter != null) {
            presenter!!.unsubscribe()
        }
    }

    /**
     * 返回按钮
     */
    fun showBackBtn(isShow:Boolean) {
        if (btn_main_bar_back != null) {
            if (isShow) {
                btn_main_bar_back.visibility = View.VISIBLE
            } else {
            btn_main_bar_back.visibility = View.INVISIBLE
            }
        }
    }
}
