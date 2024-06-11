package com.rifqi.fitmate.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rifqi.fitmate.data.local.dao.ScheduleExerciseDao
import com.rifqi.fitmate.data.local.entity.ScheduleExerciseEntity


@Database(entities = [ScheduleExerciseEntity::class], version = 2, exportSchema = false)
abstract class FitmateDatabase  : RoomDatabase(){

    abstract fun scheduleDao() : ScheduleExerciseDao


}