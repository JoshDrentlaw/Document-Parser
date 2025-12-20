package org.mossbrosdev.documentparser

import technology.tabula.TableWithRulingLines
import java.awt.geom.Rectangle2D


class TableExtractor(
    private val scale: Float = 1.0f,
    private val regions: MutableList<TableWithRulingLines>
) {
    fun extract() : MutableList<DetectedTable> {
        val extractedRegions = mutableListOf<DetectedTable>()
        for (region in regions) {
            val tableData = region.rows
                .map { row ->
                    row.map { column ->
                        column.text.trim()
                    }.toMutableList()
                }.toMutableList()
            val detectedTable = DetectedTable(
                coordinates = createCoordinates(region),
                data = tableData
            )
            extractedRegions.add(detectedTable)
        }

        return extractedRegions
    }
    private fun createCoordinates(rect: Rectangle2D.Float): Coordinates {
        return Coordinates(
            rect.x * scale,
            rect.y * scale,
            rect.width * scale,
            rect.height * scale
        )
    }
}