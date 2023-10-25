package com.anythingsskyblue.android.library.media

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import com.anythingsskyblue.android.library.media.media_picker.MediaPicker
import com.anythingsskyblue.android.library.media.metadata.VideoUtil
import com.anythingsskyblue.android.library.media.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(

                    ){
                        val coroutineScope = rememberCoroutineScope()
                        var bitmap by remember { mutableStateOf<Bitmap?>(null) }

                        Thumbnail(bitmap)
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    VideoUtil.getFirstFrame("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                                        .onSuccess { bitmap = it }
                                        .onFailure { bitmap = null }
                                }
                            },
                        ) {
                            Text(text = "run")
                        }

                        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
                        Text(text = selectedImageUri?.toString() ?: "")
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    selectedImageUri = MediaPicker.showImagePicker(this@MainActivity)
                                }
                            },
                        ) {
                            Text(text = "show gallery")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Thumbnail(bitmap: Bitmap?) {
    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "image",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {

    }
}