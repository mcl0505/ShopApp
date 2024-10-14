package com.mh.shop.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mh.shop.http.ConsumeInfoBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.MineViewModel
import com.mh.shop.http.livedata.UserLiveData
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityConsumeBinding
import com.mh0505.shop.databinding.ItemMineConsumeListBinding
import com.mh55.easy.ext.addAdapter
import com.mh55.easy.ext.gone
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.visible
import com.mh55.easy.ui.activity.BaseActivity
import com.mh55.easy.ui.recycler.BindAdapter

/**
 * @createTime =>  2024/10/11 10:26
 * @class =>  ConsumeActivity.kt
 * @desciption =>  消耗达人
 **/
class ConsumeActivity : BaseActivity<ActivityConsumeBinding,MainViewModel>() {
    private var mAdapter : BindAdapter<ConsumeInfoBean,ItemMineConsumeListBinding>?=null
    override fun main(savedInstanceState: Bundle?) {
        with(mBinding){
            viewTitle.apply {
                titleLine.gone()
                imgTitleLeft.singleClick { finish() }
            }
            mRecyclerView.apply {
                linear()
                mAdapter = addAdapter(mutableListOf(),R.layout.item_mine_consume_list){holder, item, binding ->
                    val position = holder.layoutPosition+1
                    binding.apply {
                        when(item.index){
                            1->{
                                consumeLevelImg.visible()
                                consumeLevel.gone()
                                consumeLevelImg.setImageResource(R.mipmap.icon_mine_consume_top_1)
                            }
                            2->{
                                consumeLevelImg.visible()
                                consumeLevel.gone()
                                consumeLevelImg.setImageResource(R.mipmap.icon_mine_consume_top_2)
                            }
                            3->{
                                consumeLevelImg.visible()
                                consumeLevel.gone()
                                consumeLevelImg.setImageResource(R.mipmap.icon_mine_consume_top_3)
                            }
                            else->{
                                consumeLevelImg.gone()
                                consumeLevel.visible()
                                consumeLevel.text = position.toString()
                            }
                        }
                        consumeName.text = item.nickname
                        consumeMobile.text = item.mobile
                        consumeNumber.text = item.consumeValue
                    }

                }
            }
        }
        mViewModel.getConsumeList()
    }

    override fun initViewObservable() {
        super.initViewObservable()
        UserLiveData.observe(this){
            mBinding.tvMoney.text = it.consumeValue
        }

        mViewModel.mConsumeInfo.observe(this){
            mAdapter?.setNewInstance(it)
        }
    }

}