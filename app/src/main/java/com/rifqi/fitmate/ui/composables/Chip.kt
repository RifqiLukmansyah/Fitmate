package com.rifqi.fitmate.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rifqi.fitmate.ui.theme.neutral30


@Composable
fun Chip(
    id: Int,
    value: String,
    isActive: Boolean,
    onChipClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isActive) Color(0xFF66EFE0) else neutral30
    val contentColor = if (isActive) Color(0xFF66EFE0) else Color(0xFFECECEC)

    Card(

        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.copy(alpha = 0.1F),
            contentColor = contentColor,

            ),
        shape = RoundedCornerShape(50.dp),
        modifier = modifier.clickable {
            onChipClick(id)
        }
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,


                )
        }


    }
}
