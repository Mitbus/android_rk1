package com.example.rk1

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentMain = supportFragmentManager.findFragmentById(R.id.fragment_main) as NavHostFragment
        val navController = fragmentMain.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
}
