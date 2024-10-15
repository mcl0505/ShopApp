package com.mh.shop.ui.activity


import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.ShopBean
import com.mh.shop.ui.dialog.CommonRichTextDialog
import com.mh.shop.ui.dialog.ShopChangeDialog
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityShopBinding
import com.mh0505.shop.databinding.ItemTaskCouponShopBinding
import com.mh55.easy.ext.DividerOrientation
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.grid
import com.mh55.easy.ext.noMoreData
import com.mh55.easy.ext.setAdapterEmptyOrListOffset
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ui.activity.BaseRefreshActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @createTime =>  2024/10/10 10:30
 * @class =>  CouponShopActivity.kt
 * @desciption =>  券包商城
 **/
class ShopActivity : BaseRefreshActivity<ActivityShopBinding,MainViewModel,ItemTaskCouponShopBinding,ShopBean>(R.layout.item_task_coupon_shop) {
    override fun main(savedInstanceState: Bundle?) {
        super.main(savedInstanceState)
        with(mBinding){
            imgBack.singleClick { finish() }
            mRule.singleClick {
                CommonRichTextDialog("规则","https://qdjtest.qiannengwuxian.com/h5/rule").showDialog(this@ShopActivity)
            }
        }
    }
    override fun getRecyclerView(): RecyclerView = mBinding.mRecyclerView

    override fun getSmartRefreshLayout(): SmartRefreshLayout  = mBinding.mSmartRefreshLayout

    override fun onRefreshData() {
        mViewModel.getShopList()

    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.mShopList.observe(this){
            mAdapter.setAdapterEmptyOrListOffset(it)
            mBinding.mSmartRefreshLayout.noMoreData(false)
        }
    }


    override fun otherRecyclerViewSetting() {
        super.otherRecyclerViewSetting()
        mBinding.mRecyclerView.apply {
            divider {
                grid(2)
                setColor(com.mh55.easy.R.color.color_transparent.getColor())
                setDivider(16)
                startVisible = false
                endVisible = true
                orientation = DividerOrientation.GRID
            }
        }
    }

    override fun isResumeRefresh(): Boolean {
        return true
    }

    override fun convertData(
        baseViewHolder: BaseViewHolder,
        item: ShopBean,
        binding: ItemTaskCouponShopBinding,
        position: Int
    ) {
        binding.apply {
            shopName.text = item.title
            shopIcon.load(item.image)
            shopDes.text = "可领取${item.incomeDays}次 每次领取${item.dailyProfit}"
            shopValue.text = item.desc
            submitChange.singleClick {
                ShopChangeDialog(item.id,item.buttonList).showDialog(this@ShopActivity)
            }
            submitChange.isSelected = item.status == 1
            submitChange.isEnabled = item.status == 1
        }

    }

}