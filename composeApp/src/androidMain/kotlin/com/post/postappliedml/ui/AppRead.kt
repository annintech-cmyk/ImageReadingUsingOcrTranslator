package com.post.postappliedml.ui



import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.post.postappliedml.util.AndroidOcrTranslator

@Composable
fun AppRead() {
    val context = LocalContext.current

    // ✅ Create AndroidOcrTranslator instance
    val ocrTranslator = remember { AndroidOcrTranslator() }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var recognizedText by remember { mutableStateOf("Please select image!") }

    val pickPhotoLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            imageUri = uri
        }

    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Button(onClick = {
                pickPhotoLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) { Text("Pick Image") }

            Spacer(Modifier.height(16.dp))

            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(recognizedText)

            //  OCR + Translation
            LaunchedEffect(imageUri) {
                imageUri?.let { uri ->
                    try {
                        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
                        if (bytes != null) {
                            // Step 1: OCR
                            val text = ocrTranslator.recognizeText(bytes)

                            // Step 2: Translate
                            val translated = ocrTranslator.translateText(text)

                            recognizedText = "OCR: $text\nTranslated: $translated"
                        }
                    } catch (e: Exception) {
                        recognizedText = "Error: ${e.message}"
                    }
                }
            }
        }
    }
}

