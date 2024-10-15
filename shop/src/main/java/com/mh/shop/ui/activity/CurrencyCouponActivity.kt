package com.mh.shop.ui.activity

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh.shop.http.GoodsInfoBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.ui.dialog.CommonActionDialog
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityCurrencyCouponBinding
import com.mh0505.shop.databinding.ItemCurrencyCouponBinding
import com.mh55.easy.ext.DividerOrientation
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.noMoreData
import com.mh55.easy.ext.setAdapterEmptyOrListOffset
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ui.activity.BaseRefreshActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class CurrencyCouponActivity : BaseRefreshActivity<ActivityCurrencyCouponBinding,MainViewModel,ItemCurrencyCouponBinding,GoodsInfoBean>(R.layout.item_currency_coupon) {
    override fun setTitleText(): String {
        return "商城通用优惠券"
    }
    override fun getRecyclerView(): RecyclerView = mBinding.mRecyclerView

    override fun getSmartRefreshLayout(): SmartRefreshLayout = mBinding.mSmartRefreshLayout

    override fun onRefreshData() {
        mViewModel.getExchangeGoodsList(0,"3")
    }

    override fun isResumeRefresh(): Boolean {
        return true
    }

    override fun otherRecyclerViewSetting() {
        super.otherRecyclerViewSetting()
        getRecyclerView()?.apply {
            linear()
            divider {
                setColor(com.mh55.easy.R.color.color_transparent.getColor())
                setDivider(12)
                includeVisible = true
                orientation = DividerOrientation.VERTICAL
            }
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.mGoodsInfoList.observe(this){
            mAdapter.setAdapterEmptyOrListOffset(it)
            mBinding.mSmartRefreshLayout.noMoreData(false)
            mAdapter.mOffset = 0
        }
    }

    override fun convertData(
        baseViewHolder: BaseViewHolder,
        item: GoodsInfoBean,
        binding: ItemCurrencyCouponBinding,
        position: Int
    ) {
        binding.money.text = item.amount
        binding.couponName.text = "${item.amount.toDouble().toInt()}元优惠券"
        binding.exchange.singleClick {
            CommonActionDialog(3,item).showDialog(this)
        }
    }

}