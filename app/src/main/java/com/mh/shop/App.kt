package com.mh.shop

import com.mh55.easy.EasyApp
import com.mh55.easy.ext.toast

class App : EasyApp() {
    override fun onCreate() {
        super.onCreate()
        ShopConfig.init(this)
    }

    override fun onErrorTip(code: Int, msg: String) {
        super.onErrorTip(code, msg)
        when(code){
            -1->{}
            9527->"展示广告".toast()
            else->msg.toast()
        }
    }
}