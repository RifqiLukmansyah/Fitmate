package com.rifqi.fitmate.ui.screens.detailworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifqi.fitmate.data.local.entity.SchenduleExerciseEntity
import com.rifqi.fitmate.data.remote.model.DetailExerciseRespone
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.repository.ExerciseRepository
import com.rifqi.fitmate.repository.SchenduleExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailWorkoutViewModel @Inject constructor(
    private val repository: ExerciseRepository,
    private val schenduleExerciseRepository: SchenduleExerciseRepository

) : ViewModel() {

    private val _exercise: MutableStateFlow<UiState<DetailExerciseRespone>> =
        MutableStateFlow(UiState.Loading)
    val exercise: StateFlow<UiState<DetailExerciseRespone>>
        get() = _exercise



    fun getWorkoutById(workoutId: Long) {
        viewModelScope.launch {
            repository.getWorkoutById(workoutId).catch { exception ->
                _exercise.value = UiState.Error(exception.message.orEmpty())
            }.collect{ exercise ->
                _exercise.value = exercise
            }
        }
    }



    fun addWorkoutSchendule(exerciseSchendule : SchenduleExerciseEntity) : String {
        var  message = ""
        viewModelScope.launch {

            val isAlreadyExist = schenduleExerciseRepository.isExerciseAlreadyExist(exerciseSchendule.dateString,exerciseSchendule.id_exercise)
            if (isAlreadyExist.isNotEmpty()) {
                message = "Exercise is already scheduled for this date."
            } else {
                message= "Exercise scheduled successfully."
                schenduleExerciseRepository.insertSchendule(exerciseSchendule)
            }


        }

        return message

    }

}