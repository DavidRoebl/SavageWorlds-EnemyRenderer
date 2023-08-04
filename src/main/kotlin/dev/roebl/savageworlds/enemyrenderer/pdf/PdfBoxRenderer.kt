package dev.roebl.savageworlds.enemyrenderer.pdf

import dev.roebl.savageworlds.enemyrenderer.model.Die
import dev.roebl.savageworlds.enemyrenderer.model.Enemy
import dev.roebl.savageworlds.enemyrenderer.pdf.PdfBoxRenderer.Align.LEFT
import dev.roebl.savageworlds.enemyrenderer.pdf.PdfBoxRenderer.Align.RIGHT
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.util.Matrix
import java.io.File

val height = PDRectangle.A6.width
val width = PDRectangle.A6.height
val A6_LAND = PDRectangle(width, height)
private const val HANGING_INDENT = "    "

private const val BODY_START = 52f
private const val SECTION_SPACE = 10f

class PdfBoxRenderer : PdfRenderer {
    override fun render(enemy: Enemy, outFile: File, icWildcard: String) {
        PDDocument().use { document ->
            val page = PDPage(A6_LAND)
            document.addPage(page)

            PDPageContentStream(document, page).use { stream ->
                val matrix = Matrix.getTranslateInstance(0f, height)
                stream.transform(matrix)

                stream.rectangle(0f, 0f, width, height)

                if (enemy.isWildcard) {
                    val image = PDImageXObject.createFromFile(icWildcard, document)
                    stream.drawImage(image, 12f, -37f, 24f, 24f)
                }
                stream.text(
                    content = enemy.name,
                    textSize = 24f,
                    xPos = if (enemy.isWildcard) 42f else 10f,
                    yPos = 10f
                )

                stream.smallText(content = "Salvage", xPos = width - 15, yPos = 13f, align = RIGHT)
                stream.text(content = enemy.salvage.pretty, xPos = width - 15, yPos = 24f, align = RIGHT)

                stream.horizontalRule(44f)


                // column 1
                var yOffset = BODY_START
                var xOffset = 15f
                yOffset += stream.smallText("Attributes", xPos = xOffset, yPos = yOffset)
                yOffset += stream.dieMap(
                    enemy.attributes.asMap(),
                    xPos1 = xOffset,
                    xPos2 = xOffset + 50,
                    yPos = yOffset
                )
                yOffset += SECTION_SPACE

                if (enemy.skills.isNotEmpty()) {
                    yOffset += stream.smallText("Skills", xPos = xOffset, yPos = yOffset)
                    yOffset += stream.dieMap(
                        enemy.sortedSkills,
                        xPos1 = xOffset,
                        xPos2 = xOffset + 50,
                        yPos = yOffset
                    )
                    yOffset += SECTION_SPACE
                }

                // column 2
                yOffset = BODY_START
                xOffset = 100f
                if (enemy.equipment.isNotEmpty()) {
                    yOffset += stream.smallText("Equipment", xPos = xOffset, yPos = yOffset)
                    yOffset += stream.annotatedList(enemy.equipment, xPos = xOffset, yPos = yOffset)
                    yOffset += SECTION_SPACE
                }

                if (enemy.powers.isNotEmpty()) {
                    yOffset += stream.smallText("Powers", xPos = xOffset, yPos = yOffset)
                    yOffset += stream.annotatedList(enemy.powers, xPos = xOffset, yPos = yOffset)
                    yOffset += SECTION_SPACE
                }

                // column 3
                yOffset = BODY_START
                xOffset = width - 160
                if (enemy.hindrances.isNotEmpty()) {
                    yOffset += stream.smallText("Hindrances", xPos = xOffset, yPos = yOffset)
                    yOffset += stream.annotatedList(enemy.hindrances, xPos = xOffset, yPos = yOffset)
                    yOffset += SECTION_SPACE
                }
                if (enemy.edges.isNotEmpty()) {
                    yOffset += stream.smallText("Edges", xPos = xOffset, yPos = yOffset)
                    yOffset += stream.annotatedList(enemy.edges, xPos = xOffset, yPos = yOffset)
                    yOffset += SECTION_SPACE
                }
                if (enemy.notes.isNotEmpty()) {
                    yOffset += stream.smallText("Notes", xPos = xOffset, yPos = yOffset)
                    yOffset += stream.annotatedList(enemy.notes, xPos = xOffset, yPos = yOffset)
                    yOffset += SECTION_SPACE
                }

                stream.smallText("Wounds", xPos = 100f, yPos = height - 45f)
                stream.rectangle(xPos = 100f, yPos = height - 33, width = 20f, height = 20f)
                stream.text("-1", xPos = 104f, yPos = height - 31)
                stream.rectangle(xPos = 120f, yPos = height - 33, width = 20f, height = 20f)
                stream.text("-2", xPos = 124f, yPos = height - 31)
                stream.rectangle(xPos = 140f, yPos = height - 33, width = 20f, height = 20f)
                stream.text("-3", xPos = 144f, yPos = height - 31)

                stream.text("INC", xPos = 164f, yPos = height - 31)
                stream.smallText("Fatigue", xPos = 229f, yPos = height - 45f, align = RIGHT)
                stream.rectangle(xPos = 189f, yPos = height - 33, width = 20f, height = 20f)
                stream.text("-2", xPos = 193f, yPos = height - 31)
                stream.rectangle(xPos = 209f, yPos = height - 33, width = 20f, height = 20f)
                stream.text("-1", xPos = 213f, yPos = height - 31)



                stream.smallText(content = "Pace", xPos = width - 95, yPos = height - 38f, align = RIGHT)
                stream.text(content = enemy.pace, xPos = width - 95, yPos = height - 27f, align = RIGHT)
                stream.smallText(content = "Parry", xPos = width - 65, yPos = height - 38f, align = RIGHT)
                stream.text(content = enemy.parry, xPos = width - 65, yPos = height - 27f, align = RIGHT)
                stream.smallText(content = "Toughness", xPos = width - 15, yPos = height - 38f, align = RIGHT)
                stream.text(content = enemy.toughness, xPos = width - 15, yPos = height - 27f, align = RIGHT)
            }

            document.save(outFile)
        }
    }

