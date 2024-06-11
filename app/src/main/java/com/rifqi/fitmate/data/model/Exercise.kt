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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Exercise

        if (id != other.id) return false
        if (name != other.name) return false
        if (rating != other.rating) return false
        if (level != other.level) return false
        if (calEstimation != other.calEstimation) return false
        if (requiredEquiment != other.requiredEquiment) return false
        if (explain != other.explain) return false
        if (!step.contentEquals(other.step)) return false
        if (category != other.category) return false
        if (isSupportInteractive != other.isSupportInteractive) return false
        if (interactiveSetting != other.interactiveSetting) return false
        if (interctiveBodyPartSegmentValue != other.interctiveBodyPartSegmentValue) return false
        if (!bodyPartNeeded.contentEquals(other.bodyPartNeeded)) return false
        if (muscle != other.muscle) return false
        if (photo != other.photo) return false
        if (Gif != other.Gif) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + rating
        result = 31 * result + level
        result = 31 * result + calEstimation
        result = 31 * result + requiredEquiment.hashCode()
        result = 31 * result + explain.hashCode()
        result = 31 * result + step.contentHashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + isSupportInteractive.hashCode()
        result = 31 * result + interactiveSetting.hashCode()
        result = 31 * result + interctiveBodyPartSegmentValue.hashCode()
        result = 31 * result + bodyPartNeeded.contentHashCode()
        result = 31 * result + muscle.hashCode()
        result = 31 * result + photo
        result = 31 * result + Gif
        return result
    }
}

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