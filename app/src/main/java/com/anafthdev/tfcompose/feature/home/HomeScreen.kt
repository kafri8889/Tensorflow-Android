package com.anafthdev.tfcompose.feature.home

import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anafthdev.tfcompose.R
import com.anafthdev.tfcompose.data.model.ClassificationResult
import com.anafthdev.tfcompose.foundation.extension.getLabels
import com.anafthdev.tfcompose.foundation.extension.toARGB8888
import com.anafthdev.tfcompose.foundation.extension.toast
import com.anafthdev.tfcompose.foundation.extension.uppercaseFirstWord
import com.anafthdev.tfcompose.foundation.uicomponent.ClassificationResultItems
import com.anafthdev.tfcompose.foundation.uicomponent.ClassificationSelectorDialog
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: HomeViewModel
) {

	val backgroundColor = MaterialTheme.colorScheme.background
	
	val context = LocalContext.current
	
	val state by viewModel.state.collectAsStateWithLifecycle()
	
	val systemUiController = rememberSystemUiController()
	
	val pickImageResultLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.OpenDocument(),
		onResult = { uri ->
			if (uri != null) {
				val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
					ImageDecoder.decodeBitmap(
						ImageDecoder.createSource(context.contentResolver, uri)
					)
				} else {
					MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
				}
				
				viewModel.dispatch(
					HomeAction.Predict(bitmap.toARGB8888())
				)
				
				viewModel.dispatch(
					HomeAction.SetImageRequest(
						ImageRequest.Builder(context).apply {
							data(uri)
						}.build()
					)
				)
			} else "something went wrong".toast(context)
		}
	)
	
	val takePictureResultLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.TakePicturePreview(),
		onResult = { bitmap ->
			if (bitmap != null) {
				viewModel.dispatch(
					HomeAction.Predict(bitmap.toARGB8888())
				)
				
				viewModel.dispatch(
					HomeAction.SetImageRequest(
						ImageRequest.Builder(context).apply {
							data(bitmap)
						}.build()
					)
				)
			} else "something went wrong".toast(context)
		}
	)
	
	SideEffect {
		systemUiController.setSystemBarsColor(
			color = backgroundColor
		)
	}
	
	// Set default classification result
	LaunchedEffect(state.selectedClassification) {
		viewModel.dispatch(
			HomeAction.SetClassificationResult(
				arrayListOf<ClassificationResult>().apply {
					state.selectedClassification.getLabels(context).forEach { label ->
						add(
							ClassificationResult(
								label = label,
								accuracy = 0f
							)
						)
					}
				}
			)
		)
	}
	
	Box(
		modifier = Modifier
			.fillMaxSize()
	) {
		AnimatedVisibility(
			visible = state.isClassificationDropdownMenuShowed,
			enter = fadeIn(
				animationSpec = tween(300)
			),
			exit = fadeOut(
				animationSpec = tween(300)
			),
			modifier = Modifier
				.fillMaxSize()
				.zIndex(2f)
		) {
			ClassificationSelectorDialog(
				selectedClassification = state.selectedClassification,
				onDismissRequest = {
					viewModel.dispatch(
						HomeAction.SetClassificationDropdownMenuShowed(false)
					)
				},
				onClassificationSelected = { classification ->
					viewModel.dispatch(
						HomeAction.SetClassification(classification)
					)
				}
			)
		}
		
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier
				.padding(16.dp)
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
		) {
			SmallTopAppBar(
				title = {
					Text(
						maxLines = 1,
						overflow = TextOverflow.Ellipsis,
						text = state.selectedClassification.name.uppercaseFirstWord(onlyFirstWord = true)
					)
				},
				actions = {
					IconButton(
						onClick = {
							viewModel.dispatch(
								HomeAction.SetClassificationDropdownMenuShowed(true)
							)
						}
					) {
						Icon(
							painter = painterResource(id = R.drawable.ic_classification),
							contentDescription = null
						)
					}
				}
			)
			
			AsyncImage(
				model = state.imageRequest ?: ColorDrawable(Color.LightGray.toArgb()),
				contentScale = ContentScale.Crop,
				contentDescription = null,
				modifier = Modifier
					.fillMaxWidth()
					.aspectRatio(1f)
					.clip(MaterialTheme.shapes.medium)
			)
			
			Spacer(modifier = Modifier.height(16.dp))
			
			ClassificationResultItems(
				results = state.classificationResults,
				modifier = Modifier
					.fillMaxWidth()
			)
			
			Spacer(modifier = Modifier.height(16.dp))
			
			Row(
				horizontalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.fillMaxWidth()
			) {
				Button(
					onClick = {
						pickImageResultLauncher.launch(arrayOf("image/*"))
					}
				) {
					Text(stringResource(id = R.string.pick_image))
				}
				
				Button(
					onClick = {
						takePictureResultLauncher.launch(null)
					}
				) {
					Text(stringResource(id = R.string.take_image))
				}
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			Button(
				onClick = {
					// TODO: Live detection
				}
			) {
				Text(stringResource(id = R.string.live_detection))
			}
			
			Spacer(modifier = Modifier.height(24.dp))
		}
	}
}
