package org.mossbrosdev.documentparser

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
    var parsedDocument by remember { mutableStateOf(ParsedDocument()) }
    val launcher = rememberFilePickerLauncher(
        type = FileKitType.File("pdf"),
        directory = PlatformFile(System.getProperty("user.dir")),
        title = "Select PDF file",
    ) { file ->
        file?.let {
            val pdfHandler = PDFHandler(file)
            pdfHandler.process()
            parsedDocument = pdfHandler.getParsedDocument()
        }
    }

    MaterialTheme {
        // Interaction states
        var scale by remember { mutableStateOf(1f) }

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

                val maxWidth = parsedDocument.pages.maxOfOrNull { (it.image?.width ?: 0) * scale } ?: 0.0
                val height = parsedDocument.pages.sumOf { it.image?.height ?: 0 } * scale

                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .drawBehind {
                            drawRect(
                                color = Color.Black,
                                size = Size(size.width, size.height),
                                topLeft = Offset.Zero,
                                style = Stroke(width = 5.dp.toPx())
                            )
                        }
                        .width(maxWidth.toFloat().dp)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height.dp)
                            .pointerInput(Unit) {
                                detectTransformGestures { _, pan, zoom, _ ->
                                    scale *= zoom
//                                offset += pan
                                }
                            },
                    ) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        parsedDocument.pages.forEachIndexed { index, page ->
                            page.image?.let { img ->
                                val imgWidth = img.width * scale
                                val imgHeight = img.height * scale
                                val yOffset = ((canvasHeight - imgHeight) * index)

                                // The next version of CMP will support float values for size and offset
                                drawImage(
                                    image = img,
                                    dstSize = Size(imgWidth, imgHeight).toIntSize(),
                                    dstOffset = IntOffset(
                                        x = (canvasWidth - imgWidth).toInt(),
                                        y = yOffset.toInt(),
                                    )
                                )

                                page.tables.forEachIndexed { index, table ->
                                    drawRect(
                                        style = Stroke(width = 1f),
                                        color = Color.Red,
                                        size = Size(
                                            width = (table.coordinates.width),
                                            height = (table.coordinates.height)
                                        ),
                                        topLeft = Offset(
                                            x = (table.coordinates.x),
                                            y = (table.coordinates.y) + yOffset,
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}