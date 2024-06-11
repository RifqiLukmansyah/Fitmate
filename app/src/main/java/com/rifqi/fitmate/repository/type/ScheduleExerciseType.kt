package com.rifqi.fitmate.repository.type

import com.rifqi.fitmate.data.local.entity.ScheduleExerciseEntity
import com.rifqi.fitmate.data.model.ScheduleExercise
import kotlinx.coroutines.flow.Flow

interface ScheduleExerciseType {

    suspend fun insertSchedule(exercise: ScheduleExerciseEntity)
     fun getAllSchedule() :Flow<List<ScheduleExercise>>


    fun isExerciseAlreadyExist(date : String, exerciseId : Long) : List<ScheduleExerciseEntity>
    fun getAllExerciseByDate(date : String) :Flow<List<ScheduleExerciseEntity>>

    suspend fun updateExerciseSchedule(workoutId: Long, dateString: String)

    suspend fun deleteScheduleByDate(date: String)
    suspend fun deleteExercise(exercise: ScheduleExerciseEntity)
}