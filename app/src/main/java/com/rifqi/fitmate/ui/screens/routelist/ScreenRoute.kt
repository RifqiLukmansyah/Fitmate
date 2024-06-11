package com.rifqi.fitmate.ui.screens.routelist

sealed class ScreenRoute(val route: String) {
    object Home : ScreenRoute("home")
    object Explore : ScreenRoute("explore/{searchQuery}"){
        fun createRoute(searchQuery: String) = "explore/$searchQuery"
    }
    object Profile : ScreenRoute("profile")


    object DetailWorkout : ScreenRoute("home/{workoutId}") {
        fun createRoute(workoutId: Long) = "home/$workoutId"
    }

    object InteractiveLearn : ScreenRoute("detailworkout/{workoutId}") {
        fun createRoute(workoutId: Long) = "detailworkout/$workoutId"
    }

    object Schendule : ScreenRoute("schedule")
    object DetailSchedule : ScreenRoute("schedule/{workoutdate}") {
        fun createRoute(workoutdate: String) = "schedule/$workoutdate"
    }
    object OnBoarding : ScreenRoute("onboarding")
    object EquimentSearch : ScreenRoute("equiment-search")
    object ReminderSetting : ScreenRoute("reminder-setting")
    val requiresBottomBar: Boolean
        get() = this in listOf(Home, Profile, Explore, Schendule)


}