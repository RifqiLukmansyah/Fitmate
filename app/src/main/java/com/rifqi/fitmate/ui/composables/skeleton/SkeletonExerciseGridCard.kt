package com.rifqi.fitmate.ui.composables.skeleton

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp

import com.rifqi.fitmate.ui.theme.neutral80
import com.valentinilk.shimmer.shimmer


@Composable
fun SkeletonExerciseGridCard(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = neutral80
        ),
        modifier = modifier
            .width(166.dp)
            .height(197.dp)
    ) {
        // Skeleton for Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .shimmer()
        )

        // Skeleton for Text Informasi
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(12.dp),
        ) {
            // Skeleton for Informasi di Atas Card
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Skeleton for Informasi Kategori dan Bolt Icons
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(16.dp)
                        .shimmer()
                )

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

            // Skeleton for Nama Latihan
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .shimmer()
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Skeleton for Informasi Tambahan
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                ExerciseAdditionInformationSkeleton()
                ExerciseAdditionInformationSkeleton()
            }
        }
    }
}