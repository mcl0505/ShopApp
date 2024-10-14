package com.mh.shop.ui

import android.os.Bundle
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.MineViewModel
import com.mh.shop.ui.fragment.MineFragment
import com.mh.shop.ui.fragment.TaskFragment
import com.mh0505.shop.databinding.ActivityShopMainBinding
import com.mh55.easy.ui.activity.BaseActivity
import com.mh55.easy.widget.tab.withViewPager
import com.mh55.httplibrary.HttpConfig

class ShopMainActivity : BaseActivity<ActivityShopMainBinding, MainViewModel>() {
    override fun main(savedInstanceState: Bundle?) {

        mBinding.mTabButtonGroup.withViewPager(
            this, mBinding.mViewPager, mutableListOf(
                TaskFragment.newInstance(), MineFragment.newInstance()
            )
        )
    }

    override fun onResume() {
        super.onResume()
        if (HttpConfig.userToken.isNullOrEmpty()){
            mViewModel.login()
        }else {
            mViewModel.getUserInfo()
        }
    }
}
