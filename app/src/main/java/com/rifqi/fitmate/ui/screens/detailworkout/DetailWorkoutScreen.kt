package com.rifqi.fitmate.ui.screens.detailworkout

import android.os.Build.VERSION.SDK_INT
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.local.entity.SchenduleExerciseEntity
import com.rifqi.fitmate.data.remote.model.DetailExercise
import com.rifqi.fitmate.data.remote.model.DetailExerciseRespone
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.ui.theme.lightblue60
import com.rifqi.fitmate.ui.theme.neutral10
import com.rifqi.fitmate.ui.theme.neutral30
import com.rifqi.fitmate.ui.theme.neutral80
import com.rifqi.fitmate.ui.util.formatDate
import com.rifqi.fitmate.ui.util.formatSteps
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DetailWorkoutScreen(
    workoutId: Long,
    navigateBack: () -> Unit,
    navigateToInteractiveArea: (Long) -> Unit,

    detailWorkoutViewModel: DetailWorkoutViewModel = hiltViewModel()

) {


    val exerciseState by detailWorkoutViewModel.exercise.collectAsState(initial = UiState.Loading)
    when (exerciseState) {
        is UiState.Loading -> {
            detailWorkoutViewModel.getWorkoutById(workoutId)
        }

        is UiState.Success -> {
            val data = (exerciseState as UiState.Success<DetailExerciseRespone>).data



           if(data.data != null) {
               DetailContent(
                   navigateBack,
                   data.data,
                   navigateToInteractiveArea,
                   addToSchendule = { schendule ->
                       detailWorkoutViewModel.addWorkoutSchendule(schendule)
                   },



                   )
           }
        }

        is UiState.Error -> {
            Text(stringResource(id = R.string.error_message))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    navigateBack: () -> Unit,
    exercise: DetailExercise,
    navigateToInteractiveArea: (Long) -> Unit,
    addToSchendule: (SchenduleExerciseEntity) -> String,

    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
    ) {


        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back to home screen"
                    )
                }
                Text(exercise.name ?: "")
                Surface(
                    color = neutral80,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.clickable {
                        isSheetOpen = true

                    }
                ) {
                    Box(Modifier.padding(8.dp)) {
                        Icon(
                            painterResource(id = R.drawable.ic_schendule_inactive),
                            tint = neutral10,

                            contentDescription = "Schendule to learn this"
                        )
                    }

                }
            }
            WorkoutTutorial(exercise.gifUrl ?: "")
            Spacer(modifier = Modifier.height(100.dp))
            WorkoutInformationTab(exercise)


        }

        if (isSheetOpen) {
            ModalBottomSheet(
                containerColor = neutral80,
                contentColor = neutral10,
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                }) {
                Column(
                    Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 36.dp,
                    )
                ) {


                    DatePicker(
                        state = datePickerState,

                        title = {
                            Text(
                                "When you want learn this exercise",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },

                        showModeToggle = true, colors = DatePickerDefaults.colors(
                            yearContentColor = neutral10,
                            containerColor = neutral80,

                            selectedDayContentColor = lightblue60,
                            todayDateBorderColor = neutral10,
                            todayContentColor = neutral30
                        )
                    )

                    Button(
                        enabled = System.currentTimeMillis() <= (datePickerState.selectedDateMillis
                            ?: 0) || formatDate(System.currentTimeMillis()) == formatDate(
                            datePickerState.selectedDateMillis ?: 0
                        ),
                        onClick = {


                            val data = SchenduleExerciseEntity(
                                id_exercise = exercise.id?.toLong() ?: 0,
                                name_exercise = exercise.name ?: "",
                                exercise_calori = exercise.calEstimation ?: 0,
                                exercise_gif_url = exercise.gifUrl ?: "",
                                exercise_category = exercise.category?.name ?: "",
                                dateMillis = datePickerState.selectedDateMillis ?: 0,
                                dateString = formatDate(datePickerState.selectedDateMillis ?: 0L),
                                exercise_muscle_target = exercise.muscle?.name ?: "",
                                isFinished = false,
                            )

                            CoroutineScope(Dispatchers.Main).launch {
                                val message = addToSchendule(data)

                                Toast.makeText(
                                    context,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                isSheetOpen = false
                            }

                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Schendule ")
                    }

                }

            }
        }
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background
                )
                .padding(
                    vertical = 16.dp,
                    horizontal = 24.dp
                )
                .fillMaxWidth()

                .align(Alignment.BottomStart)
        ) {

            if (exercise.isSupportInteractive == 1 ) {
                FilledTonalButton(
                    onClick = {
                        navigateToInteractiveArea(exercise.id?.toLong() ?: 0)
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(
                        vertical = 14.dp,
                        horizontal = 49.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = neutral80,
                        contentColor = neutral10
                    )
                ) {
                    Text(
                        stringResource(id = R.string.start_interactive_text),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily.Default,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }


        }
    }

}

@Composable
fun GifImage(
    gif: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(

        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = gif).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
    )

}

@Composable
fun WorkoutTutorial(
    gif: String,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabTitles = listOf("Animation", "Video")

    Column {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = lightblue60,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            },
            modifier = Modifier.padding(
                horizontal = 60.dp
            )
        ) {
            // Create tabs
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    }
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // Content based on selected tab
        Box(modifier.padding(16.dp)) {
            when (selectedTabIndex) {
                0 -> GifImage(gif)
                1 -> WorkoutVideo()
            }
        }

    }
}

@Composable
fun WorkoutVideo() {
    Text("Sorry , No Available for now", style = MaterialTheme.typography.bodyMedium.copy(
        textAlign = TextAlign.Center
    ))

}

@Composable
fun WorkoutInformationTab(exercise: DetailExercise) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabTitles = listOf("Overview", "Step by Step", "Muscle Target")

    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = lightblue60,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            }
        ) {
            // Create tabs
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    }
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> OverviewContent(exercise.overview ?: "")
            1 -> StepByStepContent(exercise.step ?: "")
            2 -> MuscleTargetContent(exercise.muscle?.name ?: "")
        }
    }
}

@Composable
fun OverviewContent(explain: String) {
    // Content for Overview tab
    Text(
        explain, style = MaterialTheme.typography.bodyMedium.copy(
            color = neutral30
        )
    )
}

@Composable
fun StepByStepContent(step: String) {
    val stepsArray = step.split(", ").toTypedArray()

    val formattedSteps = formatSteps(stepsArray)

    // Content for Step by Step tab
    Text(
        formattedSteps, style = MaterialTheme.typography.bodyMedium.copy(
            color = neutral30
        )
    )
}

@Composable
fun MuscleTargetContent(muscle : String) {
    // Content for Muscle Target tab
    Text("Muscle Target for this exercise is $muscle")
}
