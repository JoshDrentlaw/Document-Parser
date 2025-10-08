package org.mossbrosdev.documentparser

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var filePath by remember { mutableStateOf<String?>(null) }

    MaterialTheme {
        val pdfHandler = PDFHandler()

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { scope.launch{ filePath = pdfHandler.openFilePicker() } }) {
                Text("Upload File")
            }

            filePath?.let { path ->
                /*val tables = pdfHandler.processPDF(path)
                LazyColumn {
                    items(tables.size) { index ->
                        Row(modifier = Modifier.fillParentMaxWidth().border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer).padding(4.dp)) {
                            tables[index].rows.forEach { row ->
                                val rowData = row.joinToString(" | ") { it.text.replace("\r", " ").trim() }
                                Text(rowData, modifier = Modifier.weight(1f).border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer))
                            }
                        }
                    }
                }*/
            }
        }
    }
}