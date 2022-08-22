package com.anafthdev.tfcompose.foundation.common.di

import android.content.Context
import com.anafthdev.tfcompose.foundation.common.Classifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {
	
//	@Provides
//	@Singleton
//	fun provideClassifier(
//		@ApplicationContext context: Context
//	): Classifier = Classifier(context)
	
}