package com.anafthdev.tfcompose.feature.home.environment

import android.content.Context
import android.graphics.Bitmap
import com.anafthdev.staver.foundation.di.DiName
import com.anafthdev.tfcompose.data.model.ClassificationResult
import com.anafthdev.tfcompose.foundation.common.Classifier
import com.anafthdev.tfcompose.foundation.common.FlowerClassification
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Named

class HomeEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher,
	@ApplicationContext private val context: Context
): IHomeEnvironment {
	
	private val _classificationResults = MutableStateFlow(emptyList<ClassificationResult>())
	private val classificationResults: StateFlow<List<ClassificationResult>> = _classificationResults
	
	private val flowerClassification = FlowerClassification(context)
	
	override fun getClassificationResults(): Flow<List<ClassificationResult>> {
		return classificationResults
	}
	
	override suspend fun predict(bitmap: Bitmap) {
		val result = flowerClassification.predict(bitmap)
		
		_classificationResults.emit(result)
	}
	
}