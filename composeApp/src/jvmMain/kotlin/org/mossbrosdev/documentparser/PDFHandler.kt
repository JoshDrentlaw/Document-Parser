package org.mossbrosdev.documentparser

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.absolutePath
import org.apache.pdfbox.Loader
import org.apache.pdfbox.rendering.PDFRenderer
import org.mossbrosdev.documentparser.pdf.extraction.ExtractedPage
import org.mossbrosdev.documentparser.pdf.extraction.ExtractedDocument
import org.mossbrosdev.documentparser.pdf.extraction.TableRegion
import org.mossbrosdev.documentparser.pdf.ports.TableExtractor
import technology.tabula.ObjectExtractor
import technology.tabula.Page
import technology.tabula.TableWithRulingLines
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm
import java.util.UUID
import kotlin.uuid.Uuid

class PDFHandler(
    val pdfFile: PlatformFile
) {
    private val DPI: Float = 150f
    private val PIXELS_PER_PT: Float = DPI / 72f
    private var extractedDocument: ExtractedDocument = ExtractedDocument()
    private var imageRenderer: PDFRenderer? = null

    fun getParsedDocument(): ExtractedDocument = extractedDocument

    fun process() {
        // Load the PDF document
        loadDocument()
        if (extractedDocument.pdDocument == null) return
        if (extractedDocument.pdDocument!!.isEncrypted) return
        imageRenderer = PDFRenderer(extractedDocument.pdDocument)

        // Extract pages
        extractPages()

        // Find tables using tabula-java
        extractedDocument.pages.forEachIndexed { i, page ->
            val tables = extractTablesFromPage(page.page)
            extractedDocument.pages[i].tables.addAll(tables)
        }
    }

    fun loadDocument() {
        val javaFile = java.io.File(pdfFile.absolutePath())
        extractedDocument.pdDocument = try {
            Loader.loadPDF(javaFile)
        } catch (e: java.io.IOException) {
            println(e.message.toString())
            null
        }
    }

    fun extractPages() {
        val extractor = ObjectExtractor(extractedDocument.pdDocument)
        extractor.extract().forEach { page ->
            val image = renderPageToImage(page.pageNumber - 1)
            val extractedPage = ExtractedPage(UUID.randomUUID().toString(), page, image = image)
            extractedDocument.pages.add(extractedPage)
        }
    }

    fun extractTablesFromPage(page: Page): MutableList<TableRegion> {
        val tableRegions: MutableList<TableWithRulingLines> = SpreadsheetExtractionAlgorithm().extract(page).filterIsInstance<TableWithRulingLines>().toMutableList()
        if (tableRegions.isEmpty()) return mutableListOf()

        val tableExtractor = TableExtractor(PIXELS_PER_PT, tableRegions)

        return tableExtractor.extract()
    }

    fun renderPageToImage(pageIndex: Int): ImageBitmap? {
        return imageRenderer?.renderImageWithDPI(pageIndex, DPI)?.toComposeImageBitmap()
    }
}