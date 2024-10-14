package com.mh.shop.adapter

import android.view.View
import coil.load
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mh0505.shop.R
import com.mh0505.shop.databinding.ItemSimpleImageListBinding
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ext.visibleOrGone
import com.mh55.easy.ui.recycler.BindAdapter

class SimpleImageAdapter(val type: Int = 0,val maxSize :Int = 9) : BindAdapter<String, ItemSimpleImageListBinding>(
    R.layout.item_simple_image_list) {
    lateinit var onItemDelete:(item: String, position: Int) -> Unit
    lateinit var onItemClick:(view: View, item: String, position: Int) -> Unit

    override fun convertBind(
        holder: BaseViewHolder,
        item: String,
        binding: ItemSimpleImageListBinding
    ) {
        if (type == 0 && item == "holder") {
            binding.image.setImageResource(R.mipmap.ic_syt_tianjiatupian)
        } else {
            binding.image.load(item)
        }
        binding.delete.visibleOrGone(type == 0 && item != "holder")

        binding.delete.singleClick { if (::onItemDelete.isInitialized) onItemDelete.invoke(item, holder.layoutPosition) }

        binding.root.singleClick { if (::onItemClick.isInitialized) onItemClick.invoke(binding.image, item, holder.layoutPosition) }
    }
}