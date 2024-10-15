package com.mh.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mh0505.shop.databinding.ActivitySuccessBinding
import com.mh55.easy.manager.AppManager
import com.mh55.easy.ui.activity.BaseActivity

class SuccessActivity : BaseActivity<ActivitySuccessBinding,TaskViewModel>() {

    companion object {
        /**
         *
         * @param type 1=兑换成功
         * @return null
         * @createtime 2024/4/11 20:38
         **/
        fun start(type: Int,content:String = "",tip:String = "") {
            val intent = Intent(AppManager.peekActivity() as AppCompatActivity, SuccessActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("content", content)
            intent.putExtra("tip", tip)
            AppManager.start(intent)
        }
    }

    override fun setTitleText(): String {
        return when (intent.getIntExtra("type", 0)) {
            1 -> "兑换成功"
            2 -> "答题成功"
            3 -> "兑换成功"
            4 -> "领取成功"
            5 -> "提票成功"
            6 -> "反馈成功"
            else -> ""
        }
    }

    override fun main(savedInstanceState: Bundle?) {
        val content = intent.getStringExtra("content")
        val tip = intent.getStringExtra("tip")

        if (content.isNullOrEmpty()){
            mBinding.mContent.text = when (intent.getIntExtra("type", 0)) {
                1 -> "兑换成功"
                2 -> "答题成功"
                3 -> "兑换成功"
                4 -> "领取成功"
                5 -> "提票成功"
                6 -> "反馈成功"
                else -> ""
            }
        }else {
            mBinding.mContent.text = content
        }

        if (tip.isNullOrEmpty()){
            mBinding.mDes.text = when (intent.getIntExtra("type", 0)) {
                1 -> "请在“我的券包”里面查看"
                else -> ""
            }
        }else {
            mBinding.mDes.text = tip
        }


    }

}