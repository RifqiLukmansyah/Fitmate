package com.rifqi.fitmate.data.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.local.entity.ScheduleExerciseEntity
import com.rifqi.fitmate.repository.ScheduleExerciseRepository
import com.rifqi.fitmate.ui.util.ID_REPEATING
import com.rifqi.fitmate.ui.util.NOTIFICATION_CHANNEL_ID
import com.rifqi.fitmate.ui.util.NOTIFICATION_CHANNEL_NAME
import com.rifqi.fitmate.ui.util.NOTIFICATION_ID
import com.rifqi.fitmate.ui.util.executeThread
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject
@AndroidEntryPoint
class DailyReminder : BroadcastReceiver() {

    @Inject
    lateinit var scheduleExerciseRepository: ScheduleExerciseRepository
    override fun onReceive(context: Context, intent: Intent) {
        executeThread {
            val schedule = scheduleExerciseRepository.getTodaySchedule()

            schedule.let {
                if (it.isNotEmpty()) showNotification(context, it) else Log.d("Schedule" ,"No exercise today")
            }
        }
    }
    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, PendingIntent.FLAG_IMMUTABLE)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)

    }
     fun setDailyReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminder::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_REPEATING,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
         val (hourOfDay, minute) = PreferencesManager.getReminderTime(context)

         val calendar = Calendar.getInstance().apply {
             set(Calendar.HOUR_OF_DAY, hourOfDay)
             set(Calendar.MINUTE, minute)
             set(Calendar.SECOND, 0)
         }
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun showNotification(context: Context, content: List<ScheduleExerciseEntity>) {
        val notificationStyle = NotificationCompat.InboxStyle()
        val timeString = context.resources.getString(R.string.notification_message_format)

        content.forEach{
            val scheduleData = String.format(timeString, it.exercise_category,it.exercise_muscle_target, it.name_exercise)
            notificationStyle.addLine(scheduleData)

        }


        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply{
                description = "Course Schedule"
                enableVibration(true)
                vibrationPattern = longArrayOf(100,200,300,400,500)
            }

            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setStyle(notificationStyle)
            .setContentTitle("Today Exercise")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder)
    }
}