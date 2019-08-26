package com.sukaidev.core.ext

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.core.R
import com.sukaidev.core.rx.BaseFunc
import com.sukaidev.core.rx.BaseFuncBoolean
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.core.widget.DefaultTextWatcher
import com.trello.rxlifecycle.LifecycleProvider
import org.jetbrains.anko.find
import ren.qinc.numberbutton.NumberButton
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by sukaidev on 2019/08/26.
 * 扩展委托.
 */
fun <T> notNullSingleValue():
        ReadWriteProperty<Any?, T> = NotNullSingleValueVar()

/**
 *  SharedPreferences 委托  自动存取
 */
fun <T : Any> preference(context: Context, name: String, default: T):
        ReadWriteProperty<Any?, T> = Preference(context, name, default)


private class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {
    private var value: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("not initialized")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("already initialized")
    }
}


private class Preference<T>(val context: Context, val name: String, val default: T)
    : ReadWriteProperty<Any?, T> {

    val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can not be saved into Preferences")
        }
        res as T
    }

    private fun <U> putPreference(name: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }
}

/**
 * Observable执行
 */
fun <T> Observable<T>.execute(
    subscriber: BaseSubscriber<T>,
    lifecycleProvider: LifecycleProvider<*>
) {
    this.observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .subscribe(subscriber)
}

/**
 * 扩展数据转换
 */
fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}

/**
 * Boolean类型数据转换
 */
fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFuncBoolean())
}

/**
 * 扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

/**
 * 扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit) {
    this.setOnClickListener {
        method()
    }
}

/**
 * 通过EditText状态修改Button状态
 */
fun Button.enable(et: AppCompatEditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

/**
 * 扩展视图可见性
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * 获取NumberButton中间的EditText
 */
fun NumberButton.getEditText(): EditText {
    return find(R.id.text_count)
}