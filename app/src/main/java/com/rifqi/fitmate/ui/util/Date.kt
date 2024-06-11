package com.rifqi.fitmate.ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}