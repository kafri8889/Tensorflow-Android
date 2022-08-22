package com.anafthdev.tfcompose.runtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.anafthdev.tfcompose.BuildConfig
import com.anafthdev.tfcompose.feature.tf_compose.TFCompose
import com.anafthdev.tfcompose.feature.tf_compose.TFComposeViewModel
import com.anafthdev.tfcompose.foundation.theme.TFComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	
	private val tfComposeViewModel: TFComposeViewModel by viewModels()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (BuildConfig.DEBUG) {
			Timber.plant(object : Timber.DebugTree() {})
		}
		
//		WindowCompat.setDecorFitsSystemWindows(window, false)
		
		setContent {
			TFCompose(
				viewModel = tfComposeViewModel
			)
		}
	}
	
}
