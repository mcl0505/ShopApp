package com.mh.shop.ui.dialog

import android.os.Bundle
import android.view.Gravity
import com.mh.shop.ext.isChinesePhoneNumber
import com.mh.shop.http.GoodsInfoBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.ui.activity.SuccessActivity
import com.mh0505.shop.databinding.DialogCommonActionBinding
import com.mh55.easy.ext.gone
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.toast
import com.mh55.easy.ext.visible
import com.mh55.easy.ui.dialog.BaseDialog

class CommonActionDialog(val type:Int,val bean:GoodsInfoBean?=null,val ticketBalance:String = "0") : BaseDialog<DialogCommonActionBinding, MainViewModel>() {
    override fun main(savedInstanceState: Bundle?) {
        super.main(savedInstanceState)

        with(mDialogBinding){
            dialogClose.singleClick { dismiss() }
            when(type){
                //提票
                4->{
                    mTitle.text = "提票"
                    llGoods.gone()
                    inputAll.visible()
                    inputTopTitle.text = "请输入提票数量"
                    mInputTopEdit.hint = "请输入数量"
                    inputHint.text = "当前票数余额$ticketBalance"
                }
                //通券兑换
                3->{
                    mTitle.text = "兑换商城通用优惠券"
                    llGoods.gone()
                    inputAll.gone()
                    inputTopTitle.text = "请输入兑换的商城通用优惠券数量"
                    mInputTopEdit.hint = "请输入兑换的商城通用优惠券数量"
                    inputHint.text = "需消耗${bean?.desc}"
                }
                //兑换挂牌商品
                2->{
                    mTitle.text = "兑换挂牌商品"
                    llGoods.gone()
                    inputAll.gone()
                    inputTopTitle.text = "请输入兑换的挂牌商品数量"
                    mInputTopEdit.hint = "请输入兑换的挂牌商品数量"
                    inputHint.text = "需消耗${bean?.desc}"
                }
                //兑换产品
                1->{
                    mTitle.text = "兑换产品"
                    llGoods.visible()
                    inputAll.gone()
                    inputTopTitle.text = "请输入要兑换的产品数量"
                    mInputTopEdit.hint = "请输入要兑换的产品数量"
                    inputHint.text = "当前产品兑换需消耗${bean?.desc}"
                }
            }
            submit.singleClick {
                val number = mInputTopEdit.text.toString()

                if (number.isNullOrEmpty() || number == "0"){
                    "请输入兑换数量".toast()
                    return@singleClick
                }
                when(type){
                    4->{
                        mViewModel.upTicket(number){
                            dismiss()
                            SuccessActivity.start(5)
                        }

                    }
                    else->{

                        var name = ""
                        var mobile = ""
                        var address = ""
                        if (type == 1){
                            name = mInputUserName.text.toString()
                            mobile = mInputUserMobile.text.toString()
                            address = mInputUserAddress.text.toString()

                            if (name.isNullOrEmpty()){
                                "请输入收货人姓名".toast()
                                return@singleClick
                            }

                            if (!mobile.isChinesePhoneNumber()){
                                "请输入收货人联系电话".toast()
                                return@singleClick
                            }

                            if (address.isNullOrEmpty()){
                                "请输入收货人地址".toast()
                                return@singleClick
                            }

                        }

                        mViewModel.exchangeGoods(bean?.id?:"",number,name,mobile,address){
                            dismiss()
                            SuccessActivity.start(3)
                        }
                    }
                }
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