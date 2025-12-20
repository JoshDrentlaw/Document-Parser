package org.mossbrosdev.documentparser

import androidx.compose.ui.graphics.ImageBitmap
import org.apache.pdfbox.pdmodel.PDDocument
import technology.tabula.Page

data class Coordinates(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
)
data class DetectedTable(
    val coordinates: Coordinates,
    val data: MutableList<MutableList<String>> = mutableListOf(),
)
data class ParsedPage(
    val page: Page,
    val pageNumber: Int = page.pageNumber,
    val tables: MutableList<DetectedTable> = mutableListOf(),
    val image: ImageBitmap? = null,
)
data class ParsedDocument(
    var pdDocument: PDDocument? = null,
    val pages: MutableList<ParsedPage> = mutableListOf()
) {
    fun getPageImages() : MutableList<ImageBitmap> = pages.mapNotNull { it.image }.toMutableList()
}
