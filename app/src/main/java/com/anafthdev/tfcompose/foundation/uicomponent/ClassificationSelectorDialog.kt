package com.anafthdev.tfcompose.foundation.uicomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.anafthdev.tfcompose.R
import com.anafthdev.tfcompose.data.Classification
import com.anafthdev.tfcompose.foundation.extension.uppercaseFirstWord

/**
 * @see <a href="https://m3.material.io/components/dialogs/specs">MD3 Dialogs</a>
 */
@Composable
fun ClassificationSelectorDialog(
	selectedClassification: Classification,
	onDismissRequest: () -> Unit,
	onClassificationSelected: (Classification) -> Unit
) {

	Dialog(onDismissRequest = onDismissRequest) {
		Card(
			shape = MaterialTheme.shapes.large,
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.surface
			),
			modifier = Modifier
				.fillMaxWidth()
		) {
			Column(
				modifier = Modifier
					.padding(24.dp)
					.fillMaxWidth()
			) {
				Text(
					text = stringResource(id = R.string.select_classification),
					style = MaterialTheme.typography.headlineSmall.copy(
						color = MaterialTheme.colorScheme.onSurface
					)
				)
				
				Spacer(modifier = Modifier.height(16.dp))
				
				LazyVerticalGrid(
					columns = GridCells.Fixed(2),
					modifier = Modifier.fillMaxWidth()
				) {
					items(
						items = Classification.values()
					) { classification ->
						Row(
							verticalAlignment = Alignment.CenterVertically,
							modifier = Modifier
								.clip(MaterialTheme.shapes.medium)
								.clickable {
									onClassificationSelected(classification)
									onDismissRequest()
								}
						) {
							RadioButton(
								selected = selectedClassification == classification,
								onClick = {
									onClassificationSelected(classification)
									onDismissRequest()
								}
							)
							
							Text(
								maxLines = 1,
								overflow = TextOverflow.Ellipsis,
								text = classification.name.uppercaseFirstWord(onlyFirstWord = true),
								style = MaterialTheme.typography.bodyMedium.copy(
									color = MaterialTheme.colorScheme.onSurfaceVariant
								)
							)
						}
					}
				}
				
				Spacer(modifier = Modifier.height(24.dp))
				
				Row(
					horizontalArrangement = Arrangement.End,
					modifier = Modifier
						.fillMaxWidth()
				) {
					TextButton(
						onClick = onDismissRequest
					) {
						Text(stringResource(id = R.string.close))
					}
				}
			}
		}
	}
}
