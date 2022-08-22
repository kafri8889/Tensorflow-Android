package com.anafthdev.tfcompose.feature.home.di

import com.anafthdev.tfcompose.feature.home.environment.HomeEnvironment
import com.anafthdev.tfcompose.feature.home.environment.IHomeEnvironment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
	
	@Binds
	abstract fun provideEnvironment(
		homeEnvironment: HomeEnvironment
	): IHomeEnvironment
	
}