package org.mossbrosdev.documentparser.pdf.ports

import org.mossbrosdev.documentparser.pdf.common.Coordinates
import org.mossbrosdev.documentparser.pdf.extraction.TableRegion
import technology.tabula.TableWithRulingLines
import java.util.UUID

class TableExtractor(
    private val scale: Float = 1.0f,
    private val regions: MutableList<TableWithRulingLines>
) {
    fun extract() : MutableList<TableRegion> {
        val extractedRegions = mutableListOf<TableRegion>()

        for (region in regions) {
            val coords = Coordinates.Factory.fromRect(region, scale)
            val uuid = UUID.randomUUID().toString()

            val tableRegion = runCatching {
                val tableData = region.rows .map { row -> row.map { cell -> cell.text.trim() } }

                TableRegion.Extracted(
                    id = uuid,
                    coordinates = coords,
                    data = tableData,
                )
            }.getOrElse { t ->
                TableRegion.Failed(
                    id = uuid,
                    coordinates = coords,
                    reason = "Failed to extract table data",
                    throwable = t
                )
            }

            extractedRegions.add(tableRegion)
        }

        return extractedRegions
    }
}