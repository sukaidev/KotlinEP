package com.sukaidev.core.ui.recycler

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by sukaidev on 2019/08/26.
 *
 */
class MultipleViewHolder(view: View) : BaseViewHolder(view) {

    companion object {
        fun create(view: View): MultipleViewHolder {
            return MultipleViewHolder(view)
        }
    }
}