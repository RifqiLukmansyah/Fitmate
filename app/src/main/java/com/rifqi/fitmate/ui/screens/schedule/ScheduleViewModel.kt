package com.rifqi.fitmate.ui.screens.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifqi.fitmate.data.model.ScheduleExercise
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.repository.ScheduleExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleExerciseRepository: ScheduleExerciseRepository
) : ViewModel() {
    private val _scheduleState: MutableStateFlow<UiState<List<ScheduleExercise>>> =
        MutableStateFlow(UiState.Loading)
    val scheduleState: StateFlow<UiState<List<ScheduleExercise>>>
        get() = _scheduleState  .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )



    fun fetchAllScheduleWorkout() {
        viewModelScope.launch {
            scheduleExerciseRepository.getAllSchedule().catch {exception ->
                _scheduleState.value = UiState.Error(exception.message.orEmpty())

            }.collect{ schedule ->
                _scheduleState.value = UiState.Success(schedule)

            }
        }
    }


}