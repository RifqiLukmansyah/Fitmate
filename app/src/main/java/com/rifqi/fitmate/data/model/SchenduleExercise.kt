package com.rifqi.fitmate.data.model

data class SchenduleExercise (
    val id: Long,
    val id_exercise: Long,
    val dateString: String,
    val categories: String,
    val muscleTarget: String,
    val isFinished : Boolean,
    val totalCalories: Double,
    val exerciseCount: Int)