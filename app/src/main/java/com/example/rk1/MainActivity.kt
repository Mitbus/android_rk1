package com.example.rk1

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentMain = supportFragmentManager.findFragmentById(R.id.fragment_main) as NavHostFragment
        val navController = fragmentMain.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
        } else if (itemId == R.id.changeLang) {
            changeOSLanguage("en")
        }
        return super.onOptionsItemSelected(item)
    }


    fun changeOSLanguage(language: String) {
        // only for testing
        resources.configuration.setLocale(Locale(language))
        resources.updateConfiguration(resources.configuration, null)
        recreate()
    }

}
