package dev.roebl.savageworlds.enemyrenderer.pdf.textutils

import dev.roebl.savageworlds.enemyrenderer.pdf.Align
import org.apache.pdfbox.pdmodel.PDPageContentStream

fun PDPageContentStream.map(
    map: Map<String, Any>,
    isBold: Boolean = false,
    textSize: Float = 12f,
    textSize2: Float = 12f,
    lineSpace: Float = 2f,
    yPos: Float,
    xPos1: Float,
    xPos2: Float = xPos1 + map.keys.map { it.capitalize() }.maxOf { font(isBold).widthOf(it, textSize) } + 4,
): Float {
    var _yPos = yPos

    map.forEach { (key, value) ->
        text(
            content = key.replaceFirstChar { it.uppercaseChar() },
            xPos = xPos1,
            yPos = _yPos,
            align = Align.LEFT,
            textSize = textSize,
            lineSpace = lineSpace,
            isBold = isBold
        )
        text(
            content = value.toString(),
            xPos = xPos2,
            yPos = _yPos,
            align = Align.LEFT,
            textSize = textSize2,
            lineSpace = lineSpace,
            isBold = isBold
        )
        _yPos += textSize + lineSpace
    }
    return _yPos - yPos
}
