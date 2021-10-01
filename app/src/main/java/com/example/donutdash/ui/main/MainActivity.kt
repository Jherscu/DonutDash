package com.example.donutdash.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.donutdash.R
import com.example.donutdash.adapter.ToppingsAdapter
import com.example.donutdash.ui.dialog.LargeOrderDialogFragment
import com.google.android.material.snackbar.Snackbar

/**
 * Activity that hosts the donut order flow.
 */

class MainActivity : AppCompatActivity(), LargeOrderDialogFragment.LargeOrderDialogListener {

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
     * If the user chooses to accept the large order fee, continues with confirmation message.
     */
    override fun onLargeOrderDialogPositiveClick() {
        // Creates explanatory snackbar and navigates to the next fragment
        Snackbar.make(
            findViewById(R.id.next_button),
            R.string.fee_applied,
            Snackbar.LENGTH_LONG
        )
            .setAction("DISMISS", View.OnClickListener {})
            .show()
        navController.navigate(R.id.action_flavorFragment_to_toppingsFragment)
    }


    /**
     * If the user chooses not to accept the large order fee, a message is
     * displayed asking the user to remove a donut.
     */
    override fun onLargeOrderDialogNegativeClick() {
        // Creates explanatory snackbar
        Snackbar.make(
            findViewById(R.id.next_button),
            R.string.remove_donut,
            Snackbar.LENGTH_LONG
        )
            .setAction("DISMISS", View.OnClickListener {})
            .show()
    }

    /**
     * Handle navigation when Up is selected from the action bar.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}