package com.meiyue.meimei.ui.Mime.IndexPage

import android.os.Bundle
import android.view.View
import com.meiyue.meimei.R
import com.meiyue.meimei.base.BaseActivity
import com.stone.card.library.CardAdapter
import com.stone.card.library.CardSlidePanel
import kotlinx.android.synthetic.main.activity_index.*
import kotlinx.android.synthetic.main.item_card_view.*

/**
 * Created by Administrator on 2017/11/11 0011.
 */

class IndexActivity : BaseActivity<IndexPresenter>(), CardSlidePanel.CardSwitchListener,IndexContract.View{
    override fun onClick(p0: View?) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
    }

    override fun loadSuccess() {

    }

    override fun hideLoading() {

    }

    override fun initView() {
        title = "模特卡片"
        showBackBtn(true)

        val dataList = arrayOf(R.drawable.img_index_circle,R.drawable.img_index_message,R.drawable.img_index_guidance)
        card_slide_index.setCardSwitchListener(this)
        card_slide_index.adapter =  object: CardAdapter() {
            override fun getLayoutId(): Int {
                return R.layout.item_card_view
            }

            override fun getCount(): Int {
                return dataList.size
            }

            override fun bindView(view: View?, index: Int) {
                card_image_view.setImageDrawable(resources.getDrawable(dataList[index]))
            }

            override fun getItem(index: Int): Any {
                return index
            }

        }
        this.createPresenter(IndexPresenter(this))
    }

    override fun bindEvent() {

    }


    override fun onShow(index: Int) {

    }

    override fun onCardVanish(index: Int, type: Int) {

    }
}
