package com.rifqi.fitmate.ui.util

import android.content.Context
import android.content.SharedPreferences

object OnBordingPrefence {

    private const val PREF_NAME = "fitmate_prefs"
    private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isOnboardingCompleted(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    fun setOnboardingCompleted(context: Context, completed: Boolean) {
        getSharedPreferences(context).edit().putBoolean(KEY_ONBOARDING_COMPLETED, completed).apply()
    }
}