package com.planets.demo.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.core.app.ApplicationProvider
import com.planets.demo.PlanetActivity
import com.planets.demo.PlanetApp
import com.planets.demo.data.mappers.toPlanet
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class PlanetUITest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<PlanetActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            PlanetApp()
        }
    }

    @Test
    fun clickListItem_NavigateToDetailScreen() {
        // Create a NavController for navigation
        composeTestRule.activity.setContent {
            val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            // Provide fake data for the planetPagingFlow
            val fakePlanets = TestData.planets.map { it.toPlanet() }
            val fakePagingData = PagingData.from(fakePlanets)
            composeTestRule.setContent {
                NavHost(navController, startDestination = "planet_list_screen") {
                    composable("planet_list_screen") {
                        val viewModel =
                            hiltViewModel<PlanetViewModel>() // Provide a mock view model with test data
                        val fakeFlow = flow {
                            emit(fakePagingData)
                        }
                        val fakeLazyPagingItems = fakeFlow.collectAsLazyPagingItems()
                        PlanetScreen(fakeLazyPagingItems, navController)
                    }
                    composable("planet_detail_screen/{planetId}") { backStackEntry ->
                        PlanetDetail(fakePlanets.get(0))
                    }
                }
            }

            // Click on a list item
            composeTestRule.onNodeWithTag("planet_list_item_0").performClick()

            // Verify navigation to detail screen
            composeTestRule.onNodeWithTag("planet_detail_screen").assertExists()

        }
    }
}