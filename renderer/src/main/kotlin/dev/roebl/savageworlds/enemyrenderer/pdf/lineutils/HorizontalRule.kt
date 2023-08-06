package dev.roebl.savageworlds.enemyrenderer.pdf.lineutils

import dev.roebl.savageworlds.enemyrenderer.pdf.Settings
import org.apache.pdfbox.pdmodel.PDPageContentStream

fun PDPageContentStream.horizontalRule(yPos: Float, intensity: Float = 1f) {
    line(0f, yPos, Settings.pageWidth, yPos, intensity)
}
