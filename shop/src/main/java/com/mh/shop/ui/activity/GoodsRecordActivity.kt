package com.mh.shop.ui.activity

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh.shop.http.GoodsInfoRecordBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.ui.dialog.PickUpGoodsDialog
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityGoodsBinding
import com.mh0505.shop.databinding.ItemGoodsRecordBinding
import com.mh55.easy.ext.DividerOrientation
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.noMoreData
import com.mh55.easy.ext.setAdapterEmptyOrListOffset
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ui.activity.BaseRefreshActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class GoodsRecordActivity : BaseRefreshActivity<ActivityGoodsBinding, MainViewModel, ItemGoodsRecordBinding, GoodsInfoRecordBean>(R.layout.item_goods_record) {
    override fun setTitleText(): String {
        return "兑换记录"
    }
    override fun getRecyclerView(): RecyclerView = mBinding.mRecyclerView

    override fun getSmartRefreshLayout(): SmartRefreshLayout = mBinding.mSmartRefreshLayout

    override fun onRefreshData() {
        mViewModel.exchangeGoodsRecord(mAdapter.mOffset)
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
        mViewModel.mGoodsInfoRecordList.observe(this){
            mAdapter.setAdapterEmptyOrListOffset(it)
            mBinding.mSmartRefreshLayout.noMoreData(false)
        }
    }

    override fun convertData(
        baseViewHolder: BaseViewHolder,
        item: GoodsInfoRecordBean,
        binding: ItemGoodsRecordBinding,
        position: Int
    ) {
        binding.apply {
            mTitle.text = item.title
            mTime.text = item.create_at
            mNumber.text = "兑换数量：${item.num}"
        }
        binding.root.singleClick {
            PickUpGoodsDialog(item).showDialog(this)
        }
    }

}