package com.anafthdev.tfcompose.feature.tf_compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.tfcompose.feature.tf_compose.data.LocalTFComposeState
import com.anafthdev.tfcompose.foundation.theme.TFComposeTheme
import com.anafthdev.tfcompose.runtime.navigation.TFComposeNavigation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import timber.log.Timber

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun TFCompose(
	viewModel: TFComposeViewModel
) {
	
	val state by viewModel.state.collectAsStateWithLifecycle()
	
	TFComposeTheme {
		CompositionLocalProvider(
			LocalTFComposeState provides state,
			LocalOverscrollConfiguration provides null
		) {
			val backgroundColor = MaterialTheme.colorScheme.background
			
			Surface(
				modifier = Modifier.fillMaxSize(),
				color = backgroundColor
			) {
				TFComposeNavigation()
			}
		}
	}
	
}
