package dev.roebl.savageworlds.enemyrenderer.pdf.lineutils

import dev.roebl.savageworlds.enemyrenderer.pdf.Align
import dev.roebl.savageworlds.enemyrenderer.pdf.textutils.text
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
