package com.rifqi.fitmate.data.model

import androidx.annotation.DrawableRes


data class Exercise(
    val id : Long,
    val name : String,
    val rating : Int,
    val level : Int,
    val calEstimation : Int,
    val requiredEquiment : Boolean,
    val explain : String,
    val step : Array<String>,
    val category : Category,
    val isSupportInteractive : Boolean = false,
    val interactiveSetting : InteractiveExerciseSetting = InteractiveExerciseSetting(
        repetion =  0,
        set = 0,
        RestInterval = 0
    ),
    val interctiveBodyPartSegmentValue: BodyPartSegmentValue = BodyPartSegmentValue(0.0,0.0,0.0,0.0),
    val bodyPartNeeded : Array<String> = arrayOf(""),
    val muscle : Muscle,
    @DrawableRes val photo : Int,
    @DrawableRes val Gif : Int,
)

data class BodyPartSegmentValue(
    val rightArm : Double,
    val leftArm : Double,
    val rightLeg : Double,
    val leftLeg : Double,
)


data class InteractiveExerciseSetting(
    val repetion : Int,
    val set : Int,
    val RestInterval : Long,
)