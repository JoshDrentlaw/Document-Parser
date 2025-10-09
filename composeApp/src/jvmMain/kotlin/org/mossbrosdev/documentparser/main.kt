package org.mossbrosdev.documentparser

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.github.vinceglb.filekit.FileKit
import java.io.File

fun main() = application {
    FileKit.init(
        appId = "org.mossbrosdev.documentparser",
        filesDir = File(System.getProperty("user.home"))
    )

    val sizeState = mutableStateOf(DpSize(1280.dp, 800.dp))
    val windowState = rememberWindowState(size = sizeState.value)

    Window(
        onCloseRequest = ::exitApplication,
        title = "DocumentParser",
        state = windowState,
    ) {
        App()
    }
}