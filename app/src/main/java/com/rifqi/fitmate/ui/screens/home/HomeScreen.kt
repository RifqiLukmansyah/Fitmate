package com.rifqi.fitmate.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.local.dao.TodayExerciseSummary
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.ui.composables.Chip
import com.rifqi.fitmate.ui.composables.ExerciseGridCard
import com.rifqi.fitmate.ui.composables.ExerciseHorizontalCard
import com.rifqi.fitmate.ui.composables.ListSection
import com.rifqi.fitmate.ui.composables.skeleton.SkeletonExerciseHorizontalList
import com.rifqi.fitmate.ui.composables.skeleton.SkeletonGridList
import com.rifqi.fitmate.ui.composables.skeleton.SkeletonMuscleList
import com.rifqi.fitmate.ui.theme.lightblue60
import com.rifqi.fitmate.ui.theme.neutral30

@Composable
fun HomeScreen(
    navigateToDetail: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetailSchedule : (String) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,

    ) {
    val todaySchedule by viewModel.todaySchedule.collectAsState()
    val activeMuscleId by viewModel.activeMuscleId.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item{

            }
            item {
                viewModel.exercisePopular.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {

                            ListSection(
                                title = stringResource(id = R.string.section_favorite_title),
                                subtitle = stringResource(
                                    id = R.string.section_favorite_subtitle
                                )
                            ) {
                                SkeletonExerciseHorizontalList()

                            }
                            viewModel.fetchPopularWorkout()
                        }

                        is UiState.Success -> {

                            if (uiState.data.data?.isEmpty() == true) {
                                Text(stringResource(id = R.string.empty_exercise_message))
                            } else {
                                ListSection(
                                    title = stringResource(id = R.string.section_favorite_title),
                                    subtitle = stringResource(
                                        id = R.string.section_favorite_subtitle
                                    )
                                ) {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                    ) {
                                        items(uiState.data.data.orEmpty(), key = { it?.id ?: 0 }) { exercise ->
                                            if (exercise != null) {
                                                ExerciseHorizontalCard(
                                                    exercise = exercise,
                                                    navigateToDetail = navigateToDetail
                                                )
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

                todaySchedule.let {uiState ->
                    when(uiState) {
                        is UiState.Loading -> {
                            Text("Loading ...")
                            viewModel.getTodaySchedule()
                        }

                        is UiState.Success-> {

                            val exerciseSummary = uiState.data



                            if (exerciseSummary == TodayExerciseSummary("N/A", "",0, 0, 0)) {

                            } else {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = neutral30.copy(alpha = 0.1F),
                                        contentColor = contentColor,

                                        ),
                                    modifier = Modifier.padding(16.dp)
                                ){
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(36.dp),
                                        modifier = Modifier.padding(16.dp)){
                                        Column(modifier = Modifier.weight(2f)){

                                            Row{
                                                Text(exerciseSummary.muscleTarget)

                                            }
                                            val percentageFinished = if (exerciseSummary.total_exercise_today > 0) {
                                                (exerciseSummary.total_exercise_finished.toDouble() / exerciseSummary.total_exercise_today.toDouble() * 100).toInt()
                                            } else {
                                                0
                                            }
                                            Spacer(modifier = modifier.height(12.dp))
                                            Text("$percentageFinished% Complated" , style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 12.sp
                                            ))


                                            // Tampilkan persentase dalam LinearProgressIndicator
                                            LinearProgressIndicator(
                                                progress = percentageFinished / 100f,
                                                trackColor = neutral30,
                                                color = lightblue60,
                                                modifier = Modifier
                                                    .padding(vertical = 8.dp)
                                            )

                                            // Tampilkan persentase sebagai teks

                                        }
                                        Button(

                                            modifier = Modifier.weight(1f),
                                            onClick = { navigateToDetailSchedule(exerciseSummary.dateString)}) {

                                                Icon(Icons.Filled.NavigateNext , contentDescription = null)

                                        }
                                    }
                                }
                            }

                        }
                        is UiState.Error-> {
                            Text("Error")
                        }
                    }
                }

                viewModel.muscleTargetState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            ListSection(
                                title = stringResource(id = R.string.section_discover_title),
                                subtitle = stringResource(
                                    id = R.string.section_discover_subtitle
                                )
                            ) {

                                SkeletonMuscleList()

                            }
                            viewModel.fetchListMuscle()
                        }

                        is UiState.Success -> {

                            if (uiState.data.data?.isEmpty() == true) {
                                Text(stringResource(id = R.string.empty_exercise_message))
                            } else {
                                ListSection(
                                    title = stringResource(id = R.string.section_discover_title),
                                    subtitle = stringResource(
                                        id = R.string.section_discover_subtitle
                                    )
                                ) {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                    ) {

                                        items(
                                            uiState.data.data.orEmpty(),
                                            key = { (it?.id ?: 0) }) { muscle ->
                                            if (muscle?.id != null && muscle.name != null) {
                                                Chip(id = muscle.id,
                                                    value = muscle.name,
                                                    isActive = activeMuscleId == muscle.id,

                                                    onChipClick = {
                                                        viewModel.setActiveMuscleId(
                                                            muscle.id
                                                        )
                                                    })
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

                viewModel.discoverExerciseState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {

                            SkeletonGridList()
                            activeMuscleId?.let { viewModel.fetchWorkoutListByIdMuscle(it) }
                        }

                        is UiState.Success -> {

                            if (uiState.data.data?.isEmpty() == true ) {
                                Text(stringResource(id = R.string.empty_exercise_message))
                            }

                            else {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(155.dp),
                                    contentPadding = PaddingValues(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.height(400.dp)
                                ) {
                                    items(uiState.data.data.orEmpty(), key = { it?.id ?: 0 }) { exercise ->
                                        if (exercise != null) {
                                            ExerciseGridCard(exercise = exercise, navigateToDetail)
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
    }

}

