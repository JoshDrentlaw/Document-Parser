package org.mossbrosdev.documentparser

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.absolutePath
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import technology.tabula.Table

class PDFHandler {
    suspend fun openFilePicker() : String? {
        val file = FileKit.openFilePicker(
            type = FileKitType.File("pdf"),
            directory = PlatformFile(System.getProperty("user.dir")),
        )

        return file?.absolutePath()
    }

    fun processPDF(pdfFilePath: String) : DocumentData {
        val tableExtractor = TableExtractor()
        val tables = tableExtractor.extractTables(pdfFilePath)

        return tables
    }
}