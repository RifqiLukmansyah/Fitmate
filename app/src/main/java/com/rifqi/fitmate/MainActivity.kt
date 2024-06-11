package com.rifqi.fitmate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.identity.Identity
import com.rifqi.fitmate.ui.FitmateApp
import com.rifqi.fitmate.ui.screens.profile.ProfileViewModel
import com.rifqi.fitmate.ui.screens.sign_in.GoogleAuthClient
import com.rifqi.fitmate.ui.theme.FitmateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSION, 0
            )

        }
        setContent {
            FitmateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = viewModel<ProfileViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)

                                }
                            }

                        }
                    )

                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                        if (state.isSignInSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Sign In SuccessFull",
                                Toast.LENGTH_LONG
                            ).show()

                            viewModel.resetState()
                        }
                    }
                    FitmateApp(googleAuthUiClient = googleAuthUiClient, onSignInClick = {
                        lifecycleScope.launch {
                            val signIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signIntentSender ?: return@launch
                                ).build()
                            )
                        }

                    },
                        onLogoutClick = {
                            lifecycleScope.launch {
                                googleAuthUiClient.signOut()

                                viewModel.resetState()

                                Toast.makeText(
                                    applicationContext,
                                    "Signed Out",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }

                    )
                }
            }
        }
    }


    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSION.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSION = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO

        )
    }


}

