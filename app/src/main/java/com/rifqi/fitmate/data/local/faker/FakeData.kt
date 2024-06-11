package com.rifqi.fitmate.data.local.faker

import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.model.BodyPartSegmentValue
import com.rifqi.fitmate.data.model.Category
import com.rifqi.fitmate.data.model.Muscle
import com.rifqi.fitmate.data.model.Exercise
import com.rifqi.fitmate.data.model.InteractiveExerciseSetting

object FakeData {

    private val fakeMuscleData = listOf(
        Muscle(id = 1, "Chest"),
        Muscle(id = 2, "Full Body"),
        Muscle(id = 3, "Legs"),
        Muscle(id = 4, "Arms"),
        Muscle(id = 5, "Back"),

        )
    private val fakeCategory = listOf(
        Category("Strength"),
        Category("Cardio"),


        )
    val fakeExerciseData = listOf(
        Exercise(
            id = 1,
            name = "Dumbbell Curl",
            rating = 99,
            level = 1,
            calEstimation = 150,
            requiredEquiment = true,
            explain = "The dumbbell curl is a bicep exercise that targets the muscles in your arms.",
            step = arrayOf(
                "Stand with your feet shoulder-width apart, holding a dumbbell in each hand with your arms fully extended and palms facing forward.",
                "Keep your upper arms stationary and exhale as you curl the weights while contracting your biceps.",
                "Inhale and slowly begin to lower the dumbbells back to the starting position."
            ),
            isSupportInteractive = true,
            interactiveSetting = InteractiveExerciseSetting(
                repetion = 12,
                set = 3,
                RestInterval = 60
            ),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 10.0,
                leftArm = 10.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            bodyPartNeeded = arrayOf("right_hand" , "left_hand" ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[3],
            photo = R.drawable.dumbbel_bicep_cover,
            Gif = R.drawable.dumbbel_bicep
        ),
        Exercise(
            id = 2,

            name = "Push-ups",
            rating = 97,
            level = 2,
            calEstimation = 55,
            requiredEquiment = false,
            interactiveSetting = InteractiveExerciseSetting(
                repetion = 6,
                set = 3,
                RestInterval = 60

            ),
            isSupportInteractive = true,
            bodyPartNeeded = arrayOf("right_hand" , "left_hand" ),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 55.0,
                leftArm = 49.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "Bodyweight exercise targeting the chest, shoulders, and triceps.",
            step = arrayOf(
                "Start in a plank position.",
                "Lower your body until your chest nearly touches the floor.",
                " Push back up to the starting position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[3],
            photo = R.drawable.push_up,
            Gif = R.drawable.push_up_model
        ),
        Exercise(
            id = 3,
            name = "Dumbbell Sumo Squat ",
            level = 3,
            rating = 97,
            calEstimation = 150,
            requiredEquiment = false,
            interactiveSetting = InteractiveExerciseSetting(
                repetion = 10,
                set = 3,
                RestInterval = 60

            ),
            isSupportInteractive = true,
            bodyPartNeeded = arrayOf("right_leg" , "left_leg" ),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 110.6,
                leftLeg= 110.7,
            ),
            explain = "A variation of the traditional squat that targets the inner thighs and glutes.",
            step = arrayOf(
                "Stand with feet wider than shoulder-width, toes pointing out.",
                "Hold a dumbbell with both hands in front of you.",
                "Squat down, keeping your back straight.",
                "Return to the starting position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.sumo_squat_cover,
            Gif = R.drawable.sumo_squat_model
        ),
        Exercise(
            id = 4,
            name = "Dumbbell Biceps Curl on Bosu Ball",
            rating = 96,
            level = 1,
            calEstimation = 180,
            requiredEquiment = true,
            isSupportInteractive = true,
            interactiveSetting = InteractiveExerciseSetting(
                repetion = 14,
                set = 3,
                RestInterval = 60
            ),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 10.0,
                leftArm = 10.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            bodyPartNeeded = arrayOf("right_hand" , "left_hand" ),
            explain = "An exercise that combines core stabilization with bicep strengthening.",
            step = arrayOf(
                "Sit on a Bosu ball in a V-sit position.",
                "Perform bicep curls while maintaining balance.",
                "Repeat"
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[3],
            photo = R.drawable.dumbbel_bicep_cover,
            Gif = R.drawable.biceps_curl_ball_model
        ),


        Exercise(
            id = 5,
            name = "Dumbbell Goblet Wall Sit",
            rating = 95,
            level = 3,
            calEstimation = 40,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "A lower body exercise that improves endurance and strength in the legs.",
            step = arrayOf(
                "Stand with your back against a wall.",
                "Hold a dumbbell at chest level.",
                "Slide down the wall into a sitting position.",
                "Hold the position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 6,
            name = "Assault Bike Run",
            rating = 88,
            level = 1,
            calEstimation = 180,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "An intense cardio workout on the assault bike, engaging both upper and lower body.",
            step = arrayOf(
                "Sit on the assault bike.",
                "Grab the handles.",
                "Start pedaling and pushing/pulling the handles.",
                "Hold the position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 7,
            name = " Bench Press Conventional Grip",
            rating = 85,
            level = 3,
            calEstimation = 50,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = " bench press variation that starts from a dead stop, targeting the chest, shoulders, and triceps.",
            step = arrayOf(
                "Lie on a bench with the barbell resting on safety pins at chest level.",
                "Grip the barbell with a conventional grip.",
                "Press the barbell up until arms are fully extended.",
                "Lower the barbell back to the pins."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 8,
            name = "  Dumbell Bulgarian Split Squat ",
            rating = 82,
            level = 3,
            calEstimation = 50,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = " A challenging variation of the Bulgarian split squat that increases range of motion and targets the lower body.",
            step = arrayOf(
                "Place one foot on an elevated surface behind you.",
                "Hold a dumbbell in each hand.",
                "Squat down on the front leg, keeping your torso upright.",
                "Push back up to the starting position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 9,
            name = "Dumbbell Incline Triceps Kickback",
            rating = 82,
            level = 3,
            calEstimation = 14,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "Targets the triceps muscles by extending the arms on an incline bench.",
            step = arrayOf(
                "Lean forward on an incline bench.",
                "Hold a dumbbell in each hand.",
                "Extend your arms backward, focusing on the triceps.",

            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 10,
            name = "Smith Sumo Chair Squat",
            rating = 77,
            level = 3,
            calEstimation = 50,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = "A squat variation using the Smith machine for stability, targeting the thighs and glutes.",
            step = arrayOf(
                "Stand with feet wider than shoulder-width, toes out.",
                "Position yourself under the Smith machine bar.",
                "Squat down as if sitting in a chair.",

                ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
        Exercise(
            id = 11,
            name = "Walking on Elliptical Machine",
            rating = 88,
            level = 2,
            calEstimation = 100,
            requiredEquiment = true,
            isSupportInteractive = false,
            bodyPartNeeded = arrayOf(""),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 0.0,
                leftArm = 0.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            explain = " A low-impact cardio exercise on the elliptical machine, ideal for overall fitness and endurance.",
            step = arrayOf(
                "Stand on the elliptical machine.",
                "Hold onto the handles.",
                "Start walking, adjusting the resistance as needed.",
                ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.dumbbel_goblet_wall_cover,
            Gif = R.drawable.dumbbel_goblet_wall_model
        ),
    )


}