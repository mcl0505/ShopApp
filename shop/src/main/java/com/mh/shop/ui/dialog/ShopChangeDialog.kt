package com.mh.shop.ui.dialog

import android.os.Bundle
import android.view.Gravity
import com.mh.shop.http.ButtonListBean
import com.mh.shop.http.MainViewModel
import com.mh.shop.ui.activity.SuccessActivity
import com.mh0505.shop.R
import com.mh0505.shop.databinding.DialogShopChangeBinding
import com.mh0505.shop.databinding.ItemDialogShopChangeBinding
import com.mh55.easy.ext.DividerOrientation
import com.mh55.easy.ext.addAdapter
import com.mh55.easy.ext.divider
import com.mh55.easy.ext.getColor
import com.mh55.easy.ext.linear
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ui.dialog.BaseDialog
import com.mh55.easy.ui.recycler.BindAdapter

class ShopChangeDialog(val id:String,val list:MutableList<ButtonListBean>) : BaseDialog<DialogShopChangeBinding,MainViewModel>() {
    private var mSelectIndex = 0
    private var mAdapter:BindAdapter<ButtonListBean,ItemDialogShopChangeBinding>? = null
    override fun main(savedInstanceState: Bundle?) {
        super.main(savedInstanceState)
        with(mDialogBinding){
            dialogClose.singleClick { dismiss() }
            mRecyclerView.apply {
                linear()
                divider {
                    setColor(com.mh55.easy.R.color.color_transparent.getColor())
                    setDivider(24)
                    orientation  = DividerOrientation.VERTICAL
                    includeVisible = true
                }
                mAdapter = addAdapter<ButtonListBean, ItemDialogShopChangeBinding>(list,
                    R.layout.item_dialog_shop_change){ holder, item, binding ->
                    binding.selectText.text = item.title
                    binding.selectIcon.setImageResource(if (mSelectIndex == holder.layoutPosition)R.mipmap.icon_point_select else R.mipmap.icon_point_unselect)
                    binding.llItem.singleClick {
                        mSelectIndex = holder.layoutPosition
                        mAdapter?.notifyDataSetChanged()
                    }
                }
            }
            submit.singleClick {
                val type = list[mSelectIndex].value
                mViewModel.shopExchange(type,id){
                    dismiss()
                    SuccessActivity.start(1)
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