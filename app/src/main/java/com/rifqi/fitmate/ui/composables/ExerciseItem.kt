package com.rifqi.fitmate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.remote.model.ExerciseApi
import com.rifqi.fitmate.ui.theme.lightblue60
import com.rifqi.fitmate.ui.theme.neutral10
import com.rifqi.fitmate.ui.theme.neutral80


@Composable
fun ExerciseItem(
    exercise: ExerciseApi,
    navigateToDetailSchedule: (Long) -> Unit

) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = neutral80
        ),
        modifier = Modifier

            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                exercise.id?.let { navigateToDetailSchedule(it.toLong()) }
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        neutral10,
                    )
                    .width(64.dp)
                    .fillMaxHeight()

            ) {
                // image
                exercise.gifUrl?.let { GifImageApi(70.dp, it) }
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,

                modifier = Modifier.padding(10.dp)
            ) {


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {

                        exercise.name?.let {
                            Text(
                                it,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = neutral10,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                ),
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                9.dp
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocalFireDepartment,

                                contentDescription = null,
                                tint = lightblue60
                            )
                            Text(
                                text = stringResource(
                                    R.string.calori_format,
                                    exercise.calEstimation ?: ""
                                ),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = neutral10
                                )
                            )
                        }
                    }
                }

            }
        }


    }
}