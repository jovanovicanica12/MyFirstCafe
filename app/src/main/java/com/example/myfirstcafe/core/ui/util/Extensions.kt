package com.example.myfirstcafe.core.ui.util

import kotlin.math.roundToInt

fun Double.toMoney(): String = MoneyFormat.format(this)
fun Double.round2(): Double = (this * 100).roundToInt() / 100.0