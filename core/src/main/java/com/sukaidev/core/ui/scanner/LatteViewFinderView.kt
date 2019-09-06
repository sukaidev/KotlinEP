package com.sukaidev.core.ui.scanner

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.sukaidev.core.R
import me.dm7.barcodescanner.core.ViewFinderView

/**
 * Created by sukaidev on 2019/05/13.
 */
class LatteViewFinderView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) : ViewFinderView(context, attributeSet) {

    init {
        mSquareViewFinder = true
        mBorderPaint.color = ContextCompat.getColor(getContext(), R.color.app_main)
        mLaserPaint.color = ContextCompat.getColor(getContext(), R.color.app_main)
    }
}
