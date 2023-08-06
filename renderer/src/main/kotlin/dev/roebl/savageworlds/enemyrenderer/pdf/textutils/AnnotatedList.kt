package dev.roebl.savageworlds.enemyrenderer.pdf.textutils

import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import kotlin.math.min

fun PDPageContentStream.annotatedList(
    map: Map<String, String>,
    yPos: Float,
    xPos: Float,
    maxWidth: Float = 150f
): Float {
    val font = font()
    beginText()
    newLineAtOffset(xPos, -1 * yPos - 12f)
    var overflowLines = 0
    map.forEach { (entry, annotation) ->
        overflowLines += listEntry(
            font = font,
            entry = "$entry ",
            annotation = annotation,
            maxWidth = maxWidth
        )
    }
    endText()
    return map.size * 15f + overflowLines * 10f
}

private fun PDPageContentStream.listEntry(
    font: PDType1Font,
    entry: String,
    annotation: String,
    maxWidth: Float,
    indent: String = "  "
): Int {
    val entryWidth = font.widthOf(entry, 12f)
    val annotationWidth = font.widthOf(annotation, 9f)

    if (entryWidth + annotationWidth <= maxWidth) {
        // both fit into a single line
        setLeading(15f)
        setFont(font, 12f)
        showText(entry)
        setFont(font, 9f)
        showText(annotation)
        newLine()
        return 0
    }

    // need to put some part of annotation into next line
    val indentWidth = font.widthOf(indent, 9f)
    if (indentWidth + annotationWidth <= maxWidth) {
        // whole annotation fits into next line
        setLeading(10f)
        setFont(font, 12f)
        showText(entry)
        newLine()
        setLeading(15f)
        setFont(font, 9f)
        showText(indent)
        showText(annotation)
        newLine()
        return 1
    }

    // annotation does not fit into a single line --> need to split it
    val annotationLines = AnnotationLines()
    val annotationList = annotation.split(" ").toTypedArray()
    val firstLineSize = findElementsThatFit(
        input = annotationList,
        font = font,
        maxWidth = maxWidth - entryWidth,
        startIndex = 0
    )
    annotationLines.firstLine = annotationList.take(firstLineSize).recombine()
    var startIndex = firstLineSize
    while (startIndex <= annotationList.size) {
        val lineSize = findElementsThatFit(
            input = annotationList,
            font = font,
            maxWidth = maxWidth,
            startIndex = startIndex,
            indent = "  "
        )
        if (lineSize == 0) {
            break
        }
        val line = annotationList.copyOfRange(startIndex, startIndex + lineSize).recombine().prependIndent("  ")
        annotationLines.lines.add(line)

        startIndex += lineSize
    }

    setLeading(10f)
    setFont(font, 12f)
    showText(entry)
    setFont(font, 9f)
    showText(annotationLines.firstLine)
    annotationLines.lines.forEach {
        newLine()
        showText(it)
    }
    setLeading(15f)
    newLine()
    return annotationLines.lines.size
}

private fun findElementsThatFit(
    input: Array<String>,
    font: PDType1Font,
    maxWidth: Float,
    startIndex: Int = 0,
    indent: String = "  "
): Int {
    val widthOfIndent = font.widthOf(indent, 9f)

    var toIndex = startIndex
    do {
        toIndex += 1
        val string = input.copyOfRange(startIndex, min(toIndex, input.size)).recombine()
        val width = font.widthOf(string, 9f) + widthOfIndent

    } while (toIndex <= input.size && width <= maxWidth)

    return toIndex - startIndex - 1
}

private fun List<String>.recombine(delimiter: String = " "): String {
    return toTypedArray().recombine(delimiter)
}

private fun Array<String>.recombine(delimiter: String = " "): String {
    return if (isEmpty()) {
        return ""
    } else {
        reduce { first, second ->
            "$first$delimiter$second"
        }
    }
}

private class AnnotationLines {
    var firstLine: String = ""
    val lines: MutableList<String> = mutableListOf()
}