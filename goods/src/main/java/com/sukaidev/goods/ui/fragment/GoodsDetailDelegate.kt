package com.sukaidev.goods.ui.fragment

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.presenter.GoodsDetailPresenter
import com.sukaidev.goods.presenter.view.GoodsDetailView
import q.rorbin.badgeview.QBadgeView

/**
 * Created by sukaidev on 2019/08/16.
 * 商品详情页面.
 */
class GoodsDetailDelegate : BaseMvpDelegate<GoodsDetailPresenter>(),GoodsDetailView {

    private lateinit var mCartBadge: QBadgeView

    override fun injectComponent() {

    }

    override fun setLayout(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetGoodsDetail(result: Goods) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddCartResult(result: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}