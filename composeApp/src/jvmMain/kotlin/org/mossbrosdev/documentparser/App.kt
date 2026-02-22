package org.mossbrosdev.documentparser

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntSize
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mossbrosdev.documentparser.ui.workspace.components.UploadButton

@Composable
@Preview
fun App() {
    MaterialTheme {
        // Interaction states
        var scale by remember { mutableStateOf(1f) }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    UploadButton { file ->
                        file?.let {
                            val pdfHandler = PDFHandler(file)
                            pdfHandler.process()
                            extractedDocument = pdfHandler.getParsedDocument()
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onPrimaryContainer),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f, true)
                            .fillMaxSize()
                            .border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer, MaterialTheme.shapes.medium)
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                                .clipToBounds(),
                        ) {  }
                    }
                    Column(
                        modifier = Modifier
                            .weight(3f, true)
                            .fillMaxSize()
                            .border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer, MaterialTheme.shapes.medium)
                            .clip(MaterialTheme.shapes.medium),
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxSize()
                                .clipToBounds(),
                        ) {
                            val viewportSize = with(LocalDensity.current) {
                                IntSize(maxWidth.roundToPx(), maxHeight.roundToPx())
                            }
                        }
                    }
                }
            }
        }
    }
}