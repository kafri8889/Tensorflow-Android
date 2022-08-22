package com.anafthdev.tfcompose.data

sealed class TFComposeDestination(val route: String) {
	
	class Home {
		object Root: TFComposeDestination("home/root")
		object Home: TFComposeDestination("home/home")
	}
	
}
