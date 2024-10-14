package com.mh.shop.ui.dialog

import android.os.Bundle
import android.view.Gravity
import com.mh0505.shop.databinding.DialogCommonTipBinding
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.visibleOrGone
import com.mh55.easy.mvvm.BaseViewModel
import com.mh55.easy.ui.dialog.BaseDialog
/**
 * @createTime =>  2024/4/11 9:49
 * @class =>  CommonTipDialog.kt
 * @desciption =>  公告提示弹框
 **/
class CommonTipDialog(
    val title:String = "",
    val tip:String = "",
    val content:String = "",
    val leftText: String = "取消",
    val rightText: String = "确定",
    val block:(type:Boolean)->Unit) : BaseDialog<DialogCommonTipBinding,BaseViewModel>() {
    override fun main(savedInstanceState: Bundle?) {
        with(mDialogBinding){
            tvTipTitle.visibleOrGone(title.isNotEmpty())
            tvTipContent.visibleOrGone(tip.isNotEmpty())
            tvTipContent02.visibleOrGone(content.isNotEmpty())

            tvTipTitle.text = title
            tvTipContent.text = tip
            tvTipContent02.text = content

            tvCancel.text = leftText
            tvDetermine.text = rightText

            tvCancel.singleClick {
                block.invoke(false)
                dismiss()
            }

            tvDetermine.singleClick {
                block.invoke(true)
                dismiss()
            }

        }
    }

    override fun setGravity(): Int {
        return Gravity.CENTER
    }
}