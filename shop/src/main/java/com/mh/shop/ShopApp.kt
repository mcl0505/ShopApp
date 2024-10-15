package com.mh.shop

import android.app.Application
import com.mh55.easy.app.AppConfig
import com.mh55.easy.ext.toast
import com.mh55.easy.utils.AppListener

open class ShopApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ShopConfig.init(this)
        AppConfig.init(this,object : AppListener{
            override fun onErrorTip(code: Int, msg: String) {
                if (code<0)return
                msg.toast()
            }

        })
    }


}