package com.anafthdev.tfcompose.runtime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.anafthdev.tfcompose.data.TFComposeDestination

@Composable
fun TFComposeNavigation() {
	
	val navController = rememberNavController()
	
	NavHost(
		navController = navController,
		startDestination = TFComposeDestination.Home.Root.route
	) {
		HomeNavHost(navController)
	}
}
