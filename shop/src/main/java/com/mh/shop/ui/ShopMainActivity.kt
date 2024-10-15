package com.mh.shop.ui

import android.os.Bundle
import com.mh.shop.http.MainViewModel
import com.mh.shop.ui.fragment.MineFragment
import com.mh.shop.ui.fragment.TaskFragment
import com.mh0505.shop.databinding.ActivityShopMainBinding
import com.mh55.easy.ui.activity.BaseActivity
import com.mh55.easy.widget.tab.withViewPager
import com.mh.httplibrary.HttpConfig

class ShopMainActivity : BaseActivity<ActivityShopMainBinding, MainViewModel>() {
    var mToken =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsb2NhbGhvc3QiLCJhdWQiOiJsb2NhbGhvc3QiLCJpYXQiOjE3Mjg2MDk4ODMsIm5iZiI6MTcyODYwOTg4MywiZXhwIjoxNzI5MjE0NjgzLCJ1c2VyIjp7Im1vYmlsZSI6IjE0Nzg1NTY0NDMyIiwibmlja25hbWUiOiJcdTVmMjBcdTRlMDkiLCJhdmF0YXIiOiIiLCJwTW9iaWxlIjoiMTQ3ODU1NjQ0MzEifSwianRpIjp7InVpZCI6MCwidHlwZSI6IiJ9fQ.uUOGesO6tOx0m6BfTIop9JpPJxLP80JaLtBGKBtC904"

    override fun main(savedInstanceState: Bundle?) {
//        mToken = intent.getStringExtra("token")?:""
        mBinding.mTabButtonGroup.withViewPager(
            this, mBinding.mViewPager, mutableListOf(
                TaskFragment.newInstance(), MineFragment.newInstance()
            )
        )
    }

    override fun onResume() {
        super.onResume()

        if (HttpConfig.userToken.isNullOrEmpty()){
            mViewModel.login(mToken)
        }else {
            mViewModel.getUserInfo()
        }
    }
}
