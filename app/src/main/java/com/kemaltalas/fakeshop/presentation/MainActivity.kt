package com.kemaltalas.fakeshop.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.kemaltalas.fakeshop.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kemaltalas.fakeshop.databinding.ActivityMainBinding
import com.kemaltalas.fakeshop.presentation.adapters.BasketAdapter
import com.kemaltalas.fakeshop.presentation.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var viewModel: FavoritesViewModel

    private var badgenumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = binding.navView

        val bottommNav = findViewById<BottomNavigationView>(R.id.nav_view)
        bottommNav?.setupWithNavController(navController)
        var badge = bottommNav.getOrCreateBadge(R.id.basketFragment)
        badge.isVisible = true
        badge.maxCharacterCount = 2

        viewModel.getCartItems().observe(this){
            badge.number = it.size
        }

        //setupBottomNavMenu(navController)
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            // the IDs of fragments as defined in the `navigation_graph`
            if (nd.id == R.id.homeFragment || nd.id == R.id.categoriesFragment
                || nd.id == R.id.basketFragment || nd.id == R.id.favoritesFragment || nd.id == R.id.loginFragment || nd.id == R.id.userPanelFragment
            ) {
                navView.visibility = View.VISIBLE
            } else {
                navView.visibility = View.GONE
            }
        }

    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.nav_view)

        bottomNav?.setupWithNavController(navController)

        var badge = bottomNav?.getOrCreateBadge(R.id.basketFragment)
        badge?.isVisible = true
        badge?.number = badgenumber

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.fragmentContainerView)) || super.onOptionsItemSelected(item)
    }


    }


