package com.mh.shop.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.MineViewModel
import com.mh.shop.http.SuggestListBean
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityFeedbackListBinding
import com.mh0505.shop.databinding.ItemMineListLogViewBinding
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.isLoadMore
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.noMoreData
import com.mh55.easy.ext.setAdapterEmptyOrListOffset
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ui.activity.BaseRefreshActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import org.jetbrains.anko.textColor

/**
 * @createTime =>  2024/4/13 15:38
 * @class =>  FeedbackListActivity.kt
 * @desciption =>  反馈列表
 **/
class FeedbackListActivity : BaseRefreshActivity<ActivityFeedbackListBinding, MainViewModel, ItemMineListLogViewBinding, SuggestListBean>(
    R.layout.item_mine_list_log_view) {

    override fun setTitleText(): String {
        return "反馈列表"
    }

    override fun isResumeRefresh(): Boolean {
        return true
    }
    override fun getRecyclerView(): RecyclerView = mBinding.mRecyclerView

    override fun getSmartRefreshLayout(): SmartRefreshLayout = mBinding.mSmartRefreshLayout

    override fun onRefreshData() {
        mViewModel.getSuggestList(mAdapter.mOffset)
    }

    override fun otherRecyclerViewSetting() {
        super.otherRecyclerViewSetting()
        getRecyclerView().apply {
            linear()
            divider {
              setColor(com.mh55.easy.R.color.color_transparent.getColor())
              setDivider(12)
              includeVisible = true
            }
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.sSuggestListBeanSuccess.observe(this){
            mAdapter.setAdapterEmptyOrListOffset(mList = it)
            getSmartRefreshLayout().noMoreData(it.isLoadMore())
        }
    }

    override fun convertData(
        baseViewHolder: BaseViewHolder,
        item: SuggestListBean,
        binding: ItemMineListLogViewBinding,
        position: Int
    ) {
        binding.apply {
            if (item.status == 0) {
                tvTitle.text = "待回复"
                tvTitle.textColor = R.color.setup_yellow.getColor()
            } else {
                tvTitle.text = "已回复"
                tvTitle.textColor = R.color.setup_green.getColor()
            }

            tvContent.text = item.content
            tvCreatedAt.text = item.create_at

            main.singleClick {
                val bundle = Bundle()
                bundle.putParcelable("data",item)
                startActivity(FeedbackDetailsActivity::class.java, bundle = bundle)
            }
        }
    }

}