package com.meiyue.meimei.app

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.umeng.message.*
import com.umeng.message.entity.UMessage
import java.util.*

/**
 * Created by Administrator on 2017/11/9 0009.
 */

class MyApplication : Application() {
    private val tag = MyApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = this
        activityList = LinkedList()
        windowManager = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        loginInfo = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        init()

    }

    /**
     * 初始化
     */
    private fun init() {
        /**
         * 友盟推送初始化
         */
        initUMengPush()


        //        CrashHandler.create(this);

        //        JPushInterface.init(this); // 初始化JPush
        //        JPushInterface.setDebugMode(true);  // 设置日志,发布时请关闭日志

    }

    private fun initUMengPush() {
        //友盟推送初始化
        pushAgent = PushAgent.getInstance(this)
        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(object : IUmengRegisterCallback {

            override fun onSuccess(deviceToken: String) {
                println("88888888" + deviceToken)
                Log.d("mytoken", deviceToken)
            }


            override fun onFailure(s: String, s1: String) {
                Log.d("mytoken", s + ":" + s1)
            }
        })
        val handler = object : UmengMessageHandler() {


            override fun getNotification(context: Context?, uMessage: UMessage?): Notification {

                return super.getNotification(context, uMessage)
            }

            override fun dealWithCustomMessage(context: Context?, uMessage: UMessage?) {
                Handler(mainLooper).post {
                    // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
                    val isClickOrDismissed = true
                    if (isClickOrDismissed) {
                        //自定义消息的点击统计
                        UTrack.getInstance(applicationContext).trackMsgClick(uMessage)
                    } else {
                        //自定义消息的忽略统计
                        UTrack.getInstance(applicationContext).trackMsgDismissed(uMessage)
                    }
                    Toast.makeText(context, "111", Toast.LENGTH_LONG).show()
                }
            }
        }

        pushAgent.messageHandler = handler

        //umeng自定义行为
        val notificationClickHandler = object : UmengNotificationClickHandler() {
            override fun dealWithCustomAction(context: Context?, msg: UMessage?) {
                var id = 0
                var type = 0
                var url = ""

                if (null != msg!!.extra["act_type"]) {
                    type = Integer.parseInt(msg.extra["act_type"])
                }
                if (null != msg.extra["act_id"]) {
                    id = Integer.parseInt(msg.extra["act_id"])
                }
                if (null != msg.extra["url"]) {
                    url = msg.extra["url"].toString()
                }
                val intent = Intent()
                //                switch (type){
                //                    case 1://收件箱
                //                        intent.putExtra("id",id);
                //                        intent.setClass(context, MyMessageActivity.class);
                //                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //                        startActivity(intent);
                //                        break;
                //                    case 2://模特
                //                        intent.putExtra(Constant.INTENT.TYPE, Constant.WEBVIEW.MODEL);
                //                        intent.putExtra(Constant.INTENT.INT_ID, id);
                //                        intent.setClass(context, MyMessageActivity.class);
                //                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //                        startActivity(intent);
                //                        break;
                //                    case 3://通告报名
                //                        intent.putExtra(Constant.INTENT.TYPE,id);
                //                        intent.setClass(context, NoticeDetailActivity.class);
                //                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //                        startActivity(intent);
                //                        break;
                //                    case 4://钱包
                //                        intent.setClass(context, WalletActivity.class);
                //                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //                        startActivity(intent);
                //                        break;
                //                }
            }
        }
        pushAgent.notificationClickHandler = notificationClickHandler
    }

    companion object {
        var instance: MyApplication? = null
            private set
        var context: Context? = null
            private set

        /**
         * 是否完成  整个项目
         */
        var isCompleteProject = false
        lateinit var pushAgent: PushAgent
        lateinit var loginInfo: SharedPreferences

        /**
         * activity管理stack
         */
        var activityList: LinkedList<Activity>? = null
        private var windowManager: WindowManager? = null
        private val activityStack = Stack<Activity>()

        val ISDEBUG = false

        /**
         * 获取屏幕宽度
         */
        val systemWidth: Int
            get() = windowManager!!.defaultDisplay.width

        /**
         * 获取屏幕高度
         */
        val systemHeight: Int
            get() = windowManager!!.defaultDisplay.height

        fun addAct(activity: Activity) {
            activityList!!.add(activity)
        }

        fun finishAct() {
            for (activity in activityList!!) {
                activity.finish()
            }
        }

        /**
         * 添加Activity到堆栈
         */
        fun addActivity(activity: Activity) {
            activityStack.push(activity)
        }

        /**
         * 获取当前Activity（堆栈中最后一个压入的）
         */
        fun currentActivity(): Activity {
            return activityStack.lastElement()
        }

        /**
         * 结束当前Activity（堆栈中最后一个压入的）
         */
        fun finishCurrentActivity() {
            val activity = activityStack.pop()
            activity.finish()
        }

        /**
         * 结束指定类名的Activity
         */
        fun finishActivity(cls: Class<*>) {
            for (activity in activityStack) {
                if (activity.javaClass == cls) {
                    activity.finish()
                }
            }
        }

        /**
         * 结束所有Activity
         */
        fun finishAllActivity() {
            for (activity in activityStack) {
                activity?.finish()
            }
            activityStack.clear()
        }

        /**
         * 退出应用程序
         */
        fun AppExit(context: Context) {
            try {
                finishAllActivity()
                val manager = context
                        .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                manager.killBackgroundProcesses(context.packageName)
                System.exit(0)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
