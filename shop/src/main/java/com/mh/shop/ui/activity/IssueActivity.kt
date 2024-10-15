package com.mh.shop.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.SpanUtils
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.OptionBean
import com.mh.shop.http.SubmitOptionBean
import com.mh.shop.http.TestBean
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityIssueBinding
import com.mh0505.shop.databinding.ItemIssueBinding
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.listToString
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.toast
import com.mh55.easy.ui.activity.BaseActivity
import com.mh55.easy.ui.recycler.BindAdapter

class IssueActivity : BaseActivity<ActivityIssueBinding, MainViewModel>() {

    //题目列表
    private val mIssueList:MutableList<TestBean> = mutableListOf()
    private val mAnswerList:MutableList<OptionBean> = mutableListOf()
    private var mSelectIssueIndex = 0
    private val mSelectIds = mutableListOf<String>()
    //考题提交列表
    private val mSubmitList = mutableListOf<SubmitOptionBean>()


    override fun main(savedInstanceState: Bundle?) {
        mViewModel.getIssueList()
        mSelectIssueIndex = 0
        with(mBinding) {
            imgBack.singleClick { finish() }
            nextIssue.singleClick {
                //这里还需要判断答案正确与否
                if (mSelectIds.isEmpty()){
                    "请选择答案并提交".toast()
                    return@singleClick
                }

                val testInfo = mIssueList[mSelectIssueIndex]
                when(testInfo.type){
                    2->{
                        val tagAnswerList = testInfo.answer
                        if (!tagAnswerList.containsAll(mSelectIds)){
                            "答案错误，无法进入下一题".toast()
                            return@singleClick
                        }

                    }
                    else->{
                        val tagAnswer = testInfo.answer[0]
                        val optionAnswer = mSelectIds[0]

                        if (tagAnswer != optionAnswer){
                            "答案错误，无法进入下一题".toast()
                            return@singleClick
                        }

                    }
                }


                mSubmitList.add(SubmitOptionBean(testInfo.id,testInfo.type,testInfo.seq,mSelectIds.listToString()))


                mSelectIssueIndex ++
                if (mSelectIssueIndex >= mIssueList.size){
                    mViewModel.submitIssue(mViewModel.mIssueList.value!!.id,mSubmitList){
                        //在这里提交题目信息
                        SuccessActivity.start(2)
                        finish()
                    }
                }else {
                    updateIssue()
                }

            }
            mRecyclerView.apply {
                linear()
                adapter = mAdapter
            }
        }

    }

    override fun initViewObservable() {
        super.initViewObservable()
        mViewModel.mIssueList.observe(this){
            this.mIssueList.clear()
            this.mIssueList.addAll(it.list)
            updateIssue()
        }
    }

    private fun updateIssue() {
        mSelectIds.clear()
        val testBean = mIssueList[mSelectIssueIndex]
        mAdapter.setNewInstance(mutableListOf())
        mAdapter.setNewInstance(testBean.option)
        mBinding.issueTitle.text = "${testBean.seq}、(${getIssueType(testBean.type)})${testBean.title}"
        updateNumber()
    }

    private fun getIssueType(type: Int) = when (type) {
        1 -> "单选题"
        2 -> "多选题"
        3 -> "判断题"
        else -> ""
    }

    private fun updateNumber() {
        SpanUtils.with(mBinding.issueNumber)
            .append((mSelectIssueIndex + 1).toString()).setForegroundColor(R.color.color_shop.getColor())
            .append("/").setForegroundColor("#646566".getColor())
            .append(mIssueList.size.toString()).setForegroundColor("#646566".getColor())
            .create()
    }

    private val mAdapter = object : BindAdapter<OptionBean, ItemIssueBinding>(R.layout.item_issue) {
        override fun convertBind(holder: BaseViewHolder, item: OptionBean, binding: ItemIssueBinding) {
            val mItemId = item.id

            binding.apply {
                issueContent.text = item.sort+"."+item.text
                val mType = mIssueList[mSelectIssueIndex].type
                imgIssue.setImageResource(R.mipmap.icon_point_unselect)
                llIssue.setBackgroundColor(com.mh55.easy.R.color.color_transparent.getColor())


                if (mSelectIds.contains(mItemId)){
                    imgIssue.setImageResource(R.mipmap.icon_point_select)
                    llIssue.setBackgroundColor("#FFF9F0".getColor())
                }else {
                    imgIssue.setImageResource(R.mipmap.icon_point_unselect)
                    llIssue.setBackgroundColor(com.mh55.easy.R.color.color_transparent.getColor())
                }


                llIssue.singleClick {

                    when(mType){
                        2->{
                            if (mSelectIds.contains(mItemId)){
                                mSelectIds.remove(mItemId)
                            }else {
                                mSelectIds.add(mItemId)
                            }

                            notifyDataSetChanged()

                        }
                        else->{
                            if (mSelectIds.isEmpty()){
                                mSelectIds.add(mItemId)
                            }else {
                                mSelectIds.clear()
                                mSelectIds.add(mItemId)
                            }
                            notifyDataSetChanged()
                        }
                    }

                }
            }
        }

    }

}