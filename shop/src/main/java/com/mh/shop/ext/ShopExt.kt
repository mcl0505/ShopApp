package com.mh.shop.ext

fun String.isChinesePhoneNumber(): Boolean {
    return Regex("^1[3456789]\\d{9}$").matches(this)
}

fun String.isHttpUrl() = if (this.contains("http://") || this.contains("https://")) true else false