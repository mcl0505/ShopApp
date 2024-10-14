package com.mh.shop

import android.app.Application
import com.mh55.httplibrary.HttpConfig
import com.mh55.httplibrary.RxHttpManager

object ShopConfig {
    fun init(application: Application){
        HttpConfig.apply {
            apiUrl = "https://qdjtest.qiannengwuxian.com/api/"
            isDebug = false
        }
        RxHttpManager.init(application)
    }


}