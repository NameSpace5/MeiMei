package com.meiyue.meimei.ui.Mime.IndexPage;


import com.meiyue.meimei.base.BasePresenter;
import com.meiyue.meimei.base.BaseView;

/**
 * Created by Administrator on 2017/11/11 0011.
 */

public class IndexContract {
    interface View extends BaseView {
        void loadSuccess();
    }

    interface  Presenter extends BasePresenter {
        void loadData();
    }
}
