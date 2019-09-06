package com.sukaidev.core.ui.scanner

import android.content.Context
import android.util.AttributeSet

import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.zbar.ZBarScannerView

/**
 * Created by sukaidev on 2019/05/13.
 */
class ScanView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) : ZBarScannerView(context, attributeSet) {

    override fun createViewFinderView(context: Context): IViewFinder {
        return LatteViewFinderView(context)
    }
}
