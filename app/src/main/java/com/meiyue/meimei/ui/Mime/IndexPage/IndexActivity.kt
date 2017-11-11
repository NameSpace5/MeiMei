package com.meiyue.meimei.ui.Mime.IndexPage

import android.view.View
import com.meiyue.meimei.R
import com.meiyue.meimei.base.BaseActivity
import com.stone.card.library.CardSlidePanel
import kotlinx.android.synthetic.main.activity_index.*

/**
 * Created by Administrator on 2017/11/11 0011.
 */

class IndexActivity : BaseActivity<IndexPresenter>(), CardSlidePanel.CardSwitchListener,IndexContract.View{
    override fun loadSuccess() {

    }

    override fun hideLoading() {

    }

    override fun initView() {
        setContentView(R.layout.activity_index)
        setTitle("模特卡片")
        card_slide_index.setCardSwitchListener(this)
//        card_slide_index.adapter =  object: CardAdapter() {
//            override fun getLayoutId(): Int {
//                return R.layout.item_card
//            }
//
//            override fun getCount(): Int {
//                return dataList.size
//            }
//
//            override fun bindView(view: View?, index: Int) {
//            }
//
//            override fun getItem(index: Int): Any {
//            }
//
//        }
        this.createPresenter(IndexPresenter(this))
    }

    override fun bindEvent() {

    }

    override fun onClick(view: View) {

    }

    override fun onShow(index: Int) {

    }

    override fun onCardVanish(index: Int, type: Int) {

    }
}
