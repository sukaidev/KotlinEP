package com.sukaidev.order.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sukaidev.core.ext.onClick
import com.sukaidev.order.R
import com.sukaidev.order.data.protocol.ShipAddress
import kotlinx.android.synthetic.main.item_address.view.*

/**
 * Created by sukaidev on 2019/09/04.
 *
 */
class ShipAddressAdapter(data: MutableList<ShipAddress>?) : BaseQuickAdapter<ShipAddress, BaseViewHolder>(R.layout.item_address, data) {

    private var mOptClickListener: OnOptClickListener? = null

    override fun convert(holder: BaseViewHolder, item: ShipAddress) {
        holder.itemView.mSetDefaultTv.isSelected = (item.shipIsDefault == 0)
        holder.itemView.mShipNameTv.text = item.shipUserName
        holder.itemView.mShipMobileTv.text = item.shipUserMobile
        holder.itemView.mShipAddressTv.text = item.shipAddress

        holder.itemView.mSetDefaultTv.onClick {
            mOptClickListener?.let {
                if (holder.itemView.mSetDefaultTv.isSelected) {
                    return@onClick
                }
                item.shipIsDefault = 0
                it.onSetDefault(item)
            }
        }

        holder.itemView.mEditTv.onClick {
            mOptClickListener?.onEdit(item)
        }
        holder.itemView.mDeleteTv.onClick {
            mOptClickListener?.onDelete(item)
        }
    }

    fun setOnOptClickListener(listener: OnOptClickListener) {
        this.mOptClickListener = listener
    }

    /**
     * 操作接口
     */
    interface OnOptClickListener {
        fun onSetDefault(address: ShipAddress)
        fun onEdit(address: ShipAddress)
        fun onDelete(address: ShipAddress)
    }
}