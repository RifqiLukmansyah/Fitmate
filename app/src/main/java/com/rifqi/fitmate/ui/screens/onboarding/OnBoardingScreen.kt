package com.rifqi.fitmate.ui.screens.onboarding

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rifqi.fitmate.ui.composables.OnBoardDetails
import com.rifqi.fitmate.ui.composables.OnBoardImageView
import com.rifqi.fitmate.ui.composables.OnBoardNavButton
import com.rifqi.fitmate.ui.composables.TabSelector
import com.rifqi.fitmate.ui.util.OnBordingPrefence


@Composable
fun OnBoardingScreen(
    navigateToHome : () -> Unit,

    ) {
    val onboardPages = onboardPagesList

    val context = LocalContext.current

    val currentPage = remember { mutableIntStateOf(0) }

//    LaunchedEffect(key1 =OnBordingPrefence.isOnboardingCompleted(context)  ) {
//        navigateToHome()
//    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Box(

            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.fillMaxWidth().clickable {
                OnBordingPrefence.setOnboardingCompleted(context, true)
                navigateToHome()
            }
        ) {
            Text("Skip", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(36.dp))

        OnBoardImageView(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()  ,
            imageRes = onboardPages[currentPage.intValue].imageRes
        )
        Spacer(modifier = Modifier.height(64.dp))

        OnBoardDetails(
            modifier = Modifier
                .weight(1f),

            currentPage = onboardPages[currentPage.intValue]
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 36.dp)
        ) {


            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),


                ) {

                TabSelector(
                    onboardPages = onboardPages,
                    currentPage = currentPage.intValue
                ) { index ->
                    currentPage.intValue = index
                }
            }

            OnBoardNavButton(
                currentPage = currentPage.intValue,
                noOfPages = onboardPages.size,
                onNextClicked = {
                    currentPage.intValue++

                },
                onEndClicked = {
                    OnBordingPrefence.setOnboardingCompleted(context, true)
                    navigateToHome()
                }

            )
        }
    }
}