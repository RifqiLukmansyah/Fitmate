package com.rifqi.fitmate.repository

import com.google.gson.Gson
import com.rifqi.fitmate.data.local.faker.FakeData
import com.rifqi.fitmate.data.model.Exercise
import com.rifqi.fitmate.data.remote.model.DetailExerciseRespone
import com.rifqi.fitmate.data.remote.model.ExerciseResponse
import com.rifqi.fitmate.data.remote.model.MuscleResponse
import com.rifqi.fitmate.data.remote.retrofit.ExerciseApiService
import com.rifqi.fitmate.data.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val exerciseApiService: ExerciseApiService){

    private val exercises = mutableListOf<Exercise>()


    init {
        if (exercises.isEmpty()) {
            FakeData.fakeExerciseData.forEach { exercise ->
                exercises.add(
                    Exercise(
                        exercise.id,
                        exercise.name,
                        exercise.rating,
                        exercise.level,
                        exercise.calEstimation,
                        exercise.requiredEquiment,
                        exercise.explain,
                        exercise.step,
                        exercise.category,
                        exercise.isSupportInteractive,
                        exercise.interactiveSetting,
                        exercise.interctiveBodyPartSegmentValue,
                        exercise.bodyPartNeeded,
                        exercise.muscle,
                        exercise.photo,
                        exercise.Gif
                    )
                )
            }
        }

    }



    fun getTopExercise(limit : Int = 5 ): Flow<UiState<ExerciseResponse>>  = flow {
        emit(UiState.Loading)
        try {
            val muscleResponse = exerciseApiService.getTopRatedExercise(limit)
            emit(UiState.Success(muscleResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ExerciseResponse::class.java)
            errorResponse.message?.let { UiState.Error(it) }?.let { emit(it) }
        }

    }

    fun getWorkoutByIdMuscle(muscleId: Int): Flow<UiState<ExerciseResponse>>  = flow  {
        emit(UiState.Loading)
        try {
            val muscleResponse = exerciseApiService.getExerciseList(muscleId)
            emit(UiState.Success(muscleResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ExerciseResponse::class.java)
            errorResponse.message?.let { UiState.Error(it) }?.let { emit(it) }
        }

    }

    fun getAllMuscleCategory(): Flow<UiState<MuscleResponse>> = flow {
        emit(UiState.Loading)
        try{
            val muscleResponse = exerciseApiService.getMuscleList()
            emit(UiState.Success(muscleResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, MuscleResponse::class.java)
            errorResponse.message?.let { UiState.Error(it) }?.let { emit(it) }
        }
    }
    fun getWorkoutById(workoutId: Long): Flow<UiState<DetailExerciseRespone>>  = flow{
        emit(UiState.Loading)
        try{
            val detailExerciseResponse = exerciseApiService.getDetailExercise(workoutId.toInt())
            emit(UiState.Success(detailExerciseResponse))

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, DetailExerciseRespone::class.java)
            errorResponse.message?.let { UiState.Error(it) }?.let { emit(it) }
        }
    }

    fun searchExercise(query : String ) : Flow<UiState<ExerciseResponse>>  = flow{
        emit(UiState.Loading)
        try{
            val detailExerciseResponse = exerciseApiService.searchExercise(query)
            emit(UiState.Success(detailExerciseResponse))

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ExerciseResponse::class.java)
            errorResponse.message?.let { UiState.Error(it) }?.let { emit(it) }
        }
    }
}