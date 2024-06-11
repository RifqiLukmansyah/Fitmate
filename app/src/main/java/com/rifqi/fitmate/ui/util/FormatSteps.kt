package com.rifqi.fitmate.ui.util

fun formatSteps(steps: Array<String>): String {
    val formattedSteps = StringBuilder()

    for ((index, step) in steps.withIndex()) {
        formattedSteps.append("${index + 1}. $step\n")
    }

    return formattedSteps.toString().trimEnd()
}
