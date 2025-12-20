package org.mossbrosdev.documentparser

import androidx.compose.ui.graphics.ImageBitmap
import io.github.vinceglb.filekit.PlatformFile
import java.nio.file.Paths
import kotlin.test.Test

class PDFHandlerTest {
    val singleTablePdfPath = Paths.get("src", "jvmTest", "resources", "one-table.pdf").toAbsolutePath().toString()
    val multipleTablesPdfPath = Paths.get("src", "jvmTest", "resources", "multiple-tables.pdf").toAbsolutePath().toString()
    val capitalOnePdfPath = Paths.get("src", "jvmTest", "resources", "capital-one.pdf").toAbsolutePath().toString()

    @Test
    fun `loadDocument sets pdDocument`() {
        val pdfHandler = PDFHandler(PlatformFile(singleTablePdfPath))
        pdfHandler.loadDocument()
        assert(pdfHandler.getParsedDocument().pdDocument != null)
    }

    @Test
    fun `extractPages extracts a single page`() {
        val pdfHandler = PDFHandler(PlatformFile(singleTablePdfPath))
        pdfHandler.loadDocument()
        pdfHandler.extractPages()
        assert(pdfHandler.getParsedDocument().pages.isNotEmpty())
    }

    @Test
    fun `extractPages extracts multiple pages`() {
        val pdfHandler = PDFHandler(PlatformFile(multipleTablesPdfPath))
        pdfHandler.loadDocument()
        pdfHandler.extractPages()
        assert(pdfHandler.getParsedDocument().pages.size == 2)
    }

    @Test
    fun `process extracts a single table`() {
        val pdfHandler = PDFHandler(PlatformFile(singleTablePdfPath))
        pdfHandler.process()
        assert(pdfHandler.getParsedDocument().pages.isNotEmpty())
        assert(pdfHandler.getParsedDocument().pages.first().tables.isNotEmpty())
    }

    @Test
    fun `process extracts tables from capital one file`() {
        val pdfHandler = PDFHandler(PlatformFile(capitalOnePdfPath))
        pdfHandler.process()
        assert(pdfHandler.getParsedDocument().pages.isNotEmpty())
        assert(pdfHandler.getParsedDocument().pages.size == 2)
    }

    @Test
    fun `renderPageToImage returns an image`() {
        val pdfHandler = PDFHandler(PlatformFile(singleTablePdfPath))
        pdfHandler.process()
        assert(pdfHandler.getParsedDocument().pages.isNotEmpty())
        assert(pdfHandler.getParsedDocument().pages.first().image is ImageBitmap)
        assert(pdfHandler.getParsedDocument().pages.first().image?.width != null)
        assert(pdfHandler.getParsedDocument().pages.first().image?.height != null)
    }
}