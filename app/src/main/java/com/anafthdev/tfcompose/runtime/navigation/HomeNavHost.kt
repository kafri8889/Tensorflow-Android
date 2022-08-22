package com.anafthdev.tfcompose.runtime.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.anafthdev.tfcompose.data.TFComposeDestination
import com.anafthdev.tfcompose.feature.home.HomeScreen
import com.anafthdev.tfcompose.feature.home.HomeViewModel

fun NavGraphBuilder.HomeNavHost(navController: NavController) {
	navigation(
		startDestination = TFComposeDestination.Home.Home.route,
		route = TFComposeDestination.Home.Root.route
	) {
		composable(TFComposeDestination.Home.Home.route) { backEntry ->
			val viewModel = hiltViewModel<HomeViewModel>(backEntry)
			
			HomeScreen(
				navController = navController,
				viewModel = viewModel
			)
		}
	}
}
