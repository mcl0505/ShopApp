package com.mh.shop

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.mh.shop.ui.ShopMainActivity
import com.mh55.easy.ext.toast
import com.mh55.easy.manager.AppManager
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

    /**开启商城模块**/
    fun startShop(token:String = ""){
        if (token.isNullOrEmpty()){
            "请登录后重试".toast()
            return
        }

        val intent = Intent(AppManager.peekActivity() as AppCompatActivity, ShopMainActivity::class.java)
        intent.putExtra("token", token)
        AppManager.start(intent)
    }


}