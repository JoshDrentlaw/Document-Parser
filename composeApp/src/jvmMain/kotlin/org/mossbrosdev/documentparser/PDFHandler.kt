package org.mossbrosdev.documentparser

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.absolutePath
import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer

class PDFHandler {
    protected var pdfDocument: PDDocument? = null

    fun loadDocument(pdfFile: PlatformFile) {
        val javaFile = java.io.File(pdfFile.absolutePath())
        pdfDocument = try {
            Loader.loadPDF(javaFile)
        } catch (e: java.io.IOException) {
            println(e.message.toString())
            null
        }
    }

    fun renderPDFToImage(): ImageBitmap {
        val renderer = PDFRenderer(pdfDocument)
        val image = renderer.renderImageWithDPI(0, 150f)
        return image.toComposeImageBitmap()
    }

    fun processPDF() : ParsedDocument {
        pdfDocument?.let {
            val tableExtractor = TableExtractor(pdfDocument)
            val document = tableExtractor.extractTables()

            return document
        }

        return ParsedDocument(mutableListOf())
    }
}