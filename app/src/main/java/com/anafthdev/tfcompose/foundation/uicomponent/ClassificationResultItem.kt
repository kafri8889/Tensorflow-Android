package com.anafthdev.tfcompose.foundation.uicomponent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.anafthdev.tfcompose.data.model.ClassificationResult
import java.text.DecimalFormat

@Composable
fun ClassificationResultItems(
	results: List<ClassificationResult>,
	modifier: Modifier = Modifier
) {
	
	val decimalFormat = remember {
		DecimalFormat("#.##")
	}
	
	Column(
		modifier = modifier
	) {
		results.forEach { classificationResult ->
			val progress by animateFloatAsState(
				targetValue = classificationResult.accuracy,
				animationSpec = tween(1000)
			)
			
			Spacer(modifier = Modifier.height(16.dp))
			
			Row(
				verticalAlignment = Alignment.CenterVertically,
			) {
				Text(
					text = classificationResult.label,
					style = MaterialTheme.typography.titleSmall,
					modifier = Modifier
						.weight(1f)
				)
				
				Spacer(modifier = Modifier.width(16.dp))
				
				Text(
					text = "${decimalFormat.format(classificationResult.accuracy * 100)}%",
					style = MaterialTheme.typography.bodyMedium
				)
			}
			
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 8.dp)
			) {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(32.dp)
						.clip(MaterialTheme.shapes.small)
						.background(MaterialTheme.colorScheme.primaryContainer)
				)
				
				Box(
					modifier = Modifier
						.fillMaxWidth(progress)
						.height(32.dp)
						.clip(MaterialTheme.shapes.small)
						.background(MaterialTheme.colorScheme.primary)
						.zIndex(2f)
				)
			}
		}
	}
}
