package com.rifqi.fitmate.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.remote.model.ExerciseData
import com.rifqi.fitmate.ui.theme.lightblue60
import com.rifqi.fitmate.ui.theme.neutral30


@Composable
fun ExerciseHorizontalCard(
    exercise: ExerciseData, navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .clickable {
                navigateToDetail(exercise.id?.toLong() ?: 0)
            }
            .width(300.dp)
            .height(140.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val contrast = 0.6f
            val colorMatrix = floatArrayOf(
                contrast, 0f, 0f, 0f, 0f,
                0f, contrast, 0f, 0f, 0f,
                0f, 0f, contrast, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(exercise.photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix)),
            )

            // Text Informasi
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                // Informasi di Atas Card
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Informasi Kategori dan Target Otot
                    Text(
                        text = "${exercise.muscle?.name} | ${exercise.category?.name} ",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = neutral30
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        for (i in 1..3) {
                            Icon(
                                imageVector = if (i <= (exercise.level ?: 1)) Icons.Default.Bolt else Icons.Outlined.Bolt,
                                contentDescription = null,
                                tint = if (i <= (exercise.level ?: 1)) lightblue60 else neutral30,
                                modifier = Modifier.size(19.dp)
                            )
                        }
                    }
                }

                // Informasi di Bawah Card
                Column() {
                    // Nama Latihan
                    Text(
                        text = exercise.name ?: "",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(9.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Informasi Tambahan
                        ExerciseAdditionInformation(
                            icon = Icons.Filled.LocalFireDepartment,
                            text = stringResource(
                                id = R.string.calori_format,
                                exercise.calEstimation ?: 0
                            ),
                            iconTint = lightblue60
                        )

                        // Rating
                        ExerciseAdditionInformation(
                            icon = Icons.Filled.Star,
                            text = stringResource(id = R.string.rating_format, exercise.rating ?: 0),
                            iconTint = lightblue60
                        )
                    }
                }
            }
        }
    }
}


//@Preview
//@Composable
//fun ExerciseHorizontalCardPreview() {
//    FitmateTheme {
//        ExerciseHorizontalCard(FakeData.fakeExerciseData[0], )
//    }
//}