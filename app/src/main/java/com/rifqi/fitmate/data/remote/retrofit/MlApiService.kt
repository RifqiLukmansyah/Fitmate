package com.rifqi.fitmate.data.remote.retrofit

import com.rifqi.fitmate.data.remote.model.PredictEquimentResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MlApiService {

    @Multipart
    @POST("/prediction")
    suspend fun predictImage(
        @Part image: MultipartBody.Part,
    ): PredictEquimentResponse
}