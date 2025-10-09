package org.mossbrosdev.documentparser

import org.apache.pdfbox.pdmodel.PDDocument
import technology.tabula.ObjectExtractor
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm

data class ExtractedTable(var rows: MutableList<MutableList<String>>)
data class ParsedPage(val pageNumber: Int, val tables: MutableList<ExtractedTable>)
data class ParsedDocument(val pages: MutableList<ParsedPage>)

class TableExtractor(
    val pdfDocument: PDDocument? = null,
) {
    fun extractTables(): ParsedDocument {
        val extractor = ObjectExtractor(pdfDocument)
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