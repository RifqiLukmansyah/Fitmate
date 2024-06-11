package com.rifqi.fitmate.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rifqi.fitmate.R
import com.rifqi.fitmate.data.remote.model.ExerciseData
import com.rifqi.fitmate.ui.theme.lightblue60
import com.rifqi.fitmate.ui.theme.neutral10
import com.rifqi.fitmate.ui.theme.neutral30
import com.rifqi.fitmate.ui.theme.neutral80


@Composable
fun ExerciseGridCard(exercise: ExerciseData, navigateToDetail: (Long) -> Unit, modifier: Modifier = Modifier) {

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = neutral80
        ),

        modifier = modifier
            .width(166.dp)
            .height(197.dp).clickable {
                navigateToDetail(exercise.id?.toLong() ?: 0)
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(exercise.photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(104.dp),
        )
        Column(

            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(12.dp),
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${exercise.category?.name} ",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = neutral30
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (i in 1..3) {
                        Icon(
                            imageVector = if (i <= (exercise.level ?: 0)) Icons.Default.Bolt else Icons.Outlined.Bolt,
                            contentDescription = null,
                            tint = if (i <= (exercise.level ?: 0)) lightblue60 else neutral30,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                }
            }

            Text(
                text = exercise.name ?: "Exercise name",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = neutral10,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Informasi Tambahan
                ExerciseAdditionInformation(
                    icon = Icons.Filled.LocalFireDepartment,
                    text = stringResource(id = R.string.calori_format, exercise.calEstimation ?: 0),
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

//@Preview
//@Composable
//fun ExerciseGridCardPreview() {
//    FitmateTheme {
//        ExerciseGridCard(FakeData.fakeExerciseData[0])
//    }
//}