package com.anafthdev.tfcompose.feature.tf_compose.environment

import kotlinx.coroutines.CoroutineDispatcher

interface ITFComposeEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
}