package com.mh.shop.ui.activity

import android.os.Bundle
import com.mh.shop.adapter.SimpleImageAdapter
import com.mh.shop.http.SuggestListBean
import com.mh0505.shop.databinding.ActivityFeedbackDetailsBinding
import com.mh55.easy.ext.DividerOrientation
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.grid
import com.mh55.easy.ui.activity.BaseActivity
import com.zzhoujay.richtext.RichText

class FeedbackDetailsActivity : BaseActivity<ActivityFeedbackDetailsBinding, MineViewModel>() {
    var bean: SuggestListBean?=null

    private val mPicAdapter by lazy { SimpleImageAdapter(1) }

    override fun setTitleText(): String {
        return "反馈详情"
    }
    override fun main(savedInstanceState: Bundle?) {
        bean = intent.extras?.getParcelable<SuggestListBean>("data")
        mBinding.apply {
            mRecyclerViewPic.apply {
                grid(2)
                divider {
                    setColor(com.mh55.easy.R.color.color_transparent.getColor())
                    setDivider(10)
                    includeVisible = true
                    orientation = DividerOrientation.GRID
                }
                adapter = mPicAdapter
            }
            bean?.let {listBean->
                mPicAdapter.setList(listBean.image.map { it })
                etFeedbackContent.text = listBean.content
                RichText.from(listBean.r_content).into(etFeedbackRContent)
                etPhone.setText(listBean.link_phone)
                etLinkMan.setText(listBean.link_man)
                feedbackTime.text = "反馈时间：${listBean.create_at}"
                rFeedbackTime.text = "回复时间：${listBean.update_at}"
            }

        }
    }

}