package com.meiyue.meimei

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleAdapter
import com.meiyue.meimei.R.id.*
import com.meiyue.meimei.base.BaseActivity
import com.meiyue.meimei.ui.Mime.LoginPage.LoginPresenter
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : BaseActivity<LoginPresenter>(), AdapterView.OnItemClickListener{
    override fun onClick(p0: View?) {

    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//        // 动态插入一个Fragment到FrameLayout中
//        val contentFragment = ContentFragment()
//        val args = Bundle()
//        args.putString("text", left_mDrawerList.getItemAtPosition(position)
//                .toString())
//        contentFragment.setArguments(args)
//
//        val fm = fragmentManager
//        // 开启一个事务
//        fm.beginTransaction().replace(R.id.middle_frame, contentFragment)
//                .commit()

        drawer_layout.closeDrawer(left_menu)
    }

    // 数据源
    private val res = intArrayOf(R.drawable.img_index_home,
            R.drawable.img_index_message,
            R.drawable.img_index_circle,
            R.drawable.img_index_mine,
            R.drawable.img_index_guidance)
    private val name = arrayOf("首页", "消息", "圈子", "我的", "新手引导")


    override fun initView() {
        val left_data_list = ArrayList<Map<String,Any>>()
        title = "首页"
        showBackBtn(true)
        //初始化抽屉
        left_menu.alpha = 0.8f
        (0 until res.size).mapTo(left_data_list) { mapOf(Pair("name",name[it]), Pair("src",res[it])) }
        val adapter = SimpleAdapter(this,left_data_list,R.layout.item_index,
                arrayOf("name","src"), intArrayOf(R.id.tv_index_item_text,R.id.iv_index_item_img))
        lv_drawer_left.adapter = adapter
        lv_drawer_left.onItemClickListener = this
    }


    override fun bindEvent() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        }
    }

