package dev.roebl.savageworlds.enemyrenderer.pdf

import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font


fun PDPageContentStream.annotatedList(
    map: Map<String, String>,
    yPos: Float,
    xPos: Float,
    maxWidth: Float = 150f
): Float {
    val font = font()
    beginText()
    setLeading(15f)
    newLineAtOffset(xPos, -1 * yPos - 12f)
    var overflows = 0
    map.forEach { (entry, annotation) ->
        val width = with(font) {
            widthOf("$entry ", 12f) + widthOf(annotation, 9f)
        }

        setFont(font, 12f)
        showText("$entry ")
        if (width > maxWidth) {
            setLeading(10f)
            newLine()
            setLeading(14f)
            overflows += 1
            showText("  ")
        }

        setFont(font, 9f)
        showText(annotation)
        newLine()
    }
    endText()
    return map.size * 14f + overflows * 10f
}

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

fun PDPageContentStream.smallText(
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

fun PDPageContentStream.text(
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

private fun PDType1Font.widthOf(text: String, fontSize: Float): Float {
    return getStringWidth(text) / 1000.0f * fontSize
}

private fun String.capitalize(): String {
    return replaceFirstChar { it.uppercaseChar() }
}

private fun font(isBold: Boolean = false): PDType1Font =
    if (isBold) PDType1Font.HELVETICA_BOLD else PDType1Font.HELVETICA