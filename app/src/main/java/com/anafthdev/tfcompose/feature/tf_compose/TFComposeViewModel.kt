package com.anafthdev.tfcompose.feature.tf_compose

import com.anafthdev.staver.foundation.viewmodel.StatefulViewModel
import com.anafthdev.tfcompose.feature.tf_compose.environment.ITFComposeEnvironment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TFComposeViewModel @Inject constructor(
	tfComposeEnvironment: ITFComposeEnvironment
): StatefulViewModel<TFComposeState, TFComposeEffect, TFComposeAction, ITFComposeEnvironment>(
	TFComposeState,
	tfComposeEnvironment
) {
	
	override fun dispatch(action: TFComposeAction) {}
}