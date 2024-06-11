package com.rifqi.fitmate.ui.screens.routelist

sealed class ScreenRoute(val route: String) {
    data object Home : ScreenRoute("home")
    data object Explore : ScreenRoute("explore/{searchQuery}"){
        fun createRoute(searchQuery: String) = "explore/$searchQuery"
    }
    data object Profile : ScreenRoute("profile")


    data object DetailWorkout : ScreenRoute("home/{workoutId}") {
        fun createRoute(workoutId: Long) = "home/$workoutId"
    }

    data object InteractiveLearn : ScreenRoute("detailworkout/{workoutId}") {
        fun createRoute(workoutId: Long) = "detailworkout/$workoutId"
    }

    data object Schedule : ScreenRoute("schedule")
    data object DetailSchedule : ScreenRoute("schedule/{workoutdate}") {
        fun createRoute(workoutdate: String) = "schedule/$workoutdate"
    }
    data object OnBoarding : ScreenRoute("onboarding")
    data object EquimentSearch : ScreenRoute("equiment-search")
    data object ReminderSetting : ScreenRoute("reminder-setting")


}