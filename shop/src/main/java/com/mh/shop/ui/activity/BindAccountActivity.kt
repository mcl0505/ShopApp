package com.mh.shop.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.livedata.UserLiveData
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityBindAccountBinding
import com.mh55.easy.ext.gone
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.toast
import com.mh55.easy.ext.visible
import com.mh55.easy.ui.activity.BaseActivity

class BindAccountActivity : BaseActivity<ActivityBindAccountBinding,MainViewModel>() {
    override fun setTitleText(): String {
        return "绑定账号"
    }
    override fun main(savedInstanceState: Bundle?) {

    }

    override fun initViewObservable() {
        super.initViewObservable()
        UserLiveData.observe(this){
           if(it.tradAccount.isNullOrEmpty()){
               mBinding.llSuccess.gone()
               mBinding.llEdit.visible()
               mBinding.bindAccount.singleClick {
                   val account = mBinding.mInputTopEdit.text.toString()
                   if (account.isNullOrEmpty()){
                       mBinding.mInputTopEdit.hint.toast()
                       return@singleClick
                   }

                   mViewModel.bindAccount(account){
                       mViewModel.getUserInfo()
                   }
               }

           }else {
               mBinding.llSuccess.visible()
               mBinding.llEdit.gone()
               mBinding.accountInfo.text = "绑定的账号：${it.tradAccount}"
           }
        }
    }

}