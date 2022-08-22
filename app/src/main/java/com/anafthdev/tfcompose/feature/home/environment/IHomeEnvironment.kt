package com.anafthdev.tfcompose.feature.home.environment

import android.graphics.Bitmap
import com.anafthdev.tfcompose.data.model.ClassificationResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface IHomeEnvironment {
	
	val dispatcher: CoroutineDispatcher
	
	fun getClassificationResults(): Flow<List<ClassificationResult>>
	
	suspend fun predict(bitmap: Bitmap)
	
}