package com.mh.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AppUtils
import com.mh.shop.adapter.SimpleImageAdapter
import com.mh.shop.http.CancellationReasonBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.http.MineViewModel
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ActivityFeedbackAndSuggestionsBinding
import com.mh55.easy.ext.DividerOrientation
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.getImageDialogPermission
import com.mh55.easy.ext.grid
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.toast
import com.mh55.easy.ext.visibleOrGone
import com.mh55.easy.manager.AppManager
import com.mh55.easy.ui.activity.BaseActivity
import com.mh55.easy.ui.dialog.ImageSelectDialog
import com.mh55.easy.utils.LogUtil
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.dialog.DefaultDialog
import com.qnwx.mine.ui.points.MineValueActivity
import org.jetbrains.anko.textColor


class FeedbackAndSuggestionsActivity :
    BaseActivity<ActivityFeedbackAndSuggestionsBinding, MainViewModel>() {
    var mType: Int = 0
    var defaultSelect = 0
    private val max = 6
    val mPicAdapter by lazy { SimpleImageAdapter() }
    companion object {
        fun start(type: Int) {
            val intent = Intent(
                AppManager.peekActivity() as AppCompatActivity,
                FeedbackAndSuggestionsActivity::class.java
            )
            intent.putExtra("type", type)
            AppManager.start(intent)
        }
    }

    override fun setTitleText(): String {
        mTitlebar?.apply {
            tvTitleRight.apply {
                text = "反馈结果"
                textColor = com.mh55.easy.R.color.color_333333.getColor()
                visibleOrGone(true)
                singleClick {
                    startActivity(FeedbackListActivity::class.java)
                }
            }

        }
        return "问题反馈"
    }

    override fun main(savedInstanceState: Bundle?) {
        defaultSelect = intent.getIntExtra("type",0)
        LogUtil.d("defaultSelect=$defaultSelect")
        initFeedbackRecyclerView()
        initRecyclerView()
        mBinding.apply {

            etFeedbackContent.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        var num: Int = 0
                        //文字更改后
                        if (!TextUtils.isEmpty(it)) {
                            num = it.length
                        }
                        var desS = ""
                        desS = if (it.length > 300) {
//                            it.delete(0, it.length - 1)
                            " <font color='#FF0000'>${num}</font>/<font color='#959595'>300</font>"
                        } else {
                            " <font color='#959595'>${num}</font>/<font color='#959595'>300</font>"
                        }
                        tvNum.text = "${it.length}/300"
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

            stvConfirm.singleClick {

                val feedbackContent = etFeedbackContent.text.toString()
                if (feedbackContent.isEmpty()) {
                    "反馈内容不能为空".toast()
                    return@singleClick
                }
                if (feedbackContent.length > 300) {
                    "反馈内容不能大于300字".toast()
                    return@singleClick
                }

                val phone = etPhone.text.toString().trim()
                val name = etLinkman.text.toString().trim()

                if (phone.isNullOrEmpty()) {
                    "请填写联系电话".toast()
                    return@singleClick
                }

                if (name.isNullOrEmpty()) {
                    "请填写联系人名称".toast()
                    return@singleClick
                }

                if (mPicAdapter.data.size == 1) {
                    mViewModel.setSuggestAdd(
                        type = mType,
                        content = feedbackContent,
                        mobile = phone,
                        linkMan = name
                    ){
                        "问题反馈成功".toast()
                        SuccessActivity.start(6)
                        finish()
                    }
                } else {
                    val list = mutableListOf<String>()
                    list.addAll(mPicAdapter.data)
                    list.remove("holder")
                    var imageStr = StringBuilder()
                    list.forEachIndexed { index, s ->
                        if (index == 0) {
                            imageStr.append(s)
                        } else {
                            imageStr.append(",")
                            imageStr.append(s)
                        }
                    }

                    mViewModel.setSuggestAdd(
                        type = mType,
                        images = imageStr.toString(),
                        content = feedbackContent,
                        mobile = phone,
                        linkMan = name
                    ){
                        "问题反馈成功".toast()
                        SuccessActivity.start(6)
                        finish()
                    }


                }


            }
        }
    }

    private fun initFeedbackRecyclerView() {

        val mList = arrayListOf<CancellationReasonBean>()
        mList.add(CancellationReasonBean("建言", type = 1))
        mList.add(CancellationReasonBean("投诉", type = 2))
        mList.add(CancellationReasonBean("不够优惠", type = 2))
        mList.add(CancellationReasonBean("其他", type = 3))

        mBinding.mLabelsView.apply {

            setLabels(mList) { label, position, data ->
                data.name
            }
            setSelects(defaultSelect)
            setOnLabelClickListener { label, data, position ->
                mType = (data as CancellationReasonBean).type
            }
        }

    }

    private fun initRecyclerView() {
        mBinding.apply {
            mRecyclerViewPic.apply {
                grid(4)
                divider {
                    setColor(com.mh55.easy.R.color.color_transparent.getColor())
                    setDivider(10)
                    orientation = DividerOrientation.GRID
                }

                adapter = mPicAdapter.apply {
                    setList(listOf("holder") as MutableList<String>)
                    onItemClick = { view, item, position ->
                        PermissionX.init(this@FeedbackAndSuggestionsActivity)
                            .permissions(getImageDialogPermission())
                            .explainReasonBeforeRequest()
                            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                                if (beforeRequest) {
                                    val msg =
                                        "${AppUtils.getAppName()} \n需要多媒体权限进行图片获取"
                                    val dialog = DefaultDialog(
                                        mContext, getImageDialogPermission(), msg, "授权", "拒绝",
                                        R.color.color_shop.getColor(),
                                        R.color.color_shop.getColor()
                                    )
                                    scope.showRequestReasonDialog(dialog)
                                }

                            }
                            .request { allGranted, grantedList, deniedList ->
                                if (allGranted) {
                                    ImageSelectDialog.Builder()
                                        .setSingle(true)
                                        .setCompress(true)
                                        .setEnableCrop(false)
                                        .setOnSelectCallBackListener {
                                            if (it.size > 0) {
                                                val path = it[0]
                                                mViewModel.getFile(path) {url->
                                                    data[position] = url
                                                    if (data.size < max && !data.contains("holder")) {
                                                        data.add("holder")
                                                    }
                                                    notifyDataSetChanged()
                                                }



                                            }
                                        }
                                        .build()
                                        .show(supportFragmentManager)
                                }
                            }
                    }

                    onItemDelete = { item, position ->
                        data.removeAt(position)
                        if (data.size < max && !data.contains("holder")) {
                            data.add("holder")
                        }
                        notifyDataSetChanged()
                    }
                }
            }

        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
//        UserLiveData.observe(this) {
//            mBinding.apply {
//                etPhone.setEditContent(it.mobile)
//                etLinkman.setEditContent(it.idName)
//            }
//        }
    }
}