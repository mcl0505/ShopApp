package com.mh.shop.ui.activity

import android.os.Bundle
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.PusherBean
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityPusherBinding
import com.mh0505.shop.databinding.ItemMinePusherLevelBinding
import com.mh55.easy.ext.addAdapter
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.toast
import com.mh55.easy.ext.visibleOrGone
import com.mh55.easy.ui.activity.BaseActivity
import com.mh55.easy.ui.recycler.BindAdapter

/**
 * @createTime =>  2024/10/11 9:11
 * @class =>  PublicizeActivity.kt
 * @desciption =>  推广员
 **/
class PusherActivity : BaseActivity<ActivityPusherBinding, MainViewModel>() {
    private var mAdapter :BindAdapter<PusherBean, ItemMinePusherLevelBinding>?=null
    override fun main(savedInstanceState: Bundle?) {
        with(mBinding) {
            imgBack.singleClick { finish() }
            imgLevelUp.singleClick {
                mViewModel.pusherUp {
                    "升级成功".toast()
                    request()
                }
            }
            mRecyclerView.apply {
                linear()
                mAdapter = addAdapter(mutableListOf(),R.layout.item_mine_pusher_level){holder, item, binding ->
                    val position = holder.layoutPosition
                    if (position % 2 == 0){
                        binding.llBg.setBackgroundColor("#FFF9E6".getColor())
                    }else binding.llBg.setBackgroundColor("#FFECC5".getColor())


                    binding.apply {
                        pushType.text = item.name
                        pushNumber.text = item.max_num+"个"
                        pushFactor.text = if (item.condition.contains("+"))item.condition.split("+")[0]+"\n+"+item.condition.split("+")[1] else item.condition
                    }
                }
            }
            request()
        }
    }

    fun request(){
        mViewModel.getPusherInfo()
    }



    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.mPusherInfo.observe(this){
            mAdapter?.setNewInstance(it.list)
            with(mBinding){
                pusherType.text = it.level
                pusherValue.text = it.condition
                imgLevelUp.visibleOrGone(it.upgrade_status == 2)
                conInfo.setBackgroundResource(it.getLevelImg())
            }
        }
    }

}