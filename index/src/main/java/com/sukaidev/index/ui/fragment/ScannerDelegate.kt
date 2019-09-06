package com.sukaidev.index.ui.fragment

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.core.ui.scanner.ScanView
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/05/13.
 * 二维码扫描页.
 */
class ScannerDelegate : ProxyDelegate(), ZBarScannerView.ResultHandler {

    private var mScanView: ScanView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mScanView == null) {
            mScanView = ScanView(context!!)
        }
        mScanView!!.setAutoFocus(true)
        mScanView!!.setResultHandler(this)
    }

    override fun onResume() {
        super.onResume()
        if (mScanView != null) {
            mScanView!!.startCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mScanView != null) {
            mScanView!!.stopCameraPreview()
            mScanView!!.stopCamera()
        }
    }

    override fun setLayout(): Any {
        return mScanView!!
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

    override fun handleResult(result: Result) {

        // 处理结果
        context?.toast(result.contents)

        // 出栈
        supportDelegate.pop()
    }
}
