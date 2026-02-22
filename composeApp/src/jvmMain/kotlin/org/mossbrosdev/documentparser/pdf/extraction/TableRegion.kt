package org.mossbrosdev.documentparser.pdf.extraction

import org.mossbrosdev.documentparser.pdf.common.Coordinates
import technology.tabula.TableWithRulingLines

sealed interface TableRegion {
    val id: String
    val coordinates: Coordinates

    /** Table location exists, but not extracted text yet (or extraction intentionally skipped) */
    data class Detected(
        override val id: String,
        override val coordinates: Coordinates,
        val source: TableWithRulingLines? = null,
    ) : TableRegion

    /** Table location + extracted text matrix */
    data class Extracted(
        override val id: String,
        override val coordinates: Coordinates,
        val data: List<List<String>> = mutableListOf(),
    ) : TableRegion

    /** Table location + extraction failure details */
    data class Failed(
        override val id: String,
        override val coordinates: Coordinates,
        val reason: String,
        val throwable: Throwable,
    ) : TableRegion
}