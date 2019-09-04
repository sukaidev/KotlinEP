package com.sukaidev.pay.ui.activity

/**
 * Created by sukaidev on 2019/08/19.
 * 收银台界面.
 */
//@Route(path = RouterPath.Pay.PATH_PAY)
/*
class CashRegisterActivity : BaseMvpActivity<PayPresenter>(), PayView, View.OnClickListener {

    // 订单号
    var mOrderId: Int = 0

    // 订单总价格
    var mTotalPrice: Long = 0

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
        return R.layout.activity_cash_register
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        initView()
        initData()
    }

    */
/**
     * 初始化视图
     *//*

    private fun initView() {
        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        mTotalPrice = intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE, -1)

        mTotalPriceTv.text = MoneyConverter.changeF2YWithUnit(mTotalPrice)
    }

    */
/**
     * 初始化数据
     *//*

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

    */
/**
     * 选择支付类型，UI变化
     *//*

    private fun updatePayType(isAliPay: Boolean, isWeixinPay: Boolean, isBankCardPay: Boolean) {
        mAlipayTypeTv.isSelected = isAliPay
        mWeixinTypeTv.isSelected = isWeixinPay
        mBankCardTypeTv.isSelected = isBankCardPay
    }

    */
/**
     * 获取支付签名回调
     *//*

    override fun onGetPaySignResult(result: String) {
        doAsync {
            val resultMap: Map<String, String> =
                PayTask(this@CashRegisterActivity).payV2(result, true)
            uiThread {
                if (resultMap["resultStatus"].equals("9000")) {
                    mPresenter.payOrder(mOrderId)
                } else {
                    toast("支付失败${resultMap["memo"]}")
                }
            }
        }
    }

    */
/**
     * 支付订单回调
     *//*

    override fun onPayOrderResult(result: Boolean) {
        toast("支付成功")
        finish()
    }
}*/
