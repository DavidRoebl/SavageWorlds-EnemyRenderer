package dev.roebl.savageworlds.enemyrenderer.pdf.textutils

import dev.roebl.savageworlds.enemyrenderer.pdf.Settings
import org.apache.pdfbox.pdmodel.font.PDType1Font

internal fun PDType1Font.widthOf(text: String, fontSize: Float): Float {
    return getStringWidth(text) / 1000.0f * fontSize
}

internal fun String.capitalize(): String {
    return replaceFirstChar { it.uppercaseChar() }
}

internal fun font(isBold: Boolean = false): PDType1Font =
    if (isBold) Settings.fontBold else Settings.font