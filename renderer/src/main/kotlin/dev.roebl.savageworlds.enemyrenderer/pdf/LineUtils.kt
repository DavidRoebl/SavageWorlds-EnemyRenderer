package dev.roebl.savageworlds.enemyrenderer.pdf

import dev.roebl.savageworlds.enemyrenderer.pdf.Settings.pageWidth
import org.apache.pdfbox.pdmodel.PDPageContentStream

fun PDPageContentStream.boxes(
    contents: Array<String>,
    yPos: Float,
    xPos: Float,
    boxSize: Float = 20f,
    lineThickness: Float = 0.3f,
    textIntensity: Float = 0.3f,
    verticalTextOffset: Float = 2f,
    textAlign: Align = Align.CENTER
) {

    contents.forEachIndexed { index, value ->
        rectangle(
            xPos = xPos + boxSize * index,
            yPos = yPos,
            width = boxSize,
            height = boxSize,
            thickness = lineThickness
        )
        text(
            value,
            xPos = xPos + boxSize * (index + 0.5f),
            yPos = yPos + verticalTextOffset,
            intensity = textIntensity,
            align = textAlign
        )
    }
}

fun PDPageContentStream.rectangle(
    xPos: Float,
    yPos: Float,
    width: Float,
    height: Float,
    thickness: Float = 1f
) {
    line(xPos, yPos, xPos + width, yPos, thickness)
    line(xPos + width, yPos, xPos + width, yPos + height, thickness)
    line(xPos + width, yPos + height, xPos, yPos + height, thickness)
    line(xPos, yPos + height, xPos, yPos, thickness)
}

fun PDPageContentStream.horizontalRule(yPos: Float, intensity: Float = 1f) {
    line(0f, yPos, pageWidth, yPos, intensity)
}

fun PDPageContentStream.line(xFrom: Float, yFrom: Float, xTo: Float, yTo: Float, thickness: Float = 1f) {
    setLineWidth(thickness)
    moveTo(xFrom, yFrom * -1)
    lineTo(xTo, yTo * -1)
    stroke()
}

