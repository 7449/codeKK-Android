package com.codekk.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.codekk.R
import com.codekk.databinding.ActivityMainBinding
import com.codekk.ui.base.ViewBindActivity
import org.jetbrains.anko.startActivity

class MainActivity : ViewBindActivity<ActivityMainBinding>() {

    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.op, R.id.opa, R.id.job, R.id.blog, R.id.recommend), viewBind.drawerLayout) }

    override fun initViewBind(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(viewBind.toolbarRoot.toolbar)
        viewBind.toolbarRoot.toolbar.setNavigationIcon(R.drawable.ic_menu)
        viewBind.toolbarRoot.toolbar.setNavigationOnClickListener { viewBind.drawerLayout.openDrawer(GravityCompat.START) }
        val navController = findNavController(R.id.hostFragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        viewBind.navigationview.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.hostFragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (viewBind.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            viewBind.drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> startActivity<SettingActivity>()
        }
        return super.onOptionsItemSelected(item)
    }
}
