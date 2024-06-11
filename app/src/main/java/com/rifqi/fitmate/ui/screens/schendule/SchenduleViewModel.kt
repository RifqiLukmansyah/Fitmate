package com.rifqi.fitmate.ui.screens.schendule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifqi.fitmate.data.model.SchenduleExercise
import com.rifqi.fitmate.data.util.UiState
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
class SchenduleViewModel @Inject constructor(
    private val schenduleExerciseRepository: SchenduleExerciseRepository
) : ViewModel() {
    private val _schenduleState: MutableStateFlow<UiState<List<SchenduleExercise>>> =
        MutableStateFlow(UiState.Loading)
    val schenduleState: StateFlow<UiState<List<SchenduleExercise>>>
        get() = _schenduleState  .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )



    fun fetchAllSchenduleWorkout() {
        viewModelScope.launch {
            schenduleExerciseRepository.getAllSchendule().catch {exception ->
                _schenduleState.value = UiState.Error(exception.message.orEmpty())

            }.collect{ schendule ->
                _schenduleState.value = UiState.Success(schendule)

            }
        }
    }


}