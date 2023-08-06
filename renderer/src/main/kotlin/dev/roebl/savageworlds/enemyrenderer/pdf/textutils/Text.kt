package dev.roebl.savageworlds.enemyrenderer.pdf.textutils

import dev.roebl.savageworlds.enemyrenderer.pdf.Align
import dev.roebl.savageworlds.enemyrenderer.pdf.Settings
import org.apache.pdfbox.pdmodel.PDPageContentStream


internal fun PDPageContentStream.text(
    content: String,
    textSize: Float = 12f,
    lineSpace: Float = 2f,
    isBold: Boolean = false,
    align: Align = Align.LEFT,
    intensity: Float = 1f,
    xPos: Float,
    yPos: Float
): Float {
    val strings = content.split("\n").mapIndexed { index, it ->
        if (index == 0) {
            it
        } else {
            it.prependIndent(Settings.hangingIntent)
        }
    }
    val font = font(isBold)
    val _yPos = yPos * -1 - textSize
    val _xPos = when (align) {
        Align.LEFT -> xPos
        Align.RIGHT -> xPos - font.widthOf(content, textSize)
        Align.CENTER -> xPos - font.widthOf(content, textSize) / 2
    }
    beginText()
    setFont(font, textSize)
    setNonStrokingColor(1 - intensity, 1 - intensity, 1 - intensity)
    newLineAtOffset(_xPos, _yPos)
    setLeading(textSize + lineSpace)
    strings.forEach {
        showText(it)
        newLine()
    }
    setNonStrokingColor(0f, 0f, 0f)
    endText()
    return strings.size * (textSize + lineSpace)
}