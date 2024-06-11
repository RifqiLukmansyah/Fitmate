package com.rifqi.fitmate.data.model

data class  SchenduleExerciseInput (
    val id_exercise: Long,
    val name_exercise: String,
    val exercise_calori: Int,
    val exercise_gif_url: String,
    val exercise_category: String,
    val date: Long = System.currentTimeMillis()
)