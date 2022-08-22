package com.anafthdev.tfcompose.data

import androidx.compose.ui.graphics.Color
import com.anafthdev.tfcompose.data.model.ClassificationColor

object ClassificationColors {
	
	val color_1 = ClassificationColor(
		primary = Color(0xFF81B2CA),
		container = Color(0xFFEDF4F7)
	)
	
	val color_2 = ClassificationColor(
		primary = Color(0xFF42887C),
		container = Color(0xFFE4EEEC)
	)
	
	val color_3 = ClassificationColor(
		primary = Color(0xFF836F81),
		container = Color(0xFFEDEBED)
	)
	
	val color_4 = ClassificationColor(
		primary = Color(0xFF266275),
		container = Color(0xFFD4E2E6)
	)
	
	val color_5 = ClassificationColor(
		primary = Color(0xFF58A7A7),
		container = Color(0xFFE2F1F1)
	)
	
	val color_6 = ClassificationColor(
		primary = Color(0xFF76B17E),
		container = Color(0xFFF1FFF3)
	)
	
	val color_7 = ClassificationColor(
		primary = Color(0xFFF02D55),
		container = Color(0xFFF7E2E7)
	)
	
	val color_8 = ClassificationColor(
		primary = Color(0xFF5F871C),
		container = Color(0xFFE1EAD2)
	)
	
	val color_9 = ClassificationColor(
		primary = Color(0xFFA15A30),
		container = Color(0xFFF0E0D7)
	)
	
	val color_10 = ClassificationColor(
		primary = Color(0xFFDDB300),
		container = Color(0xFFFDF4CC)
	)
	
	val color_11 = ClassificationColor(
		primary = Color(0xFFAE214A),
		container = Color(0xFFF3D3DC)
	)
	
	val color_12 = ClassificationColor(
		primary = Color(0xFFE65C00),
		container = Color(0xFFFFE0CC)
	)
	
	val color_13 = ClassificationColor(
		primary = Color(0xFF5F9679),
		container = Color(0xFFE1EDE7)
	)
	
	val color_14 = ClassificationColor(
		primary = Color(0xFF3A5073),
		container = Color(0xFFD9DEE6)
	)
	
	val color_15 = ClassificationColor(
		primary = Color(0xFFA27E00),
		container = Color(0xFFFFE6A3)
	)
	
	val color_16 = ClassificationColor(
		primary = Color(0xFFA87373),
		container = Color(0xFFF2E7E7)
	)
	
	val color_17 = ClassificationColor(
		primary = Color(0xFF7CB150),
		container = Color(0xFFE8FCD9)
	)
	
	val color_18 = ClassificationColor(
		primary = Color(0xFFC73EA8),
		container = Color(0xFFF8DAF1)
	)
	
	val color_19 = ClassificationColor(
		primary = Color(0xFF95971E),
		container = Color(0xFFEDEED3)
	)
	
	val color_20 = ClassificationColor(
		primary = Color(0xFF1B7F6A),
		container = Color(0xFFD2E8E4)
	)
	
	val values = listOf(
		color_1,
		color_2,
		color_3,
		color_4,
		color_5,
		color_6,
		color_7,
		color_8,
		color_9,
		color_10,
		color_11,
		color_12,
		color_13,
		color_14,
		color_15,
		color_16,
		color_17,
		color_18,
		color_19,
		color_20,
	)
	
	fun getRandomColor(): ClassificationColor = values.random()
	
}