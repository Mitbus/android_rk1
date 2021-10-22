package com.example.rk1

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instance = this
        val fragmentMain = supportFragmentManager.findFragmentById(R.id.fragment_main) as NavHostFragment
        val navController = fragmentMain.navController
        NavigationUI.setupActionBarWithNavController(this, navController)

        val savedLocale = Locale(PreferenceManager.getDefaultSharedPreferences(this).getString(resources.getString(R.string.pref_language), "en") ?: "en")
        if (resources.configuration.locale != savedLocale) {
            resources.configuration.setLocale(savedLocale)
            resources.updateConfiguration(resources.configuration, null)
            recreate()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var instance: MainActivity? = null
    }
}
