package com.mh.shop.ui.fragment

import android.os.Bundle
import coil.load
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.MineViewModel
import com.mh.shop.http.livedata.UserLiveData
import com.mh.shop.ui.activity.BindAccountActivity
import com.mh.shop.ui.activity.ConsumeActivity
import com.mh.shop.ui.activity.CurrencyCouponActivity
import com.mh.shop.ui.activity.DistributorActivity
import com.mh.shop.ui.activity.FeedbackAndSuggestionsActivity
import com.mh.shop.ui.activity.GoodsActivity
import com.mh.shop.ui.activity.ListingGoodsActivity
import com.mh.shop.ui.activity.MineFriendActivity
import com.mh.shop.ui.activity.PusherActivity
import com.mh0505.shop.databinding.FragmentMineBinding
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.toast
import com.mh55.easy.ext.visibleOrGone
import com.mh55.easy.ui.fragment.BaseFragment
import com.qnwx.mine.ui.points.MineValueActivity

class MineFragment : BaseFragment<FragmentMineBinding, MainViewModel>() {
    companion object {
        @JvmStatic
        fun newInstance() = MineFragment()
    }

    override fun main(savedInstanceState: Bundle?) {
        initClick()
    }

    private fun initClick() {
        with(mBinding) {
            llCoupon.singleClick {
                MineValueActivity.start("score")
            }
            llOffer.singleClick {
                MineValueActivity.start("contribution")
            }
            llTicket.singleClick {
                MineValueActivity.start("ticket")
            }
            llCurrencyCoupon.singleClick {
                startActivity(CurrencyCouponActivity::class.java)
            }
            llGoods.singleClick {
                startActivity(GoodsActivity::class.java)
            }
            llListingGoods.singleClick {
                startActivity(ListingGoodsActivity::class.java)
            }
            llNumber.singleClick {
                "暂未开放，敬请期待".toast()
            }
            relPusher.singleClick {
                startActivity(PusherActivity::class.java)
            }
            relDistributor.singleClick {
                startActivity(DistributorActivity::class.java)
            }
            relConsume.singleClick {
                startActivity(ConsumeActivity::class.java)
            }
            relFriend.singleClick {
                startActivity(MineFriendActivity::class.java)
            }
            relBindAccount.singleClick {
                startActivity(BindAccountActivity::class.java)
            }
            relFeedback.singleClick {
                FeedbackAndSuggestionsActivity.start(1)
            }
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        UserLiveData.observe(this){info->
            with(mBinding){
                user = info
                mineVip.visibleOrGone(false)
                mineAvatar.load(info.avatar)
            }
        }
    }
}