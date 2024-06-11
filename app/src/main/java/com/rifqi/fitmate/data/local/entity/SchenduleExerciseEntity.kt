package com.rifqi.fitmate.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schendule_exercise")
data class SchenduleExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "id_exercise")
    val id_exercise: Long,

    @ColumnInfo(name = "name_exercise")
    val name_exercise: String,

    @ColumnInfo(name = "exercise_calori")
    val exercise_calori: Int,

    @ColumnInfo(name = "exercise_gif_url")
    val exercise_gif_url: String,

    @ColumnInfo(name = "exercise_category")
    val exercise_category: String,

    @ColumnInfo(name = "exercise_muscle_target")
    val exercise_muscle_target: String,

    @ColumnInfo(name = "dateMillis")
    val dateMillis: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "dateString")
    val dateString: String,

    @ColumnInfo(name = "isFinished")
    val isFinished: Boolean
)