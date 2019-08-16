package com.sukaidev.goods.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ext.startLoading
import com.sukaidev.common.ui.fragment.BaseMvpFragment
import com.sukaidev.common.utils.MoneyConverter
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.CartGoods
import com.sukaidev.goods.event.CartAllCheckedEvent
import com.sukaidev.goods.event.UpdateTotalPriceEvent
import com.sukaidev.goods.injection.component.DaggerCartComponent
import com.sukaidev.goods.injection.module.CartModule
import com.sukaidev.goods.presenter.CartPresenter
import com.sukaidev.goods.presenter.view.ICartView
import com.sukaidev.goods.ui.adapter.CartGoodsAdapter
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.mMultiStateView

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
class CartFragment : BaseMvpFragment<CartPresenter>(), ICartView {

    private lateinit var mCartGoodsAdapter: CartGoodsAdapter

    private var mTotalPrice: Long = 0

    override fun injectComponent() {
        DaggerCartComponent.builder()
            .activityComponent(activityComponent)
            .cartModule(CartModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.fragment_cart
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
        loadData()
        initObserve()
    }

    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(context)
        mCartGoodsAdapter = CartGoodsAdapter(context!!)
        mCartGoodsRv.adapter = mCartGoodsAdapter

        mAllCheckedCb.onClick {
            for (item in mCartGoodsAdapter.dataList) {
                item.isSelected = mAllCheckedCb.isChecked
            }
            mCartGoodsAdapter.notifyDataSetChanged()
            updateTotalPrice()
        }
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getCartList()
    }

    override fun onGetCartList(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0) {
            mCartGoodsAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.ViewState.CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.ViewState.EMPTY
        }
    }

    private fun initObserve() {
        // 订阅全选事件
        Bus.observe<CartAllCheckedEvent>()
            .subscribe {
                run {
                    mAllCheckedCb.isChecked = it.isAllChecked
                    updateTotalPrice()
                }
            }.registerInBus(this)
        // 订阅总价变化事件
        Bus.observe<UpdateTotalPriceEvent>()
            .subscribe {
                updateTotalPrice()
            }.registerInBus(this)
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalPrice() {
        mTotalPrice = mCartGoodsAdapter.dataList
            .filter { it.isSelected }
            .map { it.goodsCount * it.goodsPrice }.sum()
        mTotalPriceTv.text = "合计${MoneyConverter.changeF2YWithUnit(mTotalPrice)}"
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}