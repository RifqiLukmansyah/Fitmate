package com.rifqi.fitmate.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseAdditionInformation(
    icon: ImageVector,
    text: String,
    iconTint: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(16.dp).padding(0.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = text ,  style = MaterialTheme.typography.labelSmall.copy(
            color = Color.White,
            fontWeight = FontWeight.Medium
        ))
    }
}
