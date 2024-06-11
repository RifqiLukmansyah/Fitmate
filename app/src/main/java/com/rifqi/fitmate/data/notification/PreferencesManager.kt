package com.rifqi.fitmate.data.notification

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {

    private const val PREFERENCES_NAME = "ReminderPreferences"
    private const val KEY_HOUR_OF_DAY = "hourOfDay"
    private const val KEY_MINUTE = "minute"

    fun saveReminderTime(context: Context, hourOfDay: Int, minute: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putInt(KEY_HOUR_OF_DAY, hourOfDay)
        editor.putInt(KEY_MINUTE, minute)

        editor.apply()
    }

    fun getReminderTime(context: Context): Pair<Int, Int> {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        val hourOfDay = sharedPreferences.getInt(KEY_HOUR_OF_DAY, 0)
        val minute = sharedPreferences.getInt(KEY_MINUTE, 0)

        return Pair(hourOfDay, minute)
    }
}
