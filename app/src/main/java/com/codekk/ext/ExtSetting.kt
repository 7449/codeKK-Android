package com.codekk.ext

import android.view.View
import com.codekk.ui.fragment.BlogListFragment
import com.codekk.ui.fragment.OpListFragment
import com.codekk.ui.fragment.OpaListFragment
import com.xadapter.listener.XMultiCallBack
import io.reactivex.network.RxBus

object ExtSetting {
    const val TYPE_TITLE = 0
    const val TYPE_ITEM = 1
}

data class SettingItem(
        val message: String = "",
        val itemMultiType: Int = ExtSetting.TYPE_TITLE,
        val itemMultiPosition: Int = XMultiCallBack.NO_CLICK_POSITION
) : XMultiCallBack {
    override val itemType: Int = itemMultiType
    override val position: Int = itemMultiPosition
}

fun View.settingItemClick(position: Int, boolean: Boolean) {
    when (position) {
        0 -> {
            context.opTagBoolean(!boolean)
            RxBus.postBus(OpListFragment::class.java.simpleName, OpListFragment::class.java.simpleName)
        }
        1 -> {
            context.opUriWebBoolean(!boolean)
            RxBus.postBus(OpListFragment::class.java.simpleName, OpListFragment::class.java.simpleName)
        }
        2 -> {
            context.opaTagBoolean(!boolean)
            RxBus.postBus(OpaListFragment::class.java.simpleName, OpaListFragment::class.java.simpleName)
        }
        3 -> {
            context.opaUriWebBoolean(!boolean)
            RxBus.postBus(OpaListFragment::class.java.simpleName, OpaListFragment::class.java.simpleName)
        }
        4 -> {
            context.blogTagBoolean(!boolean)
            RxBus.postBus(BlogListFragment::class.java.simpleName, BlogListFragment::class.java.simpleName)
        }
    }
}

fun initItem(): ArrayList<SettingItem> {
    val list = ArrayList<SettingItem>()
    list.add(SettingItem("开源项目"))
    list.add(SettingItem("显示TAG", ExtSetting.TYPE_ITEM, 0))
    list.add(SettingItem("github地址是否设置为超链接", ExtSetting.TYPE_ITEM, 1))
    list.add(SettingItem("源码解析"))
    list.add(SettingItem("显示TAG", ExtSetting.TYPE_ITEM, 2))
    list.add(SettingItem("解析对象连接是否设置为超链接", ExtSetting.TYPE_ITEM, 3))
    list.add(SettingItem("博客文章"))
    list.add(SettingItem("显示TAG", ExtSetting.TYPE_ITEM, 4))
    return list
}
