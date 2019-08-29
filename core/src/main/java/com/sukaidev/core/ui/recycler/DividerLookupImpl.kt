package com.sukaidev.core.ui.recycler

import com.choices.divider.Divider
import com.choices.divider.DividerItemDecoration

/**
 * Created by sukaidev on 2019/08/27.
 * 分割线.
 */
class DividerLookupImpl(private val color: Int, private val size: Int) :
    DividerItemDecoration.DividerLookup {

    override fun getVerticalDivider(position: Int): Divider {
        return Divider.Builder()
            .size(size)
            .color(color)
            .build()
    }

    override fun getHorizontalDivider(position: Int): Divider {
        return Divider.Builder()
            .size(size)
            .color(color)
            .build()
    }
}