package com.planets.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.planets.demo.presentation.PlanetDetail
import com.planets.demo.presentation.PlanetDetailViewModel
import com.planets.demo.presentation.PlanetScreen
import com.planets.demo.presentation.PlanetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "planet_screen") {
                composable(route = "planet_screen") {
                    Surface(color = MaterialTheme.colors.background) {
                        val viewModel = hiltViewModel<PlanetViewModel>()
                        val planets = viewModel.planetPagingFlow.collectAsLazyPagingItems()
                        PlanetScreen(planets, navController)
                    }
                }
                composable(route = "second/{planetId}") { backStackEntry ->
                    val planetId = backStackEntry.arguments?.getString("planetId")
                    val viewModel = hiltViewModel<PlanetDetailViewModel>()
                    planetId?.let { viewModel.fetchPlanetDetails(planetId)  }
                    viewModel.planetDetails.value?.let { planet -> PlanetDetail(planet)  }
                }
            }
        }
    }
}