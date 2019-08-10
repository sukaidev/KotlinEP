package com.sukaidev.user.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.user.R
import com.sukaidev.user.presenter.RegisterPresenter
import com.sukaidev.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by sukai on 2019/08/10.
 *
 */
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {
    override fun onRegisterResult(result: Boolean) {
        toast("注册成功")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mPresenter = RegisterPresenter()
        mPresenter.mView = this

        btn_register.setOnClickListener {
            mPresenter.register("", "", "")
        }

    }
}