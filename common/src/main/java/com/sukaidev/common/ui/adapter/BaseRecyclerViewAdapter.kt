package com.sukaidev.common.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by sukai on 2019/08/11.
 * RecyclerViewAdapter基类.
 */
abstract class BaseRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder>(var mContext: Context) :
    RecyclerView.Adapter<VH>() {

    // item点击事件
    var mItemClickListener: OnItemClickListener<T>? = null
    // 数据集合
    var dataList: MutableList<T> = mutableListOf()

    /**
     * 设置数据
     * Presenter已处理为null的情况
     */
    fun setData(sources: MutableList<T>) {
        dataList = sources
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener {
            mItemClickListener.let {
                mItemClickListener!!.onItemClick(dataList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    /**
     * Item点击事件回调
     */
    interface OnItemClickListener<in T> {
        fun onItemClick(item: T, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        this.mItemClickListener = listener
    }

}