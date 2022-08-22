package com.anafthdev.tfcompose.foundation.extension

import com.anafthdev.tfcompose.data.ModelType

fun ModelType.isFloating() = this == ModelType.FLOATING
fun ModelType.isQuantized() = this == ModelType.QUANTIZED
