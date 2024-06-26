package com.rifqi.fitmate.ui.screens.interactivelearn

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rifqi.fitmate.data.remote.model.DetailExerciseRespone
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.repository.ExerciseRepository
import com.rifqi.fitmate.repository.ScheduleExerciseRepository
import com.rifqi.fitmate.ui.state.InteractiveUiState
import com.rifqi.fitmate.ui.util.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InteractiveLearnViewModel  @Inject constructor(    private val repository: ExerciseRepository,
                                    private val scheduleExerciseRepository: ScheduleExerciseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InteractiveUiState())
    val uiState: StateFlow<InteractiveUiState> = _uiState.asStateFlow()

    private val _exercise: MutableStateFlow<UiState<DetailExerciseRespone>> =
        MutableStateFlow(UiState.Loading)

    private var timer: CountDownTimer? = null
    private val initialTime = MutableLiveData<Long>()
    private val currentTime = MutableLiveData<Long>()
    val exercise: StateFlow<UiState<DetailExerciseRespone>>
        get() = _exercise

    var currentTimeString by mutableStateOf("")


    private val _onCounting: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val onCounting: StateFlow<Boolean>
        get() = _onCounting


    private val _maxRepetition: MutableStateFlow<Int> = MutableStateFlow(0)
    val maxRepetition: StateFlow<Int>
        get() = _maxRepetition

    private val _maxSet: MutableStateFlow<Int> = MutableStateFlow(0)
    val maxSet: StateFlow<Int>
        get() = _maxSet

    private val _eventCountDownFinish = MutableLiveData<Boolean>()

    fun initialateCounter(exerciseId : Long? , repetition : Int? , set : Int? ) {

        if(exerciseId != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    exerciseId = exerciseId
                )


            }

        }

        if (repetition != null && set != null) {
            _maxRepetition.value = repetition
            _maxSet.value = set

        }
    }


    fun getWorkoutById(workoutId: Long) {
        viewModelScope.launch {
            repository.getWorkoutById(workoutId).catch { exception ->
                _exercise.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercise ->


                _exercise.value = exercise

            }

        }
    }


    private fun updateTimeString(millisUntilFinished: Long) {
        currentTimeString = DateUtils.formatElapsedTime(millisUntilFinished / 1000)
    }

    fun startTimer(time: Long = 10) {
        timer?.start()

        val initialTimeMillis = time * 1000
        initialTime.value = initialTimeMillis
        currentTime.value = initialTimeMillis

        timer = object : CountDownTimer(initialTimeMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                currentTime.value = millisUntilFinished
                updateTimeString(millisUntilFinished)
                _onCounting.value = true
            }

            override fun onFinish() {
                resetTimer()


                playExercise()


            }
        }
    }

    fun resetTimer() {
        timer?.cancel()
        currentTime.value = initialTime.value
        _eventCountDownFinish.value = true
        _onCounting.value = false


    }

    fun nextStepTutotrial(){
        if(_uiState.value.tutorialStep != 3){
            _uiState.update { currentState ->
                currentState.copy(
                    tutorialStep = currentState.tutorialStep +1
                )

            }
        }else{
            _uiState.update { currentState ->
                currentState.copy(
                    isTutorialScreen = false,
                    isInRestMode = false,
                )

            }
        }
    }
    fun playExercise() {
        _uiState.update { currentState ->
            currentState.copy(
                isTutorialScreen = false,
                isInRestMode = false,
            )

        }
    }

    private fun stopExercise() {
        val currentTimeMillis = System.currentTimeMillis()
        val currentDate = formatDate(currentTimeMillis)
        _uiState.update { currentState ->
            currentState.copy(
                counter = 0,
                isFinished = true,
                currentSet = currentState.currentSet +1,
                isTutorialScreen = false,
                isInRestMode = true,
            )

        }
        updateExerciseSchedule(_uiState.value.exerciseId,currentDate )
    }

    fun increaseCount() {

        if (_uiState.value.counter == maxRepetition.value - 1) {
            if (uiState.value.currentSet != maxSet.value - 1) {
                _uiState.update { currentState ->
                    currentState.copy(
                        counter = 0,
                        currentSet = currentState.currentSet + 1,
                        isInRestMode = true
                    )
                }
            } else {
                stopExercise()
            }
            if (_uiState.value.counter == 0 && _uiState.value.isInRestMode && !_uiState.value.isFinished) {
                startTimer(50)
            }
        } else {
            _uiState.update { currentState ->
                if (!currentState.isTutorialScreen) {
                    currentState.copy(
                        counter = currentState.counter + 1
                    )
                } else {
                    currentState.copy(
                        counter = 0
                    )
                }


            }
        }


    }
    private fun updateExerciseSchedule(workoutId: Long, dateString: String) {
        viewModelScope.launch {
            scheduleExerciseRepository.updateExerciseSchedule(workoutId, dateString)
        }
    }
    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}