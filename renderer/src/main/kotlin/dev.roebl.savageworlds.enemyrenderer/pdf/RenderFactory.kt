package dev.roebl.savageworlds.enemyrenderer.pdf

import dev.roebl.savageworlds.enemyrenderer.pdf.RenderEngine.PDF_BOX

object RenderFactory {
    fun get(engine: RenderEngine): PdfRenderer = when (engine) {
        PDF_BOX -> PdfBoxRenderer()
    }
}

enum class RenderEngine {
    PDF_BOX
}