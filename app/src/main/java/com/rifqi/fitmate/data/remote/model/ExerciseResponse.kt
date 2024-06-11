package com.rifqi.fitmate.data.remote.model

import com.google.gson.annotations.SerializedName

data class ExerciseResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<ExerciseData?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)


data class ExerciseData(

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("level")
	val level: Int? = null,

	@field:SerializedName("required_equipment")
	val requiredEquipment: Int? = null,

	@field:SerializedName("muscle")
	val muscle: Muscle? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("cal_estimation")
	val calEstimation: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("gif_url")
	val gifUrl: String? = null,

	@field:SerializedName("is_support_interactive")
	val isSupportInteractive: Int? = null
)
