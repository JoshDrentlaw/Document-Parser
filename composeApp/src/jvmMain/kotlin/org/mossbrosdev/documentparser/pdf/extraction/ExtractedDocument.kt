package org.mossbrosdev.documentparser.pdf.extraction

import androidx.compose.ui.graphics.ImageBitmap
import org.apache.pdfbox.pdmodel.PDDocument

data class ExtractedDocument(
    var pdDocument: PDDocument? = null,
    val pages: MutableList<ExtractedPage> = mutableListOf()
) {
    fun getPageImages() : MutableList<ImageBitmap> = pages.mapNotNull { it.image }.toMutableList()
}