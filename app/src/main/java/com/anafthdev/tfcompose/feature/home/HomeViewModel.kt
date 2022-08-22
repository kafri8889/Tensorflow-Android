package com.anafthdev.tfcompose.feature.home

import androidx.lifecycle.viewModelScope
import com.anafthdev.staver.foundation.viewmodel.StatefulViewModel
import com.anafthdev.tfcompose.feature.home.environment.IHomeEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	homeEnvironment: IHomeEnvironment
): StatefulViewModel<HomeState, HomeEffect, HomeAction, IHomeEnvironment>(
	HomeState(),
	homeEnvironment
) {
	
	init {
		viewModelScope.launch(environment.dispatcher) {
			environment.getClassificationResults().collect { results ->
				setState {
					copy(
						classificationResults = results
					)
				}
			}
		}
	}
	
	override fun dispatch(action: HomeAction) {
		when (action) {
			is HomeAction.Predict -> {
				viewModelScope.launch {
					environment.predict(action.bitmap)
				}
			}
			is HomeAction.SetImageRequest -> {
				viewModelScope.launch {
					setState {
						copy(
							imageRequest = action.imageRequest
						)
					}
				}
			}
			is HomeAction.SetClassification -> {
				viewModelScope.launch {
					setState {
						copy(
							selectedClassification = action.classification
						)
					}
				}
			}
			is HomeAction.SetClassificationResult -> {
				viewModelScope.launch {
					setState {
						copy(
							classificationResults = action.results
						)
					}
				}
			}
			is HomeAction.SetClassificationDropdownMenuShowed -> {
				viewModelScope.launch {
					setState {
						copy(
							isClassificationDropdownMenuShowed = action.show
						)
					}
				}
			}
		}
	}
}