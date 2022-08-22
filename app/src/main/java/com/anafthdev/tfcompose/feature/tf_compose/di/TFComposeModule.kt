package com.anafthdev.tfcompose.feature.tf_compose.di

import com.anafthdev.tfcompose.feature.tf_compose.environment.ITFComposeEnvironment
import com.anafthdev.tfcompose.feature.tf_compose.environment.TFComposeEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TFComposeModule {
	
	@Binds
	abstract fun provideEnvironment(
		tfComposeEnvironment: TFComposeEnvironment
	): ITFComposeEnvironment
	
}