package com.rifqi.fitmate.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rifqi.fitmate.ui.model.OnboardPage
import com.rifqi.fitmate.ui.theme.neutral30

@Composable
fun OnBoardDetails(
    modifier: Modifier = Modifier, currentPage: OnboardPage
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = currentPage.title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 26.sp,
                lineHeight = 36.sp            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = currentPage.description,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = neutral30,
                fontWeight = FontWeight.Normal
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}