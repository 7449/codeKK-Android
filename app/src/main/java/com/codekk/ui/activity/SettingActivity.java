package com.codekk.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.codekk.R;
import com.codekk.ui.base.BasePresenterImpl;
import com.codekk.ui.base.BaseStatusActivity;
import com.codekk.ui.fragment.BlogListFragment;
import com.codekk.ui.fragment.OpListFragment;
import com.codekk.ui.fragment.OpaListFragment;
import com.codekk.utils.UIUtils;
import com.common.util.SPUtils;
import com.common.widget.LoadMoreRecyclerView;
import com.xadapter.adapter.multi.MultiAdapter;
import com.xadapter.adapter.multi.SimpleMultiItem;
import com.xadapter.holder.XViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.network.bus.RxBus;

/**
 * by y on 2017/5/18
 */

public class SettingActivity extends BaseStatusActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;

    public static void newInstance() {
        UIUtils.startActivity(SettingActivity.class);
    }

    @Override
    protected void initCreate(@NonNull Bundle savedInstanceState) {
        mToolbar.setTitle(R.string.setting_title);
        setSupportActionBar(mToolbar);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        List<SimpleMultiItem> list = new ArrayList<>();
        list.add(new SimpleMultiItem(SettingAdapter.TYPE_TITLE, -1, "开源项目"));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 0, "显示TAG"));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 1, "github地址是否设置为超链接"));
        list.add(new SimpleMultiItem(SettingAdapter.TYPE_TITLE, -1, "源码解析"));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 2, "显示TAG"));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 3, "解析对象连接是否设置为超链接"));
        list.add(new SimpleMultiItem(SettingAdapter.TYPE_TITLE, -1, "博客文章"));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 4, "显示TAG"));

        mRecyclerView.setAdapter(new SettingAdapter(list));
    }

    @Override
    protected BasePresenterImpl initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }


    private static class SettingAdapter extends MultiAdapter<SimpleMultiItem> {
        static final int TYPE_TITLE = 0;

        SettingAdapter(@NonNull List<SimpleMultiItem> mDatas) {
            super(mDatas);
        }

        @Override
        protected void onBind(XViewHolder holder, SimpleMultiItem mData, int itemType, int position) {
            if (mDatas == null) {
                return;
            }
            switch (itemType) {
                case TYPE_TITLE:
                    holder.setTextView(R.id.tv_setting_title, mData.message);
                    break;
                default:
                    holder.setTextView(R.id.tv_setting_message, mData.message);
                    AppCompatCheckBox tagCheckBox = holder.getView(R.id.cb_setting);

                    if (position == 0) {
                        tagCheckBox.setChecked(SPUtils.getBoolean(SPUtils.IS_OP_TAG, true));
                    } else if (position == 1) {
                        tagCheckBox.setChecked(SPUtils.getBoolean(SPUtils.IS_OP_URL_WEB, true));
                    } else if (position == 2) {
                        tagCheckBox.setChecked(SPUtils.getBoolean(SPUtils.IS_OPA_TAG, true));
                    } else if (position == 3) {
                        tagCheckBox.setChecked(SPUtils.getBoolean(SPUtils.IS_OPA_URL_WEB, true));
                    } else if (position == 4) {
                        tagCheckBox.setChecked(SPUtils.getBoolean(SPUtils.IS_BLOG_TAG, true));
                    }

                    holder.itemView.setOnClickListener(v -> {
                        switch (position) {
                            case 0:
                                SPUtils.setBoolean(SPUtils.IS_OP_TAG, !tagCheckBox.isChecked());
                                RxBus.getInstance().post(OpListFragment.class.getSimpleName());
                                break;
                            case 1:
                                SPUtils.setBoolean(SPUtils.IS_OP_URL_WEB, !tagCheckBox.isChecked());
                                RxBus.getInstance().post(OpListFragment.class.getSimpleName());
                                break;
                            case 2:
                                SPUtils.setBoolean(SPUtils.IS_OPA_TAG, !tagCheckBox.isChecked());
                                RxBus.getInstance().post(OpaListFragment.class.getSimpleName());
                                break;
                            case 3:
                                SPUtils.setBoolean(SPUtils.IS_OPA_URL_WEB, !tagCheckBox.isChecked());
                                RxBus.getInstance().post(OpaListFragment.class.getSimpleName());
                                break;
                            case 4:
                                SPUtils.setBoolean(SPUtils.IS_BLOG_TAG, !tagCheckBox.isChecked());
                                RxBus.getInstance().post(BlogListFragment.class.getSimpleName());
                                break;
                        }
                        tagCheckBox.setChecked(!tagCheckBox.isChecked());
                    });
                    break;
            }
        }

        @Override
        protected int getLayoutId(int viewType) {
            switch (viewType) {
                case TYPE_TITLE:
                    return R.layout.item_setting_title;
                default:
                    return R.layout.item_setting;
            }
        }
    }
}
