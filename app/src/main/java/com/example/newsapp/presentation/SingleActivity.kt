package com.example.newsapp.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.newsapp.R
import com.example.newsapp.base.BaseActivity
import com.example.newsapp.databinding.ActivitySingleBinding
import com.google.android.material.snackbar.Snackbar

class SingleActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySingleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NewsApp)
        super.onCreate(savedInstanceState)

        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStartDestination()


    }

    private fun setStartDestination() {
        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.root_container) as NavHostFragment)
        val navController = navHostFragment.navController
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        navController.graph = graph
    }

}