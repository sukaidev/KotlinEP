package com.sukaidev.kotlinmall.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.sukaidev.common.ext.loadUrl
import com.sukaidev.kotlinmall.R
import kotlinx.android.synthetic.main.layout_topic_item.view.*

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
class TopicAdapter(private val context: Context, private val list: List<String>) : PagerAdapter() {

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val rooView = LayoutInflater.from(this.context).inflate(R.layout.layout_topic_item, null)
        rooView.mTopicIv.loadUrl(list[position])
        container.addView(rooView)
        return rooView
    }
}