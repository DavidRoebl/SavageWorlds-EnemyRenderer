package dev.roebl.savageworlds.enemyrenderer.pdf.textutils

import org.apache.pdfbox.pdmodel.PDPageContentStream

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
