package dev.roebl.savageworlds.enemyrenderer.pdf

import dev.roebl.savageworlds.enemyrenderer.pdf.RenderEngine.LIBRE_PDF
import dev.roebl.savageworlds.enemyrenderer.pdf.RenderEngine.PDF_BOX

object RenderFactory {
    fun get(engine: RenderEngine): PdfRenderer = when (engine) {
        LIBRE_PDF -> LibrePdfRenderer()
        PDF_BOX -> PdfBoxRenderer()
    }
}

enum class RenderEngine {
    LIBRE_PDF,
    PDF_BOX
}