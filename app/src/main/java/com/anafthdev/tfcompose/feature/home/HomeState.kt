package com.anafthdev.tfcompose.feature.home

import coil.request.ImageRequest
import com.anafthdev.tfcompose.data.Classification
import com.anafthdev.tfcompose.data.model.ClassificationResult

data class HomeState(
	val imageRequest: ImageRequest? = null,
	val classificationResults: List<ClassificationResult> = emptyList(),
	val selectedClassification: Classification = Classification.FLOWER,
	val isClassificationDropdownMenuShowed: Boolean = false
)
