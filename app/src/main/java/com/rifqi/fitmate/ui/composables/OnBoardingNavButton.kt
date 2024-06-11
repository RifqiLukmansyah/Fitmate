package com.rifqi.fitmate.ui.composables

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rifqi.fitmate.ui.theme.neutral80

@Composable
fun OnBoardNavButton(
    modifier: Modifier = Modifier, currentPage: Int, noOfPages: Int, onNextClicked: () -> Unit , onEndClicked : () -> Unit
) {
    Button(
        colors=  ButtonDefaults.buttonColors(
            containerColor = neutral80
        ),
                onClick = {
            if (currentPage < noOfPages - 1) {
                onNextClicked()
            }else{
                onEndClicked()
            }
        }, modifier = modifier
    ) {
        Text(text = if (currentPage < noOfPages - 1) "Next" else "Get Started" , style = MaterialTheme.typography.bodyMedium.copy(

        ))
    }
}