package com.rifqi.fitmate.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rifqi.fitmate.repository.ExerciseRepository

class ViewModelFactory(private val repository: ExerciseRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
//            return HomeViewModel(repository) as T
//        }
//        if (modelClass.isAssignableFrom(EquimentSearchViewModel::class.java)) {
//            return EquimentSearchViewModel() as T
//        }
//        if (modelClass.isAssignableFrom(DetailWorkoutViewModel::class.java)) {
//            return DetailWorkoutViewModel(repository ) as T
//        }
//        if (modelClass.isAssignableFrom(InteractiveLearnViewModel::class.java)) {
//            return InteractiveLearnViewModel(repository) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}