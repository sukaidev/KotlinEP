package com.sukaidev.goods.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.event.*
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.setVisible
import com.sukaidev.core.ext.toast
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.core.utils.AppPrefsUtils
import com.sukaidev.core.utils.MoneyConverter
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.CartGoods
import com.sukaidev.goods.event.*
import com.sukaidev.goods.injection.component.DaggerCartComponent
import com.sukaidev.goods.injection.module.CartModule
import com.sukaidev.goods.presenter.ShopCartPresenter
import com.sukaidev.goods.presenter.view.ShopCartView
import kotlinx.android.synthetic.main.delegate_shop_cart.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/19.
 * 购物车页面.
 */
class ShopCartDelegate : BaseMvpDelegate<ShopCartPresenter>(), ShopCartView {

    private lateinit var mAdapter: CartGoodsAdapter

    private var mTotalPrice: Long = 0

    // 是否从GoodsDetail页面进入
    private var isStartedFromDetailDelegate: Boolean = false

    override fun injectComponent() {
        DaggerCartComponent
            .builder()
            .activityComponent(mActivityComponent)
            .cartModule(CartModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_shop_cart
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        arguments?.let {
            isStartedFromDetailDelegate = it.getBoolean(GoodsConstant.ARG_STARTED_FROM_DETAIL)
        }
        initView()
        if (!isStartedFromDetailDelegate) {
            initObserve()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        loadData()
    }

    private fun initView() {

        mCartGoodsRv.layoutManager = LinearLayoutManager(context)
        mAdapter = CartGoodsAdapter(null)
        mCartGoodsRv.adapter = mAdapter

        if (isStartedFromDetailDelegate) {
            mHeaderBar.getLeftIv().visibility = View.VISIBLE
            mHeaderBar.getLeftIv().onClick {
                supportDelegate.pop()
            }
        }

        mHeaderBar.getRightTv().onClick {
            refreshEditStatus()
        }

        // 全选按钮事件
        mAllCheckedCb.onClick {
            if (mAdapter.data.isNotEmpty()) {
                mAdapter.data.forEach {
                    it.isSelected = mAllCheckedCb.isChecked
                }
                mAdapter.notifyDataSetChanged()
                updateTotalPrice()
            }
        }

        // 删除按钮事件
        mDeleteBtn.onClick {
            val cartIdList: MutableList<Int> = arrayListOf()
            mAdapter.data.filter { it.isSelected }
                .mapTo(cartIdList) { it.id }
            if (cartIdList.size == 0) {
                context?.toast("请选择需要删除的数据")
            } else {
                mPresenter.deleteCartList(cartIdList)
            }
        }

        // 结算按钮事件
        mSettleAccountsBtn.onClick {
            val cartGoodsList: MutableList<CartGoods> = arrayListOf()
            mAdapter.data.filter { it.isSelected }
                .mapTo(cartGoodsList) { it }
            if (cartGoodsList.size == 0) {
                context?.toast("请选择需要提交的数据")
            } else {
                mPresenter.submitCart(cartGoodsList, mTotalPrice)
            }
        }
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        mPresenter.getCartList()
    }

    /**
     * 注册Event监听
     */
    private fun initObserve() {
        Bus
            .observe<CartAllCheckedEvent>()
            .subscribe { t: CartAllCheckedEvent ->
                run {
                    mAllCheckedCb.isChecked = t.isAllChecked
                    updateTotalPrice()
                }
            }
            .registerInBus(this)

        Bus
            .observe<UpdateTotalPriceEvent>()
            .subscribe {
                updateTotalPrice()
            }
            .registerInBus(this)

        Bus
            .observe<UpdateDetailCartSizeEvent>()
            .subscribe {
                loadData()
            }
            .registerInBus(this)

        Bus
            .observe<LoginSuccessEvent>()
            .subscribe {
                loadData()
            }
            .registerInBus(this)

        Bus
            .observe<LogoutEvent>()
            .subscribe {
                loadData()
            }
            .registerInBus(this)

        Bus
            .observe<ToOrderManagerDelegateEvent>()
            .subscribe {
                loadData()
            }

        Bus
            .observe<PaySuccessEvent>()
            .subscribe {
                loadData()
            }
            .registerInBus(this)
    }

    /**
     * 刷新编辑状态
     */
    private fun refreshEditStatus() {
        val isEditStatus = getString(R.string.common_edit) == mHeaderBar.getRightTv().text
        mTotalPriceTitleTv.setVisible(isEditStatus.not())
        mTotalPriceTv.setVisible(isEditStatus.not())
        mSettleAccountsBtn.setVisible(isEditStatus.not())
        mDeleteBtn.setVisible(isEditStatus)
        mHeaderBar.getRightTv().text = if (isEditStatus) getString(R.string.common_complete) else getString(R.string.common_edit)
    }

    /**
     * 更新总价
     */
    private fun updateTotalPrice() {
        mTotalPriceTv.setVisible(true)
        mTotalPrice = mAdapter.data
            .filter { it.isSelected }
            .map { it.goodsCount * it.goodsPrice }
            .sum()
        mTotalPriceTv.text = MoneyConverter.changeF2YWithUnit(mTotalPrice)
    }

    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (!result.isNullOrEmpty()) {
/*            if (mCartGoodsRv.visibility == View.GONE) {
                mViewStub.visibility = View.GONE
                mCartGoodsRv.visibility = View.VISIBLE
            }*/
            mAdapter.setNewData(result)
            mHeaderBar.getRightTv().setVisible(true)
            mAllCheckedCb.isChecked = false
        } else {
            // 清空当前Adapter中的数据
            mAdapter.data.clear()
            mHeaderBar.getRightTv().setVisible(false)
            mAllCheckedCb.isChecked = false
            mTotalPriceTv.setVisible(false)
/*            setViewStateEmpty(
                this,
                mCartGoodsRv,
                R.id.mViewStub,
                R.id.tv_stub_content_empty,
                "购物车空空如也~快去购物吧"
            )*/
        }

        // 本地存储并发送事件刷新UI
        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, result?.size ?: 0)
        Bus.send(UpdateBottomCartSizeEvent())
        // 更新总价
        updateTotalPrice()
    }

    override fun onDeleteCartListResult(result: Boolean) {
        toast("删除成功")
        refreshEditStatus()
        loadData()
    }

    override fun onSubmitCartResult(result: Int) {
/*        ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
            .withInt(ProviderConstant.KEY_ORDER_ID,result)
            .navigation()*/
        Bus.send(SubmitCartEvent(result))
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0

    override fun onBackPressedSupport(): Boolean {
        return if (!isStartedFromDetailDelegate) {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                _mActivity.finish()
            } else {
                TOUCH_TIME = System.currentTimeMillis()
                toast("再次点击退出")
            }
            true
        } else {
            super.onBackPressedSupport()
        }
    }
}