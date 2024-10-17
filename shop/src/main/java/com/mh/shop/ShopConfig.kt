package com.mh.shop

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import com.mh.shop.ui.ShopMainActivity
import com.mh55.easy.ext.toast
import com.mh55.easy.manager.AppManager
import com.mh.httplibrary.HttpConfig
import com.mh.httplibrary.RxHttpManager
import com.mh55.easy.app.AppConfig
import com.mh55.easy.utils.AppListener

object ShopConfig {
    fun init(application: Application,listener: AppListener){
        AppConfig.init(application,listener)
        HttpConfig.apply {
            apiUrl = "https://qdjtest.qiannengwuxian.com/api/"
            isDebug = false
        }
        RxHttpManager.init(application)

    }

    /**开启商城模块**/
    fun startShop(context: Context,token:String = ""){
        if (token.isNullOrEmpty()){
            "请登录后重试".toast()
            return
        }

        val intent = Intent(context, ShopMainActivity::class.java)
        intent.putExtra("token", token)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

}