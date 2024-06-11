package com.rifqi.fitmate.ui.screens.remindersetting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rifqi.fitmate.data.notification.DailyReminder
import com.rifqi.fitmate.data.notification.NotificationStatus
import com.rifqi.fitmate.data.notification.PreferencesManager
import com.rifqi.fitmate.ui.theme.lightblue120
import com.rifqi.fitmate.ui.theme.lightblue60
import com.rifqi.fitmate.ui.theme.neutral10
import com.rifqi.fitmate.ui.theme.neutral30
import com.rifqi.fitmate.ui.theme.neutral80
import com.rifqi.fitmate.ui.theme.neutral90
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderSettingScreen(
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    var showTimePicker by remember { mutableStateOf(false) }
    val state = rememberTimePickerState()
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val dailyReminder = DailyReminder()
    var notificationStatus by  remember { mutableStateOf(NotificationStatus.getNotificationStatus(context)) }
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    Column(
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back to Schedule"
                )
            }
            Text("Reminder Setting")
            Surface(
                color = neutral80,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.clickable {


                }
            ) {


            }
        }




        if (showTimePicker) {
            TimePickerDialog(
                onCancel = { showTimePicker = false },
                onConfirm = {
                    val cal = Calendar.getInstance()
                    cal.set(Calendar.HOUR_OF_DAY, state.hour)
                    cal.set(Calendar.MINUTE, state.minute)
                    cal.isLenient = false
                    PreferencesManager.saveReminderTime(context, state.hour, state.minute)
                    dailyReminder.cancelAlarm(context)
                    dailyReminder.setDailyReminder(context)
                    snackScope.launch {
                        snackState.showSnackbar("We will reminder you at  ${formatter.format(cal.time)}")
                    }
                    showTimePicker = false
                },
            ) {
                TimePicker(
                    colors =  TimePickerDefaults.colors(
                        timeSelectorSelectedContainerColor = lightblue120,
                        timeSelectorSelectedContentColor = lightblue60,
                        periodSelectorSelectedContainerColor = lightblue120,
                        periodSelectorSelectedContentColor = lightblue60,
                        timeSelectorUnselectedContainerColor = lightblue120,



                    ),

                            state = state)
            }
        }
        PrefenceTurnOnOff("Notification " , notificationStatus, onChange = {
            notificationStatus = it
            if(it){
                dailyReminder.setDailyReminder(context)
            }else{
                dailyReminder.cancelAlarm(context)

            }
        })
        if(!notificationStatus){
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "We will notify you for reminder with a list of previously scheduled workouts ",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        if(notificationStatus) {
            PrefenceItem(
                "Reminder Time",
                "We will remidner you for exercise ",
                "${PreferencesManager.getReminderTime(LocalContext.current).first}:${
                    PreferencesManager.getReminderTime(
                        LocalContext.current
                    ).second
                }",
                onClick = {
                    showTimePicker = true


                }
            )
        }

        if(notificationStatus){
            Box(
                modifier = Modifier.padding(16.dp)
            ){
                Text("Please click on the reminder item above and choose when you want to exercise. " , style = MaterialTheme.typography.bodySmall)

            }
        }
        SnackbarHost(hostState = snackState )

    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = neutral80
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        colors = ButtonDefaults.buttonColors(contentColor = neutral10),
                        onClick = onCancel
                    ) { Text("Cancel") }

                    TextButton(
                        colors = ButtonDefaults.buttonColors(contentColor = neutral10),

                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}

@Composable()
fun PrefenceItem(
    title: String,
    description: String,
    value: String,
    onClick: () -> Unit,

) {
    Card(

        colors = CardDefaults.cardColors(
            containerColor = neutral80
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()

            .clickable {
                onClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()


        ) {
            Icon(
                imageVector = Icons.Filled.Timer,
                contentDescription = null,
                tint = lightblue60
            )
            Column {

                Text(
                    title, style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    description, style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        color = neutral30
                    )
                )

            }
            Text(
                value, style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

        }
    }

}


@Composable()
fun PrefenceTurnOnOff(
    title: String,
    notificationStatus : Boolean,
    onChange : (Boolean) -> Unit,
) {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = neutral80
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()


        ) {

            Row {
                Icon(
                    imageVector = Icons.Filled.NotificationsActive,
                    contentDescription = null,
                    tint = lightblue60
                )
                Spacer(modifier = Modifier.width(28.dp))

                Text(
                    title, style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )


            }
            Switch(
                colors = SwitchDefaults.colors(
                    checkedTrackColor = lightblue60,
                    checkedThumbColor = neutral90
                ),
                checked = notificationStatus,
                onCheckedChange = { it ->
                    onChange(it)
                    NotificationStatus.saveNotificationStatus(context, it)
                }
            )
        }
    }

}