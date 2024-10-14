package com.mh.shop.ui.fragment

import android.os.Bundle
import com.blankj.utilcode.util.SpanUtils
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.livedata.UserLiveData
import com.mh.shop.ui.activity.CouponActivity
import com.mh.shop.ui.activity.FeedbackAndSuggestionsActivity
import com.mh.shop.ui.activity.IssueActivity
import com.mh.shop.ui.activity.ShopActivity
import com.mh0505.shop.R
import com.mh0505.shop.databinding.FragmentTaskBinding
import com.mh0505.shop.databinding.ItemTaskBinding
import com.mh55.easy.EasyApp
import com.mh55.easy.ext.addAdapter
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ui.fragment.BaseFragment
import com.mh55.easy.ui.recycler.BindAdapter

class TaskFragment : BaseFragment<FragmentTaskBinding, MainViewModel>() {
    companion object {
        @JvmStatic
        fun newInstance() = TaskFragment()
    }

    private var mAdapter:BindAdapter<String,ItemTaskBinding>?=null

    override fun main(savedInstanceState: Bundle?) {
        with(mBinding){
            llShop.singleClick {
                startActivity(ShopActivity::class.java)
            }
            llCoupon.singleClick {
                startActivity(CouponActivity::class.java)
            }
            adValue.singleClick {
                EasyApp.appContext.onErrorTip(9527,"reward")
            }
            issueValue.singleClick {
                startActivity(IssueActivity::class.java)
            }
            suggestValue.singleClick {
                FeedbackAndSuggestionsActivity.start(0)
            }
            mTaskRecyclerView.apply {
                linear()
                mAdapter = addAdapter<String,ItemTaskBinding>(mutableListOf() ,R.layout.item_task){holder, item, binding ->
                    binding.tvContent.text = item
                }
            }

        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        UserLiveData.observe(this){
            mViewModel.getTaskIndex {
                mAdapter?.setNewInstance(it.active)
                with(mBinding){
                    SpanUtils.with(shopDes)
                        .append("每月上限 ").setForegroundColor("#979797".getColor())
                        .append(it.shopping_monthly).setForegroundColor(R.color.color_shop.getColor())
                        .create()
                    SpanUtils.with(shopValue)
                        .append("本月已获得 ").setForegroundColor("#979797".getColor())
                        .append(it.shopping).setForegroundColor(R.color.color_shop.getColor())
                        .create()
                    SpanUtils.with(pushValue)
                        .append("本月已获得 ").setForegroundColor("#979797".getColor())
                        .append(it.promotion).setForegroundColor(R.color.color_shop.getColor())
                        .create()
                    adDes.text = "看完${it.advert_total}个视频获得1个贡献值"
                    adValue.text = "${it.advert}/${it.advert_total}"
                    issueValue.isSelected = !it.exam_status
                    issueValue.isEnabled = !it.exam_status
                    issueValue.text = if (it.exam_status)"已完成" else "去做题"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()



    }
}