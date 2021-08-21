package com.example.donutdash.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.donutdash.R

/**
 * Activity that hosts the donut order flow.
 */

class MainActivity : AppCompatActivity() {

    // declare as lateinit in activity scope so it can be accessed in onSupportNavigateUp()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Retrieve navController from navHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        /* By calling this method, the title in the action bar will automatically

        be updated when the destination changes (assuming there is a valid label).

        The AppBarConfiguration you provide controls how the Navigation button is displayed.

        You are responsible for calling NavController.navigateUp to handle the Navigation button.

        Typically this is done in AppCompatActivity.onSupportNavigateUp. (Implemented Below)
         */
        setupActionBarWithNavController(navController)
    }

    /**
     * Handle navigation when Up is selected from the action bar.
     */
    override fun onSupportNavigateUp(): Boolean {
        return  navController.navigateUp() || super.onSupportNavigateUp()
    }
}