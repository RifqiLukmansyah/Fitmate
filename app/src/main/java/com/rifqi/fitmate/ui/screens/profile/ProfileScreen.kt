package com.rifqi.fitmate.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rifqi.fitmate.R
import com.rifqi.fitmate.ui.composables.SliderBanner
import com.rifqi.fitmate.ui.screens.model.UserData
import com.rifqi.fitmate.ui.theme.neutral10
import com.rifqi.fitmate.ui.theme.neutral30
import com.rifqi.fitmate.ui.theme.neutral80
import androidx.compose.foundation.layout.Column as Column1

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    redirectToHome: () -> Unit,
) {

    LocalContext.current.resources.configuration
    LocalContext.current
    Column1(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (userData == null) {
            Spacer(modifier = Modifier.height(20.dp))
            SliderBanner()


            Spacer(modifier = Modifier.height(16.dp))
            Column1(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Login to fitmate " , style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp
                ))
                Spacer(modifier = Modifier.height(12.dp))
                Text("Login for Sync your fitness feats in the cloud and unlock achievements, " , style = MaterialTheme.typography.bodyMedium.copy(
                    color = neutral30,
                    fontSize = 14.sp))
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onSignIn,
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
                    ),
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        modifier = Modifier.size(20.dp),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(id = R.string.continue_with_google),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .offset(x = (-20).dp / 2) //default icon width = 24.dp
                    )
                }
            }


        } else {
            if (userData.profilePictureUrl != null) {

                Column1(
                    horizontalAlignment = Alignment.CenterHorizontally,

                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(
                                CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }



                Column1(modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,) {
                    Text("Hello  ${userData.username}")

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onSignOut()
                            redirectToHome()
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
                        ),
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Logout,
                            modifier = Modifier.size(20.dp),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            text = stringResource(id = R.string.logout),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .offset(x = (-20).dp / 2) //default icon width = 24.dp
                        )
                    }

                }


            }

        }
    }
}

