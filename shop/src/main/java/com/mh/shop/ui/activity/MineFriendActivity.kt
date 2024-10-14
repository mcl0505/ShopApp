package com.mh.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh.shop.http.LogBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.MineFriendBean
import com.mh.shop.http.TabValue
import com.mh.shop.http.livedata.UserLiveData
import com.mh.shop.ui.dialog.CommonActionDialog
import com.mh.shop.ui.dialog.CommonTipDialog
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityMineFriendBinding
import com.mh0505.shop.databinding.ItemMineFriendBinding
import com.mh55.easy.ext.DividerOrientation
import com.mh55.easy.ext.addAdapter
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.gone
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.noMoreData
import com.mh55.easy.ext.setAdapterEmptyOrListOffset
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.toast
import com.mh55.easy.ext.visible
import com.mh55.easy.ext.visibleOrGone
import com.mh55.easy.manager.AppManager
import com.mh55.easy.ui.activity.BaseRefreshActivity
import com.mh55.easy.ui.recycler.BindAdapter
import com.qnwx.mine.ui.points.MineValueActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import org.jetbrains.anko.textColor

class MineFriendActivity : BaseRefreshActivity<ActivityMineFriendBinding, MainViewModel, ItemMineFriendBinding, MineFriendBean>(
    R.layout.item_mine_friend
) {

    override fun main(savedInstanceState: Bundle?) {
        super.main(savedInstanceState)

        mBinding.apply {
            viewTitle.apply {
                titleLine.visibleOrGone(false)
                imgTitleLeft.visibleOrGone(true)
                imgTitleLeft.setImageResource(R.mipmap.icon_task_back)
                tvTitleCenter.textColor = com.mh55.easy.R.color.color_333333.getColor()
                imgTitleLeft.singleClick { finish() }
            }
        }
    }

    override fun getRecyclerView(): RecyclerView = mBinding.mRecyclerView

    override fun getSmartRefreshLayout(): SmartRefreshLayout = mBinding.mSmartRefresh

    override fun otherRecyclerViewSetting() {
        super.otherRecyclerViewSetting()
        mBinding.mRecyclerView.apply {
            linear()
            divider {
                setColor(com.mh55.easy.R.color.color_transparent.getColor())
                setDivider(12)
                orientation = DividerOrientation.VERTICAL
                startVisible = false
                endVisible = true
            }
        }
    }

    override fun onRefreshData() {
        mViewModel.getUserFriend(mAdapter.mOffset)
    }

    override fun isResumeRefresh(): Boolean {
        return true
    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.mUserFriendList.observe(this){value->
            mAdapter.setAdapterEmptyOrListOffset(value, marginTop =10)
            mBinding.mSmartRefresh.noMoreData(true)
        }

        UserLiveData.observe(this){
            mBinding.tvMoney.text = it.totalActive
        }
    }

    override fun convertData(
        baseViewHolder: BaseViewHolder,
        item: MineFriendBean,
        binding: ItemMineFriendBinding,
        position: Int
    ) {
        binding.apply {
           friendAvatar.load(item.avatar)
            friendName.text = item.nickname
            friendMobile.text = "Tel：${item.mobile}"
            friendLevel.text = "类别：${item.levelName}"
        }
    }

}