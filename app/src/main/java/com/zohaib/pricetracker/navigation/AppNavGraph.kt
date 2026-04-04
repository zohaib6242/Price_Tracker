package com.zohaib.pricetracker.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.navDeepLink
import com.zohaib.pricetracker.presentation.details.DetailsScreen
import com.zohaib.pricetracker.presentation.feed.FeedScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "feed"
    ) {

        composable("feed") {
            FeedScreen(navController = navController)
        }

        composable(
            route = "details/{symbol}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "stocks://symbol/{symbol}"
                }
            )
        ) {
            DetailsScreen()
        }
    }
}