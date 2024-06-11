package com.rifqi.fitmate.data.remote.retrofit

import com.rifqi.fitmate.data.remote.model.DetailExerciseRespone
import com.rifqi.fitmate.data.remote.model.ExerciseResponse
import com.rifqi.fitmate.data.remote.model.MuscleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApiService {

    @GET("/getMuscle")
    suspend fun getMuscleList(

    ): MuscleResponse


    @GET("/getExercise")
    suspend fun getExerciseList(
        @Query("muscle_id") id: Int
    ): ExerciseResponse

    @GET("/getDetailExercise")
    suspend fun getDetailExercise(
        @Query("exercise_id") id: Int
    ): DetailExerciseRespone

    @GET("/getTopRatedExercise")
    suspend fun getTopRatedExercise(
        @Query("limit") id: Int = 5
    ): ExerciseResponse

    @GET("/getExerciseByQuery")
    suspend fun searchExercise(
        @Query("query") query: String
    ): ExerciseResponse
}