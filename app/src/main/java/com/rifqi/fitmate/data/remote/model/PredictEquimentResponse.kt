package com.rifqi.fitmate.data.remote.model

import com.google.gson.annotations.SerializedName

data class PredictEquimentResponse(

	@field:SerializedName("data")
	val data: PredictionData? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class Muscle(

	@field:SerializedName("name")
	val name: String? = null
)

data class Category(

	@field:SerializedName("name")
	val name: String? = null
)

data class RequiredEquipment(

	@field:SerializedName("name")
	val name: String? = null
)

data class ExerciseParent(

	@field:SerializedName("data")
	val data: ExerciseApi? = null
)

data  class ExerciseApi(
	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("level")
	val level: Int? = null,

	@field:SerializedName("required_equipment")
	val requiredEquipment: RequiredEquipment? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("cal_estimation")
	val calEstimation: Int? = null,

	@field:SerializedName("gif_url")
	val gifUrl: String? = null,

	@field:SerializedName("is_support_interactive")
	val isSupportInteractive: Int? = null,

	@field:SerializedName("muscle")
	val muscle: Muscle? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("step")
	val step: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("category")
	val category: Category? = null
)
data class PredictionData(

	@field:SerializedName("confidence")
	val confidence: Double? = null,

	@field:SerializedName("exercise")
	val exercise: ExerciseParent? = null,

	@field:SerializedName("gym_equipment_prediction")
	val gymEquipmentPrediction: String? = null,


)

data class Status(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
