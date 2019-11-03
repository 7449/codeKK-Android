package com.codekk.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.codekk.R
import com.codekk.ext.*
import com.xadapter.*
import com.xadapter.adapter.XMultiAdapter
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * by y on 2017/5/18
 */
class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        toolbar.setTitle(R.string.setting_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

        recyclerView
                .attachMultiAdapter(XMultiAdapter(initItem()))
                .setItemLayoutId { viewType ->
                    when (viewType) {
                        ExtSetting.TYPE_TITLE -> R.layout.item_setting_title
                        else -> R.layout.item_setting
                    }
                }
                .setMultiBind { holder, entity, itemViewType, _ ->
                    when (itemViewType) {
                        ExtSetting.TYPE_TITLE -> holder.setText(R.id.tv_setting_title, entity.message)
                        ExtSetting.TYPE_ITEM -> {
                            holder.setText(R.id.tv_setting_message, entity.message)
                            val tagCheckBox = holder.findById<AppCompatCheckBox>(R.id.cb_setting)
                            when (entity.itemMultiPosition) {
                                0 -> tagCheckBox.isChecked = holder.getContext().opTagBoolean()
                                1 -> tagCheckBox.isChecked = holder.getContext().opUriWebBoolean()
                                2 -> tagCheckBox.isChecked = holder.getContext().opaTagBoolean()
                                3 -> tagCheckBox.isChecked = holder.getContext().opaUriWebBoolean()
                                4 -> tagCheckBox.isChecked = holder.getContext().blogTagBoolean()
                            }
                            holder.itemView.setOnClickListener {
                                it.settingItemClick(entity.itemMultiPosition, !tagCheckBox.isChecked)
                                tagCheckBox.isChecked = !tagCheckBox.isChecked
                            }
                        }
                    }
                }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}