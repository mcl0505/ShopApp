package com.mh.shop.ui.dialog

import android.os.Bundle
import android.view.Gravity
import com.blankj.utilcode.util.ClipboardUtils
import com.mh.shop.http.GoodsInfoRecordBean
import com.mh0505.shop.databinding.DialogPickUpGoodsBinding
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.toast
import com.mh55.easy.ext.visibleOrGone
import com.mh55.easy.ui.dialog.BaseDialog

class PickUpGoodsDialog(val data:GoodsInfoRecordBean) : BaseDialog<DialogPickUpGoodsBinding,MineViewModel>() {

    override fun main(savedInstanceState: Bundle?) {
        super.main(savedInstanceState)
        with(mDialogBinding){
            title.text = data.title
            pickTime.text = data.create_at
            pickNumber .text = data.num
            pickName .text = data.name
            pickMobile.text = data.mobile
            pickAddress.text = data.area_text
            pickAddressNumber.text = data.logistics_number
            close.singleClick { dismiss() }
            wlNumber.visibleOrGone(!data.logistics_number.isNullOrEmpty())
            pickAddressNumber.singleClick {
                "复制成功".toast()
                ClipboardUtils.copyText(data.logistics_number)
            }

        }
    }

    override fun setGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun canCancel(): Boolean {
        return false
    }

}