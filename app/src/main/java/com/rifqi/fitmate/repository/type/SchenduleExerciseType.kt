package com.rifqi.fitmate.repository.type

import com.rifqi.fitmate.data.local.entity.SchenduleExerciseEntity
import com.rifqi.fitmate.data.model.SchenduleExercise
import kotlinx.coroutines.flow.Flow

interface SchenduleExerciseType {

    suspend fun insertSchendule(exercise: SchenduleExerciseEntity)
     fun getAllSchendule() :Flow<List<SchenduleExercise>>


    fun isExerciseAlreadyExist(date : String, exerciseId : Long) : List<SchenduleExerciseEntity>
    fun getAllExerciseByDate(date : String) :Flow<List<SchenduleExerciseEntity>>

    suspend fun updateExerciseSchedule(workoutId: Long, dateString: String)

    suspend fun deleteScheduleByDate(date: String)
    suspend fun deleteExercise(exercise: SchenduleExerciseEntity)
}