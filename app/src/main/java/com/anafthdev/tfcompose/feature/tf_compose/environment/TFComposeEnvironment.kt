package com.anafthdev.tfcompose.feature.tf_compose.environment

import com.anafthdev.staver.foundation.di.DiName
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class TFComposeEnvironment @Inject constructor(
	@Named(DiName.IO) override val dispatcher: CoroutineDispatcher
): ITFComposeEnvironment {
}