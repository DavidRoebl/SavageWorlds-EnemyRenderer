package dev.roebl.savageworlds.enemyrenderer.pdf

import dev.roebl.savageworlds.enemyrenderer.model.Die
import dev.roebl.savageworlds.enemyrenderer.model.Enemy
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import java.io.File

val height = PDRectangle.A6.width
val width = PDRectangle.A6.height
val A6_LAND = PDRectangle(width, height)

class PdfBoxRenderer : PdfRenderer {
    override fun render(enemy: Enemy, outFile: File, icWildcard: String) {
        PDDocument().use { document ->
            val page = PDPage(A6_LAND)
            document.addPage(page)

            PDPageContentStream(document, page).use { stream ->
                if (enemy.isWildcard) {
                    val image = PDImageXObject.createFromFile(icWildcard, document)
                    stream.drawImage(image, 10f, height - 37, 24f, 24f)
                }

                stream.text(
                    content = enemy.name,
                    textSize = 24f,
                    xPos = 40f,
                    yPos = height - 34
                )

                stream.horizontalRule(height - 44f)

                stream.renderMap(enemy.attributes.asMap(), height - 65, 55f)
                stream.renderMap(enemy.sortedSkills, height - 145, 55f)

            }

            document.save(outFile)
        }
    }

    private fun PDPageContentStream.renderMap(map: Map<String, Die>, yStart: Float, xCenter: Float) {
        var yPos = yStart
        map.forEach { (attribute, die) ->
            val width = PDType1Font.HELVETICA.getStringWidth(attribute) / 1000.0f * 12f
            text(
                content = attribute,
                xPos = xCenter - width - 2,
                yPos = yPos
            )
            text(
                content = die.name,
                xPos = xCenter + 2,
                yPos = yPos
            )
            yPos -= 14
        }
    }

    private fun PDPageContentStream.text(content: String, textSize: Float = 12f, xPos: Float, yPos: Float) {
        beginText()
        setFont(PDType1Font.HELVETICA, textSize)
        newLineAtOffset(xPos, yPos)
        showText(content)
        endText()
    }

    private fun PDPageContentStream.horizontalRule(yPos: Float) {
        line(0f, yPos, width, yPos)
    }

    private fun PDPageContentStream.line(xFrom: Float, yFrom: Float, xTo: Float, yTo: Float) {
        moveTo(xFrom, yFrom)
        lineTo(xTo, yTo)
        stroke()
    }
}
