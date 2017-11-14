package com.meiyue.meimei.http


import com.meiyue.meimei.response.HttpExceptionBean

/**
 * MyCallBack 的一个简单实现，onNext（） 方法一定要重写，onCompleted()和onError 更具需要重写
 */

abstract class SimpleMyCallBack<Any> : MyCallBack<Any> {
    override fun onCompleted() {}
    override fun onError(mHttpExceptionBean: HttpExceptionBean) {}
}
