package com.anafthdev.tfcompose.foundation.extension

import android.graphics.Bitmap

fun Bitmap.toARGB8888(): Bitmap {
	return this.copy(Bitmap.Config.ARGB_8888, true)
}
