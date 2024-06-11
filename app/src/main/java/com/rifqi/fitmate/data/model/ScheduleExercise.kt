package com.rifqi.fitmate.data.model

data class ScheduleExercise (
    val id: Long,
    val id_exercise: Long,
    val dateString: String,
    val categories: String,
    val muscleTarget: String,
    val isFinished : Boolean,
    val totalCalories: Double,
    val exerciseCount: Int)