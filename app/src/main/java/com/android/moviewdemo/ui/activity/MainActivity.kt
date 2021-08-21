package com.android.moviewdemo.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.android.moviewdemo.R
import com.android.moviewdemo.base.BaseActivity
import com.android.moviewdemo.databinding.ActivityMainBinding
import com.android.moviewdemo.ui.viewModel.main.MainVM

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {

    lateinit var navController: NavController

    override val bindingActivity: ActivityBinding
        get() = ActivityBinding(R.layout.activity_main, MainVM::class.java)

    override val toolbar: Toolbar?
        get() = null

    override fun setAccessibilityView(binding: ActivityMainBinding) {

    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        setupViews()
    }

    override fun subscribeToEvents(vm: MainVM) {

    }

    private fun setupViews() {
        // Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(this@MainActivity, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_day_night_mode -> {
                // Get new mode.
                val mode =
                    if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                        Configuration.UI_MODE_NIGHT_NO
                    ) {
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    }
                // Change UI Mode
                AppCompatDelegate.setDefaultNightMode(mode)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}