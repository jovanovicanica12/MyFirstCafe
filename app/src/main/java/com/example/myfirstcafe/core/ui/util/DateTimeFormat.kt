package com.example.myfirstcafe.core.ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeFormat {
    private val dateFmt = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
    private val timeFmt = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    fun date(epochMillis: Long): String = dateFmt.format(Date(epochMillis))
    fun time(epochMillis: Long): String = timeFmt.format(Date(epochMillis))
}