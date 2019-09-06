package com.sukaidev.pay.ui.fragment

import android.os.Bundle
import android.view.View
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.eightbitlab.rxbus.Bus
import com.sukaidev.core.event.PaySuccessEvent
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.core.utils.MoneyConverter
import com.sukaidev.pay.R
import com.sukaidev.pay.injection.component.DaggerPayComponent
import com.sukaidev.pay.injection.module.PayModule
import com.sukaidev.pay.presenter.PayPresenter
import com.sukaidev.pay.presenter.view.PayView
import com.sukaidev.core.common.ProviderConstant
import kotlinx.android.synthetic.main.delegate_cash_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * Created by sukaidev on 2019/08/19.
 * 收银台界面.
 */
class CashRegisterDelegate : BaseMvpDelegate<PayPresenter>(), PayView, View.OnClickListener {

    // 订单号
    private var mOrderId: Int = 0

    // 订单总价格
    private var mTotalPrice: Long = 0

    override fun injectComponent() {
        DaggerPayComponent
            .builder()
            .activityComponent(mActivityComponent)
            .payModule(PayModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Int {
        return R.layout.delegate_cash_register
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        initView()
        initData()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        arguments?.let {
            mOrderId = it.getInt(ProviderConstant.KEY_ORDER_ID, -1)
            mTotalPrice = it.getLong(ProviderConstant.KEY_ORDER_PRICE, -1)
        }
/*        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        mTotalPrice = intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE, -1)*/

        mTotalPriceTv.text = MoneyConverter.changeF2YWithUnit(mTotalPrice)
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        mAlipayTypeTv.isSelected = true
        mAlipayTypeTv.onClick(this)
        mWeixinTypeTv.onClick(this)
        mBankCardTypeTv.onClick(this)
        mPayBtn.onClick(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            mAlipayTypeTv -> {
                updatePayType(isAliPay = true, isWeixinPay = false, isBankCardPay = false)
            }
            mWeixinTypeTv -> {
                updatePayType(isAliPay = false, isWeixinPay = true, isBankCardPay = false)
            }
            mBankCardTypeTv -> {
                updatePayType(isAliPay = false, isWeixinPay = false, isBankCardPay = true)
            }
            mPayBtn -> {
                mPresenter.getPaySign(mOrderId, mTotalPrice)
            }
        }
    }

    /**
     * 选择支付类型，UI变化
     */
    private fun updatePayType(isAliPay: Boolean, isWeixinPay: Boolean, isBankCardPay: Boolean) {
        mAlipayTypeTv.isSelected = isAliPay
        mWeixinTypeTv.isSelected = isWeixinPay
        mBankCardTypeTv.isSelected = isBankCardPay
    }

    /**
     * 获取支付签名回调
     */
    override fun onGetPaySignResult(result: String) {
        doAsync {
            val resultMap: Map<String, String> =
                PayTask(activity).payV2(result, true)
            uiThread {
                if (resultMap["resultStatus"].equals("9000")) {
                    mPresenter.payOrder(mOrderId)
                } else {
                    context?.toast("支付失败${resultMap["memo"]}")
                }
            }
        }
    }

    /**
     * 支付订单回调
     */
    override fun onPayOrderResult(result: Boolean) {
        context?.toast("支付成功")
        Bus.send(PaySuccessEvent())
        supportDelegate.pop()
    }
}
