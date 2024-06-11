package com.rifqi.fitmate.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifqi.fitmate.data.remote.model.ExerciseResponse
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) : ViewModel()  {

    private val _exercise: MutableStateFlow<UiState<ExerciseResponse>> = MutableStateFlow(
        UiState.Loading)
    val exercise: StateFlow<UiState<ExerciseResponse>>
        get() = _exercise


    fun getExercise(query : String) {

        viewModelScope.launch {

                exerciseRepository.searchExercise(query).catch { exception ->
                    _exercise.value = UiState.Error(exception.message.orEmpty())

                }.collect { exercises ->
                    _exercise.value = exercises
                }


        }
    }

    fun getTopExercise() {
        viewModelScope.launch {
            exerciseRepository.getTopExercise(20).catch { exception ->
                _exercise.value = UiState.Error(exception.message.orEmpty())

            }.collect { exercises ->
                _exercise.value = exercises
            }
        }
    }
}