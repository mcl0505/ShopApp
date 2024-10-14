package com.mh.shop.ui.activity

import android.os.Bundle
import com.mh.shop.http.DistributorBean
import com.mh.shop.http.DistributorInfoBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.MineViewModel
import com.mh.shop.ui.dialog.CommonTipDialog
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityDistributorBinding
import com.mh0505.shop.databinding.ItemMineDistributorLevelBinding
import com.mh55.easy.ext.addAdapter
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.visibleOrGone
import com.mh55.easy.ui.activity.BaseActivity
import com.mh55.easy.ui.recycler.BindAdapter

/**
 * @createTime =>  2024/10/11 9:43
 * @class =>  DistributionActivity.kt
 * @desciption =>  分销
 **/
class DistributorActivity : BaseActivity<ActivityDistributorBinding, MainViewModel>() {
    private var mAdapter: BindAdapter<DistributorInfoBean, ItemMineDistributorLevelBinding>? = null
    override fun main(savedInstanceState: Bundle?) {
        with(mBinding) {
            imgBack.singleClick { finish() }
            mRecyclerView.apply {
                linear()
                mAdapter = addAdapter(
                    mutableListOf(),
                    R.layout.item_mine_distributor_level
                ) { holder, item, binding ->
                    binding.apply {
                        pushType.text = item.name
                        pushNumber.text = item.num+"个"
                        pushFactor.text = item.contribution+"点"
                        actionHint.visibleOrGone(item.status == 0)
                        if (item.status != 0){
                            action.visibleOrGone(item.status != 0)
                            action.apply {
                                text = when(item.status){
                                    1,3->"已兑换"
                                    2->"可兑换"
                                    else->"/"
                                }
                                isSelected = item.status == 2
                                isEnabled = item.status == 2
                                singleClick {
                                    CommonTipDialog(content = "需消耗${item.contribution}贡献值\n是否确定兑换？") {
                                        if (it) {
                                            mViewModel.getDistributorExchange(item.id){
                                                SuccessActivity.start(3)
                                            }

                                        }
                                    }.showDialog(this@DistributorActivity)
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getDistributorInfo()
    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.mDistributorInfo.observe(this) {
            mAdapter?.setNewInstance(it.list)
            with(mBinding){
                pusherType.text = it.retail_level
                pusherValue.text = it.condition

                conInfo.setBackgroundResource(it.getLevelImg())
            }
        }
    }
}