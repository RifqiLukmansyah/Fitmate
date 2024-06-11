package com.rifqi.fitmate.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rifqi.fitmate.data.local.entity.ScheduleExerciseEntity
import com.rifqi.fitmate.data.model.ScheduleExercise

@Dao
interface ScheduleExerciseDao {

    @Query("SELECT * FROM schedule_exercise WHERE dateString = :selectedDate")
    fun getExercisesByDate(selectedDate: String): List<ScheduleExerciseEntity>

    @Query("SELECT id,id_exercise, dateString, GROUP_CONCAT(exercise_category) as categories,  " +
            "CASE " +
            "WHEN COUNT(DISTINCT exercise_muscle_target) = 1 THEN GROUP_CONCAT(exercise_muscle_target) " +
            "WHEN COUNT(DISTINCT exercise_muscle_target) = 2 THEN GROUP_CONCAT(exercise_muscle_target, ' and ') " +
            "WHEN COUNT(DISTINCT exercise_muscle_target) > 2 THEN 'Mixed' END as muscleTarget, " +
            "isFinished, SUM(exercise_calori) as totalCalories, COUNT(*) as exerciseCount " +
            "FROM schedule_exercise GROUP BY dateString ORDER BY dateMillis ASC")
    fun getSummaryByDate(): List<ScheduleExercise>


    @Query("SELECT * FROM schedule_exercise WHERE dateString = :selectedDate AND id_exercise = :exerciseId")
    fun isExerciseAlreadyExist(selectedDate: String,exerciseId : Long): List<ScheduleExerciseEntity>

    @Query("SELECT dateString, COUNT(*) as total_exercise_today, " +
            "CASE " +
            "WHEN COUNT(DISTINCT exercise_muscle_target) = 1 THEN GROUP_CONCAT(exercise_muscle_target) " +
            "WHEN COUNT(DISTINCT exercise_muscle_target) = 2 THEN GROUP_CONCAT(exercise_muscle_target, ' and ') " +
            "WHEN COUNT(DISTINCT exercise_muscle_target) > 2 THEN 'Mixed' END as muscleTarget, " +
            "SUM(CAST(isFinished as INTEGER)) as total_exercise_finished, SUM(exercise_calori) as total_calori " +
            "FROM schedule_exercise WHERE dateString = :date GROUP BY dateString")
    fun getExerciseSummaryByDate(date: String): TodayExerciseSummary


    @Insert
    suspend fun insertExercise(exercise: ScheduleExerciseEntity)

    @Query("UPDATE schedule_exercise SET isFinished = 1 WHERE id_exercise = :workoutId AND dateString = :dateString")
    suspend fun updateExerciseSchedule(workoutId: Long , dateString: String)


    @Query("DELETE FROM schedule_exercise WHERE dateString = :dateString")
    suspend fun deleteExerciseByDate(dateString: String)
    @Delete
    suspend fun deleteExercise(exercise: ScheduleExerciseEntity)
}

data class TodayExerciseSummary(
    val dateString: String,
    val muscleTarget: String,

    val total_exercise_today: Int,
    val total_exercise_finished: Int,
    val total_calori: Int
)