    private fun PDPageContentStream.annotatedList(
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

    private fun PDPageContentStream.dieMap(
        map: Map<String, Die>,
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
                align = LEFT,
                textSize = textSize,
                lineSpace = lineSpace,
                isBold = isBold
            )
            text(
                content = value.name,
                xPos = xPos2,
                yPos = _yPos,
                align = LEFT,
                textSize = textSize2,
                lineSpace = lineSpace,
                isBold = isBold
            )
            _yPos += textSize + lineSpace
        }
        return _yPos - yPos
    }

    private fun PDPageContentStream.smallText(
        content: String,
        align: Align = LEFT,
        xPos: Float,
        yPos: Float
    ): Float {
        return text(content, textSize = 9f, isBold = true, lineSpace = 2f, align = align, xPos = xPos, yPos = yPos)
    }

    private fun PDPageContentStream.text(
        content: String,
        textSize: Float = 12f,
        lineSpace: Float = 2f,
        isBold: Boolean = false,
        align: Align = LEFT,
        xPos: Float,
        yPos: Float
    ): Float {
        val strings = content.split("\n").mapIndexed { index, it ->
            if (index == 0) {
                it
            } else {
                it.prependIndent(HANGING_INDENT)
            }
        }
        val font = font(isBold)
        val _yPos = yPos * -1 - textSize
        val _xPos = when (align) {
            LEFT -> xPos
            RIGHT -> xPos - font.widthOf(content, textSize)
        }
        beginText()
        setFont(font, textSize)
        newLineAtOffset(_xPos, _yPos)
        setLeading(textSize + lineSpace)
        strings.forEach {
            showText(it)
            newLine()
        }
        endText()
        return strings.size * (textSize + lineSpace)
    }

    private fun PDPageContentStream.rectangle(xPos: Float, yPos: Float, width: Float, height: Float) {
        line(xPos, yPos, xPos + width, yPos)
        line(xPos + width, yPos, xPos + width, yPos + height)
        line(xPos + width, yPos + height, xPos, yPos + height)
        line(xPos, yPos + height, xPos, yPos)
    }

    private fun PDPageContentStream.horizontalRule(yPos: Float) {
        line(0f, yPos, width, yPos)
    }

    private fun PDPageContentStream.line(xFrom: Float, yFrom: Float, xTo: Float, yTo: Float) {
        moveTo(xFrom, yFrom * -1)
        lineTo(xTo, yTo * -1)
        stroke()
    }

    enum class Align {
        LEFT, RIGHT
    }

    fun PDType1Font.widthOf(text: String, fontSize: Float): Float {
        return getStringWidth(text) / 1000.0f * fontSize
    }

    fun String.capitalize(): String {
        return replaceFirstChar { it.uppercaseChar() }
    }

    fun font(isBold: Boolean = false): PDType1Font = if (isBold) PDType1Font.HELVETICA_BOLD else PDType1Font.HELVETICA
}
