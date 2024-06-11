package com.rifqi.fitmate.ml

import android.graphics.Bitmap
import com.rifqi.fitmate.ui.model.Person


interface PoseDetector : AutoCloseable {

    fun estimatePoses(bitmap: Bitmap): List<Person>

    fun lastInferenceTimeNanos(): Long
}
