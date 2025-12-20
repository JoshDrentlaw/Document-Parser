package org.mossbrosdev.documentparser

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.github.vinceglb.filekit.FileKit
import java.io.File

fun main() = application {
    val directoryState = mutableStateOf(System.getProperty("user.dir"))
    FileKit.init(
        appId = "org.mossbrosdev.documentparser",
        filesDir = File(directoryState.value)
    )

    val sizeState = mutableStateOf(DpSize(1280.dp, 800.dp))
    val positionState = mutableStateOf(WindowPosition(0.dp, 0.dp))
    val windowState = rememberWindowState(
        size = sizeState.value,
        position = positionState.value
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "DocumentParser",
        state = windowState,
    ) {
        App()
    }
}