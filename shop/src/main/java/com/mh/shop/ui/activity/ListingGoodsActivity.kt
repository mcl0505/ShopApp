package com.mh.shop.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh.shop.http.GoodsInfoBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.MineViewModel
import com.mh.shop.ui.dialog.CommonActionDialog
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityCurrencyCouponBinding
import com.mh0505.shop.databinding.ActivityGoodsBinding
import com.mh0505.shop.databinding.ItemCurrencyCouponBinding
import com.mh0505.shop.databinding.ItemGoodsBinding
import com.mh55.easy.ext.DividerOrientation
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.grid
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.noMoreData
import com.mh55.easy.ext.setAdapterEmptyOrListOffset
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.toast
import com.mh55.easy.ext.visible
import com.mh55.easy.ui.activity.BaseRefreshActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import org.jetbrains.anko.textColor

class ListingGoodsActivity : BaseRefreshActivity<ActivityGoodsBinding, MainViewModel, ItemGoodsBinding, GoodsInfoBean>(R.layout.item_goods) {
    override fun setTitleText(): String {
        mTitlebar?.imgTitleRight?.apply {
            visible()
            setImageResource(R.mipmap.icon_mine_listing_goods_url)
            singleClick {

            }
        }
        return "挂牌商品"
    }
    override fun getRecyclerView(): RecyclerView = mBinding.mRecyclerView

    override fun getSmartRefreshLayout(): SmartRefreshLayout = mBinding.mSmartRefreshLayout

    override fun onRefreshData() {
        mViewModel.getExchangeGoodsList(0,"2")
    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.mGoodsInfoList.observe(this){
            mAdapter.setAdapterEmptyOrListOffset(it)
            mBinding.mSmartRefreshLayout.noMoreData(false)
            mAdapter.mOffset = 0
        }
    }

    override fun otherRecyclerViewSetting() {
        super.otherRecyclerViewSetting()
        getRecyclerView()?.apply {
            grid(2)
            divider {
                setColor(com.mh55.easy.R.color.color_transparent.getColor())
                setDivider(12)
                includeVisible = true
                orientation = DividerOrientation.GRID
            }
        }
    }

    override fun convertData(
        baseViewHolder: BaseViewHolder,
        item: GoodsInfoBean,
        binding: ItemGoodsBinding,
        position: Int
    ) {
        binding.apply {
            goodsName.text = item.title
            goodsImg.load(item.image)
            goodsPrice.text = item.desc
        }
        binding.exchange.singleClick {
            CommonActionDialog(2,item).showDialog(this)
        }
    }

}