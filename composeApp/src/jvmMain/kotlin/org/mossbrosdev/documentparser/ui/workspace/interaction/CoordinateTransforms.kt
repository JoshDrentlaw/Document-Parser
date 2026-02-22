package org.mossbrosdev.documentparser.ui.workspace.interaction

import androidx.compose.ui.geometry.Offset
import org.mossbrosdev.documentparser.ui.workspace.state.ViewportState

fun viewToWorld(view: Offset, state: ViewportState): Offset {
    return (view - state.translation) / state.scale
}

fun worldToView(world: Offset, state: ViewportState): Offset {
    return (world * state.scale) + state.translation
}