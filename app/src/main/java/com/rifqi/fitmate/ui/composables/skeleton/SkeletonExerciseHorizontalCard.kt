package com.rifqi.fitmate.ui.composables.skeleton

import androidx.compose.ui.Modifier
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

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.unit.dp
import com.rifqi.fitmate.ui.theme.neutral80
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonExerciseHorizontalCard(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = neutral80
        ),
        modifier = modifier
            .width(300.dp)
            .height(140.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Skeleton for the Image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmer()
            )

            // Skeleton for Text Informasi
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Skeleton for Informasi di Atas Card
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Skeleton for Informasi Kategori dan Target Otot
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(16.dp)
                            .shimmer()
                    )

                    // Skeleton for Bolt Icons
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(19.dp)
                                    .shimmer()
                            )
                        }
                    }
                }

                // Skeleton for Informasi di Bawah Card
                Column {
                    // Skeleton for Nama Latihan
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .shimmer()
                    )
                    Spacer(modifier = Modifier.height(9.dp))

                    // Skeleton for Informasi Tambahan
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ExerciseAdditionInformationSkeleton()
                        ExerciseAdditionInformationSkeleton()
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseAdditionInformationSkeleton(modifier: Modifier = Modifier) {
    // Skeleton for Informasi Tambahan
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .shimmer()
    )
}
