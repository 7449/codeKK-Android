package com.codekk.ui.activity

import android.os.Bundle
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.codekk.R
import com.codekk.ui.base.BaseStatusActivity
import com.codekk.ui.base.EmptyPresenterImpl
import com.codekk.ui.fragment.BlogListFragment
import com.codekk.ui.fragment.OpListFragment
import com.codekk.ui.fragment.OpaListFragment
import com.codekk.utils.UIUtils
import com.common.util.SPUtils
import com.xadapter.adapter.multi.MultiAdapter
import com.xadapter.adapter.multi.SimpleMultiItem
import com.xadapter.holder.XViewHolder
import io.reactivex.network.RxBus
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*

/**
 * by y on 2017/5/18
 */

class SettingActivity : BaseStatusActivity<EmptyPresenterImpl>() {

    override val layoutId: Int = R.layout.activity_setting

    override fun initCreate(savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.setting_title)
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        val list = ArrayList<SimpleMultiItem>()
        list.add(SimpleMultiItem(SettingAdapter.TYPE_TITLE, -1, "开源项目"))
        list.add(SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 0, "显示TAG"))
        list.add(SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 1, "github地址是否设置为超链接"))
        list.add(SimpleMultiItem(SettingAdapter.TYPE_TITLE, -1, "源码解析"))
        list.add(SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 2, "显示TAG"))
        list.add(SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 3, "解析对象连接是否设置为超链接"))
        list.add(SimpleMultiItem(SettingAdapter.TYPE_TITLE, -1, "博客文章"))
        list.add(SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 4, "显示TAG"))

        recyclerView.adapter = SettingAdapter(list)
    }

    override fun initPresenter(): EmptyPresenterImpl? {
        return null
    }

    companion object {

        fun newInstance() {
            UIUtils.startActivity(SettingActivity::class.java)
        }
    }
}

private class SettingAdapter internal constructor(mDatas: List<SimpleMultiItem>) : MultiAdapter<SimpleMultiItem>(mDatas) {

    override fun onBind(holder: XViewHolder, mData: SimpleMultiItem, itemType: Int, position: Int) {
        if (mDatas == null) {
            return
        }
        when (itemType) {
            TYPE_TITLE -> holder.setTextView(R.id.tv_setting_title, mData.message)
            else -> {
                holder.setTextView(R.id.tv_setting_message, mData.message)
                val tagCheckBox = holder.getView<AppCompatCheckBox>(R.id.cb_setting)

                when (position) {
                    0 -> tagCheckBox.isChecked = SPUtils.getBoolean(SPUtils.IS_OP_TAG, true)
                    1 -> tagCheckBox.isChecked = SPUtils.getBoolean(SPUtils.IS_OP_URL_WEB, true)
                    2 -> tagCheckBox.isChecked = SPUtils.getBoolean(SPUtils.IS_OPA_TAG, true)
                    3 -> tagCheckBox.isChecked = SPUtils.getBoolean(SPUtils.IS_OPA_URL_WEB, true)
                    4 -> tagCheckBox.isChecked = SPUtils.getBoolean(SPUtils.IS_BLOG_TAG, true)
                }

                holder.itemView.setOnClickListener {
                    when (position) {
                        0 -> {
                            SPUtils.setBoolean(SPUtils.IS_OP_TAG, !tagCheckBox.isChecked)
                            RxBus.instance.post(OpListFragment::class.java.simpleName)
                        }
                        1 -> {
                            SPUtils.setBoolean(SPUtils.IS_OP_URL_WEB, !tagCheckBox.isChecked)
                            RxBus.instance.post(OpListFragment::class.java.simpleName)
                        }
                        2 -> {
                            SPUtils.setBoolean(SPUtils.IS_OPA_TAG, !tagCheckBox.isChecked)
                            RxBus.instance.post(OpaListFragment::class.java.simpleName)
                        }
                        3 -> {
                            SPUtils.setBoolean(SPUtils.IS_OPA_URL_WEB, !tagCheckBox.isChecked)
                            RxBus.instance.post(OpaListFragment::class.java.simpleName)
                        }
                        4 -> {
                            SPUtils.setBoolean(SPUtils.IS_BLOG_TAG, !tagCheckBox.isChecked)
                            RxBus.instance.post(BlogListFragment::class.java.simpleName)
                        }
                    }
                    tagCheckBox.isChecked = !tagCheckBox.isChecked
                }
            }
        }
    }

    override fun getLayoutId(viewType: Int): Int {
        return when (viewType) {
            TYPE_TITLE -> R.layout.item_setting_title
            else -> R.layout.item_setting
        }
    }

    companion object {
        internal const val TYPE_TITLE = 0
    }
}
