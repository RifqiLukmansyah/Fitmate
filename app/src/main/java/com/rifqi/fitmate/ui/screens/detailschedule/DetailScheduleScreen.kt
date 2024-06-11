package com.rifqi.fitmate.ui.screens.detailschedule

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.ui.composables.ExerciseItemSchedule
import com.rifqi.fitmate.ui.theme.alertColor
import com.rifqi.fitmate.ui.theme.doneColor
import com.rifqi.fitmate.ui.theme.neutral10
import com.rifqi.fitmate.ui.theme.neutral30
import com.rifqi.fitmate.ui.theme.neutral80


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScheduleScreen(
    workoutdate: String,
    navigateBack: () -> Unit,
    navigateToDetailSchedule: (workoutId: Long) -> Unit,
    detailScheduleViewModel: DetailScheduleViewModel = hiltViewModel()

) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back to home screen"
                )
            }
            Text(workoutdate)
            Surface(
                color = neutral80,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.clickable {


                }
            ) {

                IconButton(onClick = {

                    detailScheduleViewModel.deleteScheduleByDate(workoutdate)
                    navigateBack()
                    Toast.makeText(
                        context,
                        "Exercise scheduled successfully deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        tint = neutral10,

                        contentDescription = "Delete this activity"
                    )
                }


            }
        }

        Box(modifier = Modifier.padding(10.dp)) {
            detailScheduleViewModel.exerciseState.collectAsState().value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        Text(stringResource(id = R.string.loading_message))
                        detailScheduleViewModel.fetchExerciseByDate(workoutdate)
                    }

                    is UiState.Success -> {
                        if (uiState.data.isEmpty()) {
                            Text(stringResource(id = R.string.empty_exercise_message))
                        } else {
                            LazyColumn(
                                state = rememberLazyListState(),
                            ) {
                                items(uiState.data, key = { it.id }) { exercise ->


                                    val state = rememberDismissState(
                                        confirmValueChange = {
                                            if (it == DismissValue.DismissedToStart ) {
                                                detailScheduleViewModel.deleteExercise(exercise)

                                                if(uiState.data.size == 1 ){
                                                    navigateBack()
                                                }



                                            }else if(it == DismissValue.DismissedToEnd) {
                                                detailScheduleViewModel.updateExerciseSchedule(exercise.id_exercise,exercise.dateString)

                                            }

                                            true
                                        }
                                    )

                                    SwipeToDismiss(

                                        state = state,
                                        background = {
                                            val color = when (state.dismissDirection) {
                                                DismissDirection.EndToStart -> alertColor
                                                DismissDirection.StartToEnd ->  doneColor
                                                null -> Color.Transparent
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .background(color)
                                                    .fillMaxSize()
                                                    .padding(10.dp)

                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete",
                                                    modifier = Modifier.align(Alignment.CenterEnd)
                                                )
                                                Icon(
                                                    imageVector = Icons.Default.Done,
                                                    contentDescription = "Done",
                                                    modifier = Modifier.align(Alignment.CenterStart)
                                                )
                                            }

                                        },
                                        dismissContent = {
                                            ExerciseItemSchedule(
                                                exercise = exercise,
                                                navigateToDetailSchedule = { id ->
                                                    navigateToDetailSchedule(id)
                                                })
                                        })

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
        Box(
            modifier = Modifier.fillMaxHeight().padding(16.dp),
            contentAlignment =  Alignment.BottomEnd
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(imageVector = Icons.Filled.Info , contentDescription = null , tint = neutral30)
                Text(
                    "You can remove items by swiping left and marking the exercise as done by swiping right." , style = MaterialTheme.typography.bodyMedium.copy(
                        textAlign = TextAlign.Start,
                        color = neutral30
                    ))
            }
        }

    }

}