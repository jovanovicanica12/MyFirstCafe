package com.example.myfirstcafe.core.ui.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object MoneyFormat {
    private val symbols = DecimalFormatSymbols(Locale.ENGLISH).apply {
        decimalSeparator = '.'
        groupingSeparator = ','
    }
    private val formatter = DecimalFormat("#0.00", symbols)

    fun format(amount: Double): String = "${formatter.format(amount)} €"
}