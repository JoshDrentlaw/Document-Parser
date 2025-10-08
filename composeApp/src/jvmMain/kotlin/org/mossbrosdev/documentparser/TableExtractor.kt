package org.mossbrosdev.documentparser

import androidx.compose.ui.graphics.vector.PathData
import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper
import technology.tabula.ObjectExtractor
import technology.tabula.Table
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm
import java.io.File
import java.io.IOException

data class TableData(var rows: MutableList<MutableList<String>>)
data class PageData(val pageNumber: Int, val tables: MutableList<TableData>)
data class DocumentData(val pages: MutableList<PageData>)

class TableExtractor {
    fun extractTables(filePath: String): DocumentData {
        val pdfFile = File(filePath)
        try {
            val document = Loader.loadPDF(pdfFile)
            val extractor = ObjectExtractor(document)
            val spreadsheetExtractor = SpreadsheetExtractionAlgorithm()
            val documentData = DocumentData(mutableListOf())

            extractor.extract().forEach { page ->
                val tables = spreadsheetExtractor.extract(page)
                val pageData = PageData(page.pageNumber, mutableListOf())
                tables.forEach { table ->
                    val rows = table.rows.map { row ->
                        row.map { cell -> cell.text }.toMutableList()
                    }.toMutableList()
                    val tableData = TableData(rows)
                    pageData.tables.add(tableData)
                }
                documentData.pages.add(pageData)
            }

            return documentData
        } catch (e: IOException) {
            println(e.message.toString())
            return DocumentData(mutableListOf())
        }
    }
}