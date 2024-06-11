package com.rifqi.fitmate.data.notification

import android.content.Context
import android.content.SharedPreferences

object NotificationStatus {

    private const val PREFERENCES_NAME = "NotificationPreferences"
    private const val KEY_NOTIFICATION_STATUS = "notificationStatus"

    fun saveNotificationStatus(context: Context, isEnabled: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putBoolean(KEY_NOTIFICATION_STATUS, isEnabled)

        editor.apply()
    }

    fun getNotificationStatus(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_NOTIFICATION_STATUS, false)
    }
}