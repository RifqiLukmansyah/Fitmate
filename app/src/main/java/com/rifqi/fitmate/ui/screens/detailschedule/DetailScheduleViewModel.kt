package com.rifqi.fitmate.ui.screens.detailschedule
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifqi.fitmate.data.local.entity.ScheduleExerciseEntity
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
class DetailScheduleViewModel @Inject constructor(
    private val schenduleExerciseRepository: ScheduleExerciseRepository
) : ViewModel() {

    private val _exerciseState: MutableStateFlow<UiState<List<ScheduleExerciseEntity>>> =
        MutableStateFlow(UiState.Loading)
    val exerciseState: StateFlow<UiState<List<ScheduleExerciseEntity>>>
        get() = _exerciseState  .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )


    fun fetchExerciseByDate(date : String) {
        viewModelScope.launch {
            schenduleExerciseRepository.getAllExerciseByDate(date).catch {exception ->
                _exerciseState.value = UiState.Error(exception.message.orEmpty())
            }.collect{ schendule ->
                _exerciseState.value = UiState.Success(schendule)

            }
        }
    }

    fun updateExerciseSchedule(workoutId: Long, dateString: String) {
        viewModelScope.launch {
            _exerciseState.value = UiState.Loading

            schenduleExerciseRepository.updateExerciseSchedule(workoutId, dateString)
        }
    }
    fun deleteExercise(exerciseSchendule: ScheduleExerciseEntity) {
        viewModelScope.launch {
            _exerciseState.value = UiState.Loading
            schenduleExerciseRepository.deleteExercise(exerciseSchendule)
        }
    }
    fun deleteScheduleByDate(date : String) {
        viewModelScope.launch {
            schenduleExerciseRepository.deleteScheduleByDate(date)
        }
    }


}