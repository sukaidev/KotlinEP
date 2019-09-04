package com.sukaidev.goods.ui.goods

import android.os.Bundle
import android.view.View
import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.ext.loadUrl
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.goods.R
import kotlinx.android.synthetic.main.delegate_goods_image.*

/**
 * Created by sukaidev on 2019/08/16.
 * 商品详情图片.
 */
class GoodsImageDelegate : ProxyDelegate() {

    companion object {
        fun create(images: Array<String>): GoodsImageDelegate {
            val args = Bundle()
            args.putStringArray(GoodsConstant.ARG_GOODS_DETAIL_IMAGES, images)
            val delegate = GoodsImageDelegate()
            delegate.arguments = args
            return delegate
        }
    }

    override fun setLayout(): Any {
        return R.layout.delegate_goods_image
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        arguments?.let {
            mGoodsDetailOneIv.loadUrl(it.getStringArray(GoodsConstant.ARG_GOODS_DETAIL_IMAGES)!![0])
            mGoodsDetailTwoIv.loadUrl(it.getStringArray(GoodsConstant.ARG_GOODS_DETAIL_IMAGES)!![1])
        }
    }
}