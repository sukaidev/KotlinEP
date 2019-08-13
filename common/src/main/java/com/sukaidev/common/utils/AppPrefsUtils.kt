package com.sukaidev.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.sukaidev.common.common.BaseApplication
import com.sukaidev.common.common.BaseConstant
import org.json.JSONObject
import android.preference.PreferenceManager


/**
 * Created by sukaidev on 2019/08/11.
 * SharedPreferences工具类.
 */
object AppPrefsUtils {

    /**
     * 提示:
     * Activity.getPreferences(int mode)生成 Activity名.xml 用于Activity内部存储
     * PreferenceManager.getDefaultSharedPreferences(Context)生成 包名_preferences.xml
     * Context.getSharedPreferences(String name,int mode)生成name.xml
     */
    private val PREFERENCES = BaseApplication.context.getSharedPreferences(BaseConstant.TABLE_PREFS, Context.MODE_PRIVATE)

    fun putBoolean(key: String, value: Boolean) {
        PREFERENCES
                .edit()
                .putBoolean(key, value)
                .apply()
    }

    fun getBoolean(key: String): Boolean {
        return PREFERENCES.getBoolean(key, false)
    }

    fun putString(key: String, value: String) {
        PREFERENCES
                .edit()
                .putString(key, value)
                .apply()
    }

    fun getString(key: String): String? {
        return PREFERENCES.getString(key, "")
    }

    fun putInt(key: String, value: Int) {
        PREFERENCES
                .edit()
                .putInt(key, value)
                .apply()
    }

    fun getInt(key: String): Int {
        return PREFERENCES.getInt(key, 0)
    }

    fun putLong(key: String, value: Long) {
        PREFERENCES
                .edit()
                .putLong(key, value)
                .apply()
    }

    fun getLong(key: String): Long {
        return PREFERENCES.getLong(key, 0)
    }

    fun putFloat(key: String, value: Float) {
        PREFERENCES
                .edit()
                .putFloat(key, value)
                .apply()
    }

    fun getFloat(key: String): Float {
        return PREFERENCES.getFloat(key, 0f)
    }

    fun putStringSet(key: String, set: Set<String>) {
        val localSet = getStringSet(key)?.toMutableSet()
        localSet?.addAll(set)
        PREFERENCES
                .edit()
                .putStringSet(key, localSet)
                .apply()
    }

    /**
     * 默认空Set
     */
    fun getStringSet(key: String): Set<String>? {
        val set = setOf<String>()
        return PREFERENCES.getStringSet(key, set)
    }

    fun remove(key: String) {
        PREFERENCES
                .edit()
                .remove(key)
                .apply()
    }

}