package com.mh.shop

import com.mh55.easy.EasyApp

open class ShopApp : EasyApp() {
    override fun onCreate() {
        super.onCreate()
        ShopConfig.init(this)
    }

    override fun onErrorTip(code: Int, msg: String) {
        if (code<0)return
        super.onErrorTip(code, msg)

    }
}