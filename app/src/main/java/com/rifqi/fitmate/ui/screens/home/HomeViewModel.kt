package com.rifqi.fitmate.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifqi.fitmate.data.local.dao.TodayExerciseSummary
import com.rifqi.fitmate.data.remote.model.ExerciseResponse
import com.rifqi.fitmate.data.remote.model.MuscleResponse
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.repository.ExerciseRepository
import com.rifqi.fitmate.repository.SchenduleExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val exerciseRepository: ExerciseRepository ,     private val schenduleExerciseRepository: SchenduleExerciseRepository
) : ViewModel() {
    private val _exercisePopular: MutableStateFlow<UiState<ExerciseResponse>> = MutableStateFlow(UiState.Loading)
    val exercisePopular: StateFlow<UiState<ExerciseResponse>>
        get() = _exercisePopular


    private val _discoverExerciseState: MutableStateFlow<UiState<ExerciseResponse>> = MutableStateFlow(UiState.Loading)
    val discoverExerciseState: StateFlow<UiState<ExerciseResponse>>
        get() = _discoverExerciseState


    private val _muscleTargetState : MutableStateFlow<UiState<MuscleResponse>> = MutableStateFlow(UiState.Loading)
    val muscleTargetState : StateFlow<UiState<MuscleResponse>>
        get() = _muscleTargetState


    private val _activeMuscleId: MutableStateFlow<Int?> = MutableStateFlow(1)
    val activeMuscleId: StateFlow<Int?>
        get() = _activeMuscleId


    private val _todaySchedule : MutableStateFlow<UiState<TodayExerciseSummary>> = MutableStateFlow(UiState.Loading)

    val todaySchedule :  StateFlow<UiState<TodayExerciseSummary>>
        get() = _todaySchedule  .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun fetchPopularWorkout() {
        viewModelScope.launch {
            exerciseRepository.getTopExercise().catch { exception ->
                _exercisePopular.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercises ->
                _exercisePopular.value = exercises
            }
        }
    }

    fun fetchWorkoutListByIdMuscle(muscleId : Int) {
        viewModelScope.launch {
            exerciseRepository.getWorkoutByIdMuscle(muscleId).catch { exception ->
                _discoverExerciseState.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercise ->
                _discoverExerciseState.value = exercise
            }
        }
    }


    fun getTodaySchedule() {
        viewModelScope.launch {
            schenduleExerciseRepository.getTodayExerciseSummary().catch { exception ->
                _todaySchedule.value = UiState.Error(exception.message.orEmpty())
            }.collect { schendule ->
                if (schendule != null) {
                    _todaySchedule.value = UiState.Success(schendule)
                } else {
                    // Handle the case when schendule is null
                    _todaySchedule.value = UiState.Success(TodayExerciseSummary("N/A", "",0, 0, 0))
                }
            }
        }
    }

    fun fetchListMuscle() {

        viewModelScope.launch {

            exerciseRepository.getAllMuscleCategory().catch { exception ->
                _muscleTargetState.value = UiState.Error(exception.message.orEmpty())
            }.collect { muscle ->
                _muscleTargetState.value = muscle
            }
        }
    }
    fun setActiveMuscleId(muscleId: Int) {
        _discoverExerciseState.value = UiState.Loading

        _activeMuscleId.value = muscleId

    }

}