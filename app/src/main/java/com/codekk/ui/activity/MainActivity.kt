package com.codekk.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.codekk.R
import com.codekk.mvp.presenter.MainPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.base.BaseModel
import com.codekk.ui.base.BaseStatusActivity
import com.codekk.utils.UIUtils
import com.google.android.material.navigation.NavigationView
import io.reactivex.network.RxNetWork
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : BaseStatusActivity<MainPresenterImpl>(), ViewManager.MainView, NavigationView.OnNavigationItemSelectedListener {

    private var exitTime: Long = 0

    override val layoutId: Int = R.layout.activity_main

    override val mainActivity: AppCompatActivity = this

    override fun initCreate(savedInstanceState: Bundle?) {
        main_toolbar.title = UIUtils.getString(R.string.op_title)
        setSupportActionBar(main_toolbar)
        main_toolbar.setNavigationIcon(R.drawable.ic_menu)
        main_toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(Gravity.START) }
        navigationview.setNavigationItemSelectedListener(this)
        mPresenter?.switchId(MainPresenterImpl.FIRST_FRAGMENT)
    }

    override fun initPresenter(): MainPresenterImpl? {
        return MainPresenterImpl(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.notes || itemId == R.id.common) {
            UIUtils.toast(UIUtils.getString(R.string.navigation_tips))
            return false
        }
        main_toolbar.title = item.title
        mPresenter?.switchId(itemId)
        drawerLayout.closeDrawers()
        return true
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
        } else {
            mPresenter?.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mPresenter?.switchId(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun switchSetting() {
        SettingActivity.newInstance()
    }

    override fun onBack() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            UIUtils.snackBar(activity_status_layout, R.string.exit_app)
            exitTime = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
    }

    override fun selectMenuFirst() {
        val item = navigationview.menu.findItem(R.id.op)
        item.isChecked = true
        main_toolbar.title = item.title
    }

    override fun switchPact() {
        UIUtils.startActivity(PactActivity::class.java)
    }

    override fun onDestroy() {
        mPresenter?.onMainDestroy()
        super.onDestroy()
        RxNetWork.instance.cancelAll()
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun netWorkSuccess(entity: BaseModel<*>) {

    }

    override fun netWorkError(throwable: Throwable) {

    }
}
