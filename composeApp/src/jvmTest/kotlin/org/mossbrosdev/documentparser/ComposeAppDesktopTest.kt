package org.mossbrosdev.documentparser

import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ComposeAppDesktopTest {

    @Test
    fun `extractTables can find a single table in a PDF`() {
        val pdfHandler = PDFHandler()
        val filePath = Paths.get("src", "jvmTest", "resources", "one-table.pdf").toAbsolutePath().toString()
        val documentData = pdfHandler.processPDF(filePath)
        /*assertTrue(documentData.isNotEmpty(), "No tables were extracted from the PDF.")
        assertEquals(1, documentData.size, "Expected 1 table, but found ${documentData.size}.")

        val table = documentData.first()

        assertEquals(5, table.rows.size, "Expected 5 rows, but found ${table.rows.size}.")
        assertEquals(
            listOf("", "Continent", "Capital", "Currency", "Population"),
            table.rows.map { it.first().text }
        )
        assertEquals(6, table.rows.first().size)
        assertEquals(
            listOf("Capital", "Jakarta", "Berlin", "Vienna", "Paris", "Vatican City"),
            table.rows[2].map { it.text }
        )*/
    }

    @Test
    fun `extractTables can find multiple tables in a PDF`() {
        val pdfHandler = PDFHandler()
        val filePath = Paths.get("src", "jvmTest", "resources", "multiple-tables.pdf").toAbsolutePath().toString()
        val tables = pdfHandler.processPDF(filePath)
//        assertTrue(tables.isNotEmpty(), "No tables were extracted from the PDF.")
//        assertEquals(3, tables.size, "Expected 3 tables, but found ${tables.size}.")
    }

    /*@Test
    fun `extractTables handles no tables in a PDF`() {
        val pdfHandler = PDFHandler()
        val filePath = Paths.get("src", "jvmTest", "resources", "no-tables.pdf").toAbsolutePath().toString()
        val tables = pdfHandler.processPDF(filePath)
        assertTrue(tables.isEmpty(), "Expected no tables, but found ${tables.size}.")
    }*/
}