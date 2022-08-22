package com.anafthdev.tfcompose.foundation.extension

/**
 * uppercase fist word in string
 *
 * "abc".uppercaseFirstWord() => "Abc"
 *
 * "abC".uppercaseFirstWord() => "AbC"
 *
 * "abC".uppercaseFirstWord(true) => "AbC"
 *
 * "abC".uppercaseFirstWord(true) => "Abc"
 *
 * @author kafri8889
 */
fun String.uppercaseFirstWord(onlyFirstWord: Boolean = false): String {
	if (isBlank()) return this
	if (length == 1) return uppercase()
	
	return if (onlyFirstWord) {
		"${this[0].uppercase()}${this.substring(1, length).lowercase()}"
	} else "${this[0].uppercase()}${this.substring(1, length)}"
}
