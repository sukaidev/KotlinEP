package com.sukaidev.core.ui.recycler

import androidx.annotation.ColorInt
import com.choices.divider.DividerItemDecoration

/**
 * Created by sukaidev on 2019/05/03.
 *  分割线.
 */
class BaseDecoration private constructor(@ColorInt color: Int, size: Int) :
    DividerItemDecoration() {

    init {
        setDividerLookup(DividerLookupImpl(color, size))
    }

    companion object {

        fun create(@ColorInt color: Int, size: Int): BaseDecoration {
            return BaseDecoration(color, size)
        }
    }
}
