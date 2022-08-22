package com.anafthdev.tfcompose.feature.home

import android.graphics.Bitmap
import coil.request.ImageRequest
import com.anafthdev.tfcompose.data.Classification
import com.anafthdev.tfcompose.data.model.ClassificationResult

sealed interface HomeAction {
	data class Predict(val bitmap: Bitmap): HomeAction
	data class SetImageRequest(val imageRequest: ImageRequest?): HomeAction
	data class SetClassification(val classification: Classification): HomeAction
	data class SetClassificationResult(val results: List<ClassificationResult>): HomeAction
	data class SetClassificationDropdownMenuShowed(val show: Boolean): HomeAction
	
//	/**
//	 * Pick image from gallery
//	 */
//	object PickImage: HomeAction
//
//	/**
//	 * Take image from camera
//	 */
//	object TakeImage: HomeAction
}