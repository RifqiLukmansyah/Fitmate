package com.rifqi.fitmate.ui.util

import java.util.concurrent.Executors

const val NOTIFICATION_CHANNEL_NAME = "Fitmate Exercise Channel"
const val NOTIFICATION_CHANNEL_ID = "notify-schedule"
const val NOTIFICATION_ID = 32
const val ID_REPEATING = 101

private val SINGLE_EXECUTOR = Executors.newSingleThreadExecutor()

fun executeThread(f: () -> Unit) {
    SINGLE_EXECUTOR.execute(f)
}
