package com.rifqi.fitmate.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListSection(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        SectionText(title, subtitle , modifier.padding(horizontal = 20.dp))
        content()
    }
}

@Composable
fun SectionText(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge

        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall

        )
    }
}