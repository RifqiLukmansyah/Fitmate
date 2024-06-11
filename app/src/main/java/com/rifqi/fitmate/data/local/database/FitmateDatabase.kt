package com.rifqi.fitmate.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rifqi.fitmate.data.local.dao.SchenduleExerciseDao
import com.rifqi.fitmate.data.local.entity.SchenduleExerciseEntity


@Database(entities = [SchenduleExerciseEntity::class], version = 1, exportSchema = false)
abstract class FitmateDatabase  : RoomDatabase(){

    abstract fun schenduleDao() : SchenduleExerciseDao


}