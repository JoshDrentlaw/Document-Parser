package org.mossbrosdev.documentparser

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.absolutePath
import org.apache.pdfbox.Loader
import org.apache.pdfbox.rendering.PDFRenderer
import technology.tabula.ObjectExtractor
import technology.tabula.Page
import technology.tabula.TableWithRulingLines
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm

class PDFHandler(
    val pdfFile: PlatformFile
) {
    private val DPI: Float = 150f
    private val PIXELS_PER_PT: Float = DPI / 72f
    private var parsedDocument: ParsedDocument = ParsedDocument()
    private var imageRenderer: PDFRenderer? = null

    fun getParsedDocument(): ParsedDocument = parsedDocument

    fun process() {
        // Load the PDF document
        loadDocument()
        if (parsedDocument.pdDocument == null) return
        if (parsedDocument.pdDocument!!.isEncrypted) return
        imageRenderer = PDFRenderer(parsedDocument.pdDocument)

        // Extract pages
        extractPages()

        // Find tables using tabula-java
        parsedDocument.pages.forEachIndexed { i, page ->
            val tables = extractTablesFromPage(page.page)
            parsedDocument.pages[i].tables.addAll(tables)
        }
    }

    fun loadDocument() {
        val javaFile = java.io.File(pdfFile.absolutePath())
        parsedDocument.pdDocument = try {
            Loader.loadPDF(javaFile)
        } catch (e: java.io.IOException) {
            println(e.message.toString())
            null
        }
    }

    fun extractPages() {
        val extractor = ObjectExtractor(parsedDocument.pdDocument)
        extractor.extract().forEach { page ->
            val image = renderPageToImage(page.pageNumber - 1)
            val parsedPage = ParsedPage(page, image = image)
            parsedDocument.pages.add(parsedPage)
        }
    }

    fun extractTablesFromPage(page: Page): MutableList<DetectedTable> {
        val tableRegions: MutableList<TableWithRulingLines> = SpreadsheetExtractionAlgorithm().extract(page).filterIsInstance<TableWithRulingLines>().toMutableList()
        if (tableRegions.isEmpty()) return mutableListOf()

        val tableExtractor = TableExtractor(PIXELS_PER_PT, tableRegions)

        return tableExtractor.extract()
    }

    fun renderPageToImage(pageIndex: Int): ImageBitmap? {
        return imageRenderer?.renderImageWithDPI(pageIndex, DPI)?.toComposeImageBitmap()
    }
}