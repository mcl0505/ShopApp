package com.mh.shop.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh.shop.http.CouponPackageBean
import com.mh.shop.http.MainViewModel
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityCouponBinding
import com.mh0505.shop.databinding.ItemTaskCouponBinding
import com.mh55.easy.ext.DividerOrientation
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.noMoreData
import com.mh55.easy.ext.setAdapterEmptyOrListOffset
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ui.activity.BaseRefreshActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @createTime =>  2024/10/10 11:57
 * @class =>  CouponActivity.kt
 * @desciption =>  我的券包
 **/
class CouponActivity : BaseRefreshActivity<ActivityCouponBinding,MainViewModel,ItemTaskCouponBinding,CouponPackageBean>(R.layout.item_task_coupon) {
    override fun setTitleText(): String {
        return "我的券包"
    }

    override fun main(savedInstanceState: Bundle?) {
        super.main(savedInstanceState)
        mBinding.submit.singleClick {
            mViewModel.getCouponPackageReward{
                SuccessActivity.start(5)
            }
        }
    }

    override fun getRecyclerView(): RecyclerView = mBinding.mRecyclerView

    override fun getSmartRefreshLayout(): SmartRefreshLayout = mBinding.mSmartRefreshLayout

    override fun onRefreshData() {
        mViewModel.getMineCouponList(mAdapter.mOffset)
    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.mCouponPackageList.observe(this){
            mAdapter.setAdapterEmptyOrListOffset(it)
            mBinding.mSmartRefreshLayout.noMoreData(true)
        }
    }


    override fun otherRecyclerViewSetting() {
        super.otherRecyclerViewSetting()
        mBinding.mRecyclerView.apply {
            divider {
                linear()
                setColor(com.mh55.easy.R.color.color_transparent.getColor())
                setDivider(12)
                includeVisible = true
                orientation = DividerOrientation.VERTICAL
            }
        }
    }

    override fun isResumeRefresh(): Boolean {
        return true
    }

    override fun convertData(
        baseViewHolder: BaseViewHolder,
        item: CouponPackageBean,
        binding: ItemTaskCouponBinding,
        position: Int
    ) {
        binding.apply {
            couponIcon.load(item.image)
            couponName.text = item.title
            couponTime.text = item.desc
        }
    }

}