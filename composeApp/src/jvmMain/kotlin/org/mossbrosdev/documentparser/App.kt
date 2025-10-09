package org.mossbrosdev.documentparser

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntSize
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val pdfHandler = PDFHandler()
    val scope = rememberCoroutineScope()
    var renderedImage by remember { mutableStateOf<ImageBitmap?>(null) }
    val launcher = rememberFilePickerLauncher(
        type = FileKitType.File("pdf"),
        directory = PlatformFile(System.getProperty("user.dir")),
        title = "Select PDF file",
    ) { file ->
        file?.let {
            pdfHandler.loadDocument(file)
            renderedImage = pdfHandler.renderPDFToImage()
        }
    }

    MaterialTheme {

        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Select PDF file to render:",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(end = 16.dp),
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    )
                    Button(onClick = { launcher.launch() }) {
                        Text("Upload File")
                    }
                }

                // Interaction states
                var scale by remember { mutableStateOf(0.5f) }
                var offset by remember { mutableStateOf(Offset.Zero) }
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale = (scale * zoom).coerceIn(0.5f, 5f)
                                offset += pan
                            }
                        }
                        .border(5.dp, MaterialTheme.colorScheme.onSecondaryContainer)
                        .clip(MaterialTheme.shapes.medium),
                ) {
                    renderedImage?.let { img ->
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        val imgWidth = img.width * scale
                        val imgHeight = img.height * scale

                        drawImage(
                            image = img,
                            dstSize = Size(imgWidth, imgHeight).toIntSize(),
                            dstOffset = IntOffset(
                                x = ((canvasWidth - imgWidth) / 2 + offset.x).toInt(),
                                y = ((canvasHeight - imgHeight) / 2 + offset.y).toInt(),
                            )
                        )
                    }
                }
            }
        }
    }
}