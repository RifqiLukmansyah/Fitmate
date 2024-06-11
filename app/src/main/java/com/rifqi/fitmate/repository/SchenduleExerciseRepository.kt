package com.rifqi.fitmate.repository

import com.rifqi.fitmate.data.local.dao.SchenduleExerciseDao
import com.rifqi.fitmate.data.local.dao.TodayExerciseSummary
import com.rifqi.fitmate.data.local.entity.SchenduleExerciseEntity
import com.rifqi.fitmate.data.model.SchenduleExercise
import com.rifqi.fitmate.repository.type.SchenduleExerciseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class SchenduleExerciseRepository @Inject constructor(private val db: SchenduleExerciseDao) :
    SchenduleExerciseType {
    override suspend fun insertSchendule(exercise: SchenduleExerciseEntity) {
        db.insertExercise(exercise)
    }

    override fun isExerciseAlreadyExist(
        date: String,
        exerciseId: Long
    ) : List<SchenduleExerciseEntity> {
       return db.isExerciseAlreadyExist(date, exerciseId)
    }

    override fun getAllSchendule(): Flow<List<SchenduleExercise>> {
        return flowOf(db.getSummaryByDate())
    }

    override fun getAllExerciseByDate(date: String): Flow<List<SchenduleExerciseEntity>> {
        return flowOf(db.getExercisesByDate(date))
    }

    fun getTodaySchedule() : List<SchenduleExerciseEntity> {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        return db.getExercisesByDate(currentDate)
    }


    fun getTodayExerciseSummary() : Flow<TodayExerciseSummary>{
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        return flowOf(db.getExerciseSummaryByDate(currentDate))
    }


    override suspend fun deleteScheduleByDate(date: String) {
        db.deleteExerciseByDate(date)
    }

    override suspend fun updateExerciseSchedule(workoutId: Long, dateString: String) {
        db.updateExerciseSchedule(workoutId, dateString)
    }

    override suspend fun deleteExercise(exercise: SchenduleExerciseEntity) {
        db.deleteExercise(exercise)
    }

//    companion object {
//        @Volatile
//        private var instance: SchenduleExerciseRepository? = null
//        private const val PAGE_SIZE = 10
//
//        fun getInstance(context: Context): SchenduleExerciseRepository? {
//
//            return instance ?: synchronized(SchenduleExerciseRepository::class.java) {
//                if (instance == null) {
//                    val database = FitmateDatabase.getInstance(context)
//                    instance = DataRepository(database.courseDao())
//                }
//                return instance
//            }
//        }
//    }


}