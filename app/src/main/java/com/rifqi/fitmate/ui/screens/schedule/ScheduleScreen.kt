package com.rifqi.fitmate.ui.screens.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.ui.theme.lightblue120
import com.rifqi.fitmate.ui.theme.lightblue60
import com.rifqi.fitmate.ui.theme.neutral10
import com.rifqi.fitmate.ui.theme.neutral80
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ScheduleScreen(
    navigateToDetail : (String) -> Unit,
    navigateToScheduleSetting : () -> Unit,
    scheduleViewModel: ScheduleViewModel = hiltViewModel()
) {


    Box(modifier = Modifier.padding(10.dp)) {
        scheduleViewModel.scheduleState.collectAsState().value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    Text(stringResource(id = R.string.loading_message))
                    scheduleViewModel.fetchAllScheduleWorkout()
                }
                is UiState.Success -> {
                    if (uiState.data.isEmpty()) {
                        Column ( horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center , modifier = Modifier.fillMaxSize()){
                            Image(painter = painterResource(id = R.drawable.onboarding_2) , contentDescription = null)
                            Text(stringResource(id = R.string.empty_schedule) , style = MaterialTheme.typography.bodyMedium.copy(
                                textAlign = TextAlign.Center
                            ))
                        }
                    } else {
                        LazyColumn {

                            item{
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ){

                                    Text(" Exercise Schedule")
                                    IconButton(onClick = navigateToScheduleSetting) {
                                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Setting Notification")
                                    }
                                }


                            }
                            items(uiState.data, key = { it.id }) { schedule ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = neutral80
                                    ),
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxSize()
                                        .clickable {
                                            navigateToDetail(schedule.dateString)
                                        },

                                    ) {
                                    Row {
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    lightblue120,
                                                )
                                                .width(64.dp)
                                                .padding(16.dp)

                                        ) {
                                            val date = SimpleDateFormat(
                                                "yyyy-MM-dd",
                                                Locale.getDefault()
                                            ).parse(schedule.dateString)
                                            val dayOfMonth =
                                                SimpleDateFormat("dd", Locale.getDefault()).format(
                                                    date!!
                                                )
                                            val month =
                                                SimpleDateFormat("MMM", Locale.getDefault()).format(
                                                    date
                                                )
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,

                                                ) {


                                                Text(
                                                    text = dayOfMonth,
                                                    style = MaterialTheme.typography.bodyLarge.copy(
                                                        color = lightblue60,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                )
                                                Text(
                                                    text = month,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        color = lightblue60
                                                    )
                                                )
                                            }

                                        }
                                        Column(
                                            verticalArrangement = Arrangement.SpaceBetween,

                                            modifier = Modifier.padding(10.dp)
                                        ) {


                                            Text(
                                                schedule.muscleTarget,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = neutral10,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp,

                                                    ),
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(26.dp)
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Light,

                                                        contentDescription = null,
                                                        tint = lightblue60
                                                    )
                                                    Text(
                                                        text = stringResource(
                                                            R.string.total_exercise_count,
                                                            schedule.exerciseCount
                                                        ),
                                                        style = MaterialTheme.typography.bodySmall.copy(
                                                            color = neutral10
                                                        )
                                                    )
                                                }
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.LocalFireDepartment,

                                                        contentDescription = null,
                                                        tint = lightblue60
                                                    )
                                                    Text(
                                                        text = stringResource(
                                                            R.string.calori_format,
                                                            schedule.totalCalories.toInt()
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
                    }

                }

                is UiState.Error -> {
                    Text(stringResource(id = R.string.error_message))
                }
            }


        }
    }
}