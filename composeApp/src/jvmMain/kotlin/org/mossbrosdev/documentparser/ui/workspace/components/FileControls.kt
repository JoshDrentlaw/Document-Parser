package org.mossbrosdev.documentparser.ui.workspace.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import org.mossbrosdev.documentparser.PDFHandler
import org.mossbrosdev.documentparser.pdf.extraction.ExtractedDocument

@Composable
fun UploadButton(setExtractedDocument: (PlatformFile?) -> Unit) {
    val launcher = rememberFilePickerLauncher(
        type = FileKitType.File("pdf"),
        directory = PlatformFile(System.getProperty("user.dir")),
        title = "Select PDF file",
        onResult = setExtractedDocument
    )
    Button(onClick = { launcher.launch() }) {
        Text("Open PDF")
    }
}