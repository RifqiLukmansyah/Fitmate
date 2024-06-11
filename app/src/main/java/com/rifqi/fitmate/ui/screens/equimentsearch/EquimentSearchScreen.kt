package com.rifqi.fitmate.ui.screens.equimentsearch

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.rifqi.fitmate.data.util.UiState
import com.rifqi.fitmate.ui.composables.CameraX
import com.rifqi.fitmate.ui.composables.PeekContent
import com.rifqi.fitmate.ui.theme.lightblue120
import com.rifqi.fitmate.ui.theme.lightblue60
import com.rifqi.fitmate.ui.theme.neutral10
import com.rifqi.fitmate.ui.theme.neutral80
import com.rifqi.fitmate.ui.util.reduceFileImage
import com.rifqi.fitmate.ui.util.saveBitmapToFile
import com.rifqi.fitmate.ui.util.uriToFile
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquimentSearchScreen(
    navigateToDetail: (workoutId: Long) -> Unit,

   viewModel: EquimentSearchViewModel  = hiltViewModel()
) {
    var currentProgress by remember { mutableFloatStateOf(0f) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val context = LocalContext.current
    val imageBitmap = remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri

        if(uri != null) {
            val imageFile = uriToFile(uri, context).reduceFileImage()
            Log.d("URI -> FILE", imageFile.toString())
            viewModel.predictImage(imageFile)

            scope.launch {
                scaffoldState.bottomSheetState.expand()
            }
        }

    }
    val controller = remember {

        LifecycleCameraController(context).apply {
            setEnabledUseCases(

                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )

        }
    }


    if (imageBitmap.value != null || imageUri != null) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContainerColor = neutral80,
            sheetContentColor = neutral10,
            sheetContent = {

                viewModel.predict.collectAsState(initial = UiState.Loading).value.let {uiState ->
                    when(uiState) {
                        is UiState.Loading -> {
                            scope.launch {
                                loadProgress { progress ->
                                    currentProgress = progress
                                }

                            }
                            Column(
                                horizontalAlignment =  Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(16.dp)

                            ){
                                Text("Loading ...." , style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                ))

                                Spacer(modifier = Modifier.height(12.dp))


                                Text("Fun fact: Laughter is a workout! A hearty laugh engages core muscles and boosts endorphin levels," , style = MaterialTheme.typography.bodyMedium.copy(
                                    textAlign = TextAlign.Center
                                ))
                                Spacer(modifier = Modifier.height(12.dp))

                                LinearProgressIndicator(
                                    progress =  currentProgress ,
                                    trackColor = lightblue120,
                                    color = lightblue60,

                                    modifier = Modifier.fillMaxWidth(),)
                            }

                        }
                        is UiState.Success -> {
                            uiState.data.data?.let { PeekContent(it,navigateToDetail) }




                        }
                        is UiState.Error -> {

                            Text("Error")

                        }
                    }
                }
            }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                imageBitmap.value?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            imageBitmap.value = null
                            imageUri = null
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Open Gallery",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    imageBitmap.value?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black)
                        )
                    }
                    imageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            imageBitmap.value = MediaStore.Images
                                .Media.getBitmap(context.contentResolver, it)

                        } else {
                            val source = ImageDecoder
                                .createSource(context.contentResolver, it)
                            imageBitmap.value = ImageDecoder.decodeBitmap(source)
                        }

                        imageBitmap.value?.let { bitmap ->
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                            )
                        }
                    }

                    // Position the ArrowUpward icon at the bottom center
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = "Open Gallery",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

        }


} else {
        CameraX(controller = controller, modifier = Modifier.fillMaxSize())

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = {
                    launcher.launch("image/*")

                }) {
                    Icon(
                        imageVector = Icons.Default.Photo,
                        contentDescription = "Open Gallery"
                    )
                }
                IconButton(onClick = {
                    takePhoto(
                        controller = controller,
                        context = context,
                        onPhotoTaken = { bitmap ->
                            imageBitmap.value = bitmap
                            val savedUri = saveBitmapToFile(context, bitmap)
                            savedUri.let { uri ->
                                val imageFile = uriToFile(uri, context).reduceFileImage()
                                Log.d("URI -> FILE", imageFile.toString())
                                viewModel.predictImage(imageFile)
                            }
                            viewModel.onTakePhoto(savedUri)

                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    )
                }) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Take Photo "
                    )
                }

            }


        }

    }


}

/** Iterate the progress value */
suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}


object CameraPermissions {
    val CAMERAX_PERMISSION = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )
}

private fun hasRequiredPermissions(context: Context): Boolean {
    return CameraPermissions.CAMERAX_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}

private fun takePhoto(
    controller: LifecycleCameraController,
    context: Context,
    onPhotoTaken: (Bitmap) -> Unit
) {
    if (!hasRequiredPermissions(context)) {
        return
    }
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                    postScale(-1f, 1f)
                }
                val rotatedBitMap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )

                onPhotoTaken(rotatedBitMap)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.d("Camera ", "Could't take photo", exception)
            }

        }

    )


}
