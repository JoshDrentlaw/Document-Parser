package org.mossbrosdev.documentparser

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vinceglb.filekit.FileKit

fun main() = application {
    FileKit.init(appId = "org.mossbrosdev.documentparser")

    Window(
        onCloseRequest = ::exitApplication,
        title = "DocumentParser",
    ) {
        App()
    }
}