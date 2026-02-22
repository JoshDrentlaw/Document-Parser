package org.mossbrosdev.documentparser.pdf.extraction

import androidx.compose.ui.graphics.ImageBitmap
import technology.tabula.Page

data class ExtractedPage(
    val id: String,
    val page: Page,
    val pageNumber: Int = page.pageNumber,
    val tables: MutableList<TableRegion> = mutableListOf(),
    val image: ImageBitmap? = null,
)