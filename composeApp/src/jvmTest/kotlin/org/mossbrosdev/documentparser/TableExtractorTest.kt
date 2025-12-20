package org.mossbrosdev.documentparser

class TableExtractorTest {

    /*@Test
    fun `extractTables can find a single table in a PDF`() {
        val pdfHandler = PDFHandler()
        val filePath = Paths.get("src", "jvmTest", "resources", "one-table.pdf").toAbsolutePath().toString()
        pdfHandler.loadDocument(PlatformFile(filePath))
        val document = pdfHandler.processPDF()
        assertTrue(document.pages.isNotEmpty(), "No pages were extracted from the PDF.")

        val page = document.pages.first()
        assertTrue(page.tables.isNotEmpty(), "No tables were extracted from the PDF.")
        assertEquals(1, page.tables.size, "Expected 1 table, but found ${page.tables.size}.")

        val table = page.tables.first()

        assertEquals(5, table.rows.size, "Expected 5 rows, but found ${table.rows.size}.")
        assertEquals(
            listOf("", "Continent", "Capital", "Currency", "Population"),
            table.rows.map { it.first() }
        )
        assertEquals(6, table.rows.first().size)
        assertEquals(
            listOf("Capital", "Jakarta", "Berlin", "Vienna", "Paris", "Vatican City"),
            table.rows[2].map { it }
        )
    }

    @Test
    fun `extractTables can find multiple tables in a PDF`() {
        val pdfHandler = PDFHandler()
        val filePath = Paths.get("src", "jvmTest", "resources", "multiple-tables.pdf").toAbsolutePath().toString()
        pdfHandler.loadDocument(PlatformFile(filePath))
        val document = pdfHandler.processPDF()
        assertTrue(document.pages.isNotEmpty(), "No pages were extracted from the PDF.")
        assertEquals(2, document.pages.size, "Expected 2 pages, but found ${document.pages.size}.")

        val firstPage = document.pages.first()
        val secondPage = document.pages[1]
        assertTrue(firstPage.tables.isNotEmpty(), "No tables were extracted from the first page.")
        assertTrue(secondPage.tables.isNotEmpty(), "No tables were extracted from the second page.")

        val tables = firstPage.tables + secondPage.tables
        assertEquals(3, tables.size, "Expected 3 tables, but found ${tables.size}.")
    }

    @Test
    fun `extractTables handles no tables in a PDF`() {
        val pdfHandler = PDFHandler()
        val filePath = Paths.get("src", "jvmTest", "resources", "no-tables.pdf").toAbsolutePath().toString()
        pdfHandler.loadDocument(PlatformFile(filePath))
        val document = pdfHandler.processPDF()
        assertEquals(1, document.pages.size, "Expected 1 page, but found ${document.pages.size}.")

        val page = document.pages.first()
        assertTrue(page.tables.isEmpty(), "Expected no tables, but found ${page.tables.size}.")
    }

    @Test
    fun `extractTables handles IO exceptions`() {
        val pdfHandler = PDFHandler()
        val filePath = Paths.get("src", "jvmTest", "resources", "non-existent-file.pdf").toAbsolutePath().toString()
        pdfHandler.loadDocument(PlatformFile(filePath))
        val document = pdfHandler.processPDF()
        assertEquals(0, document.pages.size, "Expected 0 pages, but found ${document.pages.size}.")
    }*/
}