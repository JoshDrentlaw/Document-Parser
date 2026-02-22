package org.mossbrosdev.documentparser.pdf.common

import java.awt.geom.Rectangle2D

data class Coordinates(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
) {

    companion object Factory {
        fun fromRect(rect: Rectangle2D.Float, scale: Float): Coordinates {
            return Coordinates(
                rect.x * scale,
                rect.y * scale,
                rect.width * scale,
                rect.height * scale
            )
        }
    }
}