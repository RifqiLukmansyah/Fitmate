package com.rifqi.fitmate.ui.screens.explore

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.ui.composables.ExerciseGridCard
import com.rifqi.fitmate.ui.composables.skeleton.SkeletonGridList

@Composable
fun ExploreScreen(
    searchQuery: String,
    navigateToDetail: (Long) -> Unit,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    Log.d("query" , searchQuery)
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if(searchQuery != "{searchQuery}") {
                Text("You search $searchQuery")


            }
            viewModel.exercise.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {

                        SkeletonGridList()
                        if(searchQuery == "{searchQuery}") {
                            viewModel.getTopExercise()

                        }  else{
                            viewModel.getExercise(searchQuery)

                        }
                    }

                    is UiState.Success -> {

                        if (uiState.data.data?.isEmpty() == true) {
                            Text(stringResource(id = R.string.empty_exercise_message))
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(155.dp),
                                contentPadding = PaddingValues(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.height(800.dp)
                            ) {
                                items(
                                    uiState.data.data.orEmpty(),
                                    key = { it?.id ?: 0 }) { exercise ->
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