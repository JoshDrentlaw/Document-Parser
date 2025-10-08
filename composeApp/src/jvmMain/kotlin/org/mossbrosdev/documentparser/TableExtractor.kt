package org.mossbrosdev.documentparser

import org.apache.pdfbox.Loader
import technology.tabula.ObjectExtractor
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm
import java.io.File
import java.io.IOException

data class ExtractedTable(var rows: MutableList<MutableList<String>>)
data class ParsedPage(val pageNumber: Int, val tables: MutableList<ExtractedTable>)
data class ParsedDocument(val pages: MutableList<ParsedPage>)

class TableExtractor {
    fun extractTables(filePath: String): ParsedDocument {
        val pdfFile = File(filePath)
        val document = try {
            Loader.loadPDF(pdfFile)
        } catch (e: IOException) {
            println(e.message.toString())
            return ParsedDocument(mutableListOf())
        }
        val extractor = ObjectExtractor(document)
        val spreadsheetExtractor = SpreadsheetExtractionAlgorithm()
        val parsedDocument = ParsedDocument(mutableListOf())

        extractor.extract().forEach { page ->
            val tables = spreadsheetExtractor.extract(page)
            val parsedPage = ParsedPage(page.pageNumber, mutableListOf())
            tables.forEach { table ->
                val rows = table.rows.map { row ->
                    row.map { cell -> cell.text }.toMutableList()
                }.toMutableList()
                val extractedTable = ExtractedTable(rows)
                parsedPage.tables.add(extractedTable)
            }
            parsedDocument.pages.add(parsedPage)
        }

        return parsedDocument
    }
}