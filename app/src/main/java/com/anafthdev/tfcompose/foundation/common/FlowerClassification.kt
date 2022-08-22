package com.anafthdev.tfcompose.foundation.common

import android.content.Context
import com.anafthdev.tfcompose.data.Classification
import com.anafthdev.tfcompose.data.ModelType
import com.anafthdev.tfcompose.foundation.extension.getLabels

class FlowerClassification(context: Context): Classifier(context, ModelType.FLOATING) {
	override fun getModel(): String {
		return "flower_classification_floating.tflite"
	}
	
	override fun getLabels(): List<String> {
		return Classification.FLOWER.getLabels(context)
	}
}