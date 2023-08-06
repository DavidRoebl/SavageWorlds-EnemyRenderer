package dev.roebl.savageworlds.enemyrenderer.pdf.textutils

import dev.roebl.savageworlds.enemyrenderer.pdf.Align
import org.apache.pdfbox.pdmodel.PDPageContentStream

internal fun PDPageContentStream.smallText(
    content: String,
    align: Align = Align.LEFT,
    intensity: Float = 1f,
    xPos: Float,
    yPos: Float
): Float {
    return text(
        content,
        textSize = 9f,
        isBold = true,
        lineSpace = 2f,
        align = align,
        xPos = xPos,
        yPos = yPos,
        intensity = intensity
    )
}