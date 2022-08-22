package com.anafthdev.tfcompose.data.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ClassificationColor(
	val primary: @RawValue Color,
	val container: @RawValue Color
): Parcelable
