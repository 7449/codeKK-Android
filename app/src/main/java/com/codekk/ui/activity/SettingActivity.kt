package com.codekk.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.codekk.*
import com.codekk.R
import com.codekk.ui.fragment.BlogListFragment
import com.codekk.ui.fragment.OpListFragment
import com.codekk.ui.fragment.OpaListFragment
import com.xadapter.*
import com.xadapter.adapter.XMultiAdapter
import com.xadapter.listener.XMultiCallBack
import com.xadapter.simple.SimpleXMultiItem
import io.reactivex.network.RxBus
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*

/**
 * by y on 2017/5/18
 */

class SettingActivity : AppCompatActivity() {

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_ITEM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        toolbar.setTitle(R.string.setting_title)
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        val list = ArrayList<SimpleXMultiItem>()
        list.add(SimpleXMultiItem("开源项目", itemMultiType = TYPE_TITLE, itemMultiPosition = XMultiCallBack.NO_CLICK_POSITION))
        list.add(SimpleXMultiItem("显示TAG", itemMultiType = TYPE_ITEM, itemMultiPosition = 0))
        list.add(SimpleXMultiItem("github地址是否设置为超链接", itemMultiType = TYPE_ITEM, itemMultiPosition = 1))
        list.add(SimpleXMultiItem("源码解析", itemMultiType = TYPE_TITLE, itemMultiPosition = XMultiCallBack.NO_CLICK_POSITION))
        list.add(SimpleXMultiItem("显示TAG", itemMultiType = TYPE_ITEM, itemMultiPosition = 2))
        list.add(SimpleXMultiItem("解析对象连接是否设置为超链接", itemMultiType = TYPE_ITEM, itemMultiPosition = 3))
        list.add(SimpleXMultiItem("博客文章", itemMultiType = TYPE_TITLE, itemMultiPosition = XMultiCallBack.NO_CLICK_POSITION))
        list.add(SimpleXMultiItem("显示TAG", itemMultiType = TYPE_ITEM, itemMultiPosition = 4))

        recyclerView
                .attachMultiAdapter(XMultiAdapter(list))
                .setItemLayoutId { viewType ->
                    when (viewType) {
                        TYPE_TITLE -> R.layout.item_setting_title
                        else -> R.layout.item_setting
                    }
                }
                .setMultiBind { holder, entity, itemViewType, position ->
                    when (itemViewType) {
                        TYPE_TITLE -> holder.setText(R.id.tv_setting_title, entity.message)
                        TYPE_ITEM -> {
                            holder.setText(R.id.tv_setting_message, entity.message)
                            val tagCheckBox = holder.findById<AppCompatCheckBox>(R.id.cb_setting)

                            when (position) {
                                0 -> tagCheckBox.isChecked = App.instance.opTagBoolean()
                                1 -> tagCheckBox.isChecked = App.instance.opUriWebBoolean()
                                2 -> tagCheckBox.isChecked = App.instance.opaTagBoolean()
                                3 -> tagCheckBox.isChecked = App.instance.opaUriWebBoolean()
                                4 -> tagCheckBox.isChecked = App.instance.blogTagBoolean()
                            }

                            holder.itemView.setOnClickListener {
                                when (position) {
                                    0 -> {
                                        App.instance.opTagBoolean(!tagCheckBox.isChecked)
                                        RxBus.postBus(OpListFragment::class.java.simpleName, OpListFragment::class.java.simpleName)
                                    }
                                    1 -> {
                                        App.instance.opUriWebBoolean(!tagCheckBox.isChecked)
                                        RxBus.postBus(OpListFragment::class.java.simpleName, OpListFragment::class.java.simpleName)
                                    }
                                    2 -> {
                                        App.instance.opaTagBoolean(!tagCheckBox.isChecked)
                                        RxBus.postBus(OpaListFragment::class.java.simpleName, OpaListFragment::class.java.simpleName)
                                    }
                                    3 -> {
                                        App.instance.opaUriWebBoolean(!tagCheckBox.isChecked)
                                        RxBus.postBus(OpaListFragment::class.java.simpleName, OpaListFragment::class.java.simpleName)
                                    }
                                    4 -> {
                                        App.instance.blogTagBoolean(!tagCheckBox.isChecked)
                                        RxBus.postBus(BlogListFragment::class.java.simpleName, BlogListFragment::class.java.simpleName)
                                    }
                                }
                                tagCheckBox.isChecked = !tagCheckBox.isChecked
                            }
                        }
                    }
                }
    }
}