package com.rifqi.fitmate.ui.model

import android.graphics.PointF

data class KeyPoint(val bodyPart: BodyPart, var coordinate: PointF, val score: Float)
