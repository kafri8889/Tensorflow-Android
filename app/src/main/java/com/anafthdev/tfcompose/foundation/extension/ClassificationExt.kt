package com.anafthdev.tfcompose.foundation.extension

import android.content.Context
import com.anafthdev.tfcompose.data.Classification
import org.tensorflow.lite.support.common.FileUtil

fun Classification.getLabels(context: Context): List<String> {
	return when (this) {
		Classification.FLOWER -> FileUtil.loadLabels(context, "labels_flower.txt")
	}
}
