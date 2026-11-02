package com.example.caffeshop.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyHelper {
    fun formatRupiah(amount: Int): String {
        val localeId = Locale.forLanguageTag("id-ID")
        val formatter = NumberFormat.getNumberInstance(localeId)
        return "Rp " + formatter.format(amount)
    }
}
