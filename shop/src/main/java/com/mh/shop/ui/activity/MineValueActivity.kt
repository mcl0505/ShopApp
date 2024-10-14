package com.qnwx.mine.ui.points

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh.shop.http.LogBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.MineViewModel
import com.mh.shop.http.TabValue
import com.mh.shop.ui.activity.SuccessActivity
import com.mh.shop.ui.dialog.CommonActionDialog
import com.mh.shop.ui.dialog.CommonTipDialog
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityMineValueBinding
import com.mh0505.shop.databinding.ItemMineValueBinding
import com.mh0505.shop.databinding.ItemMineValueTabBinding
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
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import org.jetbrains.anko.textColor

/**
 * @createTime =>  2024/4/12 16:35
 * @class =>  MineValueActivity.kt
 * @desciption =>  曝光量
 **/

class MineValueActivity :
    BaseRefreshActivity<ActivityMineValueBinding, MainViewModel, ItemMineValueBinding, LogBean>(
        R.layout.item_mine_value
    ) {

    var mType: String = "" //score=优惠券  contribution=贡献值   ticket=票

    private var mTabAdapter : BindAdapter<TabValue,ItemMineValueTabBinding>?=null
    private var mTab = "0"

    companion object {
        fun start(type: String) {
            val intent = Intent(
                AppManager.peekActivity() as AppCompatActivity,
                MineValueActivity::class.java
            )
            intent.putExtra("type", type)
            AppManager.start(intent)
        }
    }

    override fun main(savedInstanceState: Bundle?) {
        mType = intent.getStringExtra("type")?:""
        super.main(savedInstanceState)

        if (mType.isNullOrEmpty()) {
            "类型传递错误".toast()
            return
        }

        mBinding.apply {
            viewTitle.apply {
                titleLine.visibleOrGone(false)
                imgTitleLeft.visibleOrGone(true)
                imgTitleLeft.setImageResource(R.mipmap.icon_task_back)
                tvTitleCenter.textColor = com.mh55.easy.R.color.color_333333.getColor()
                imgTitleLeft.singleClick { finish() }
            }
            when (mType) {
                "score" -> {
                    bg.setImageResource(R.mipmap.bg_mine_value_coupon)
                    viewTitle.tvTitleCenter.text = "优惠券"
                    tvTip.text = "当前余额"
                    icon.setImageResource(R.mipmap.icon_mine_value_coupon)
                    action.gone()
                    mTabRecyclerView.gone()
                }

                "contribution" -> {
                    bg.setImageResource(R.mipmap.bg_mine_value_offer)
                    viewTitle.tvTitleCenter.text = "贡献值"
                    tvTip.text = "当前余额"
                    icon.setImageResource(R.mipmap.icon_mine_value_offer)
                    mTabRecyclerView.visible()
                    action.apply {
                        gone()
                        text = "兑换VIP月卡"
                        singleClick {
                            CommonTipDialog(
                                content = "兑换VIP月卡可自动一键领取券包，需消耗50贡献值。\n" +
                                        "是否确定兑换VIP月卡？"
                            ) {
                                if (it) {
                                    SuccessActivity.start(3)
                                }
                            }.showDialog(this@MineValueActivity)


                        }
                    }

                    mTabRecyclerView.apply {
                        linear(RecyclerView.HORIZONTAL)
                        divider {
                            setColor(com.mh55.easy.R.color.color_transparent.getColor())
                                setDivider(10)
                            includeVisible = true
                            orientation = DividerOrientation.HORIZONTAL
                        }
                        mTabAdapter = addAdapter<TabValue,ItemMineValueTabBinding>(mutableListOf(),R.layout.item_mine_value_tab){holder, item, binding ->
                            binding.tvTab.apply {
                                text = item.title
                                isSelected = mTab == item.value
                                singleClick {
                                    mTab = item.value
                                    mAdapter.mOffset = 0
                                    onRefreshData()
                                    mTabAdapter?.notifyDataSetChanged()
                                }
                            }

                        }
                    }
                }

                "ticket" -> {
                    bg.setImageResource(R.mipmap.bg_mine_value_ticket)
                    viewTitle.tvTitleCenter.text = "票"
                    tvTip.text = "当前余额"
                    icon.setImageResource(R.mipmap.icon_mine_value_ticket)
                    action.apply {
                        visible()
                        text = "提票"
                        singleClick {
                            CommonActionDialog(4, ticketBalance = mViewModel.mUserLog.value!!.num).showDialog(this@MineValueActivity)
                        }
                    }
                    mTabRecyclerView.gone()
                }

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
        mViewModel.getUserLog(mAdapter.mOffset, mType,mTab)
    }

    override fun isResumeRefresh(): Boolean {
        return true
    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.mUserLog.observe(this){value->
            mBinding.apply {
                tvMoney.text = value.num
                mAdapter.setAdapterEmptyOrListOffset(value.list, marginTop =10)
                mBinding.mSmartRefresh.noMoreData(true)
                mTabAdapter?.setNewInstance(value.tab)
            }
        }
    }

    override fun convertData(
        baseViewHolder: BaseViewHolder,
        item: LogBean,
        binding: ItemMineValueBinding,
        position: Int
    ) {
        binding.apply {
            mTitle.text = item.desc
            mTime.text = item.createAt
            mNumber.text = item.changeValue
            mNumber.isSelected = item.state != 2
        }
    }

}