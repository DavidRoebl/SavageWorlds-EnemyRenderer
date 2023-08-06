package dev.roebl.savageworlds.enemyrenderer.pdf

import dev.roebl.savageworlds.enemyrenderer.model.Enemy
import dev.roebl.savageworlds.enemyrenderer.pdf.Align.RIGHT
import dev.roebl.savageworlds.enemyrenderer.pdf.lineutils.boxes
import dev.roebl.savageworlds.enemyrenderer.pdf.lineutils.horizontalRule
import dev.roebl.savageworlds.enemyrenderer.pdf.lineutils.rectangle
import dev.roebl.savageworlds.enemyrenderer.pdf.textutils.annotatedList
import dev.roebl.savageworlds.enemyrenderer.pdf.textutils.map
import dev.roebl.savageworlds.enemyrenderer.pdf.textutils.smallText
import dev.roebl.savageworlds.enemyrenderer.pdf.textutils.text
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.util.Matrix
import java.io.File

private val height = PDRectangle.A6.width
private val width = PDRectangle.A6.height
private val A6_LAND = PDRectangle(width, height)
private const val HANGING_INDENT = "    "

private const val BODY_START = 52f
private const val SECTION_SPACE = 10f

class PdfRenderer(
    private val enemy: Enemy,
    private val icWildcard: ByteArray
) {

    fun renderTo(outFile: File) {
        Settings.pageWidth = width
        Settings.pageHeight = height
        Settings.hangingIntent = HANGING_INDENT

        PDDocument().use { document ->
            val page = PDPage(A6_LAND)
            document.addPage(page)

            PDPageContentStream(document, page).use { stream ->
                pageSetup(stream)

                printTopMatter(stream) {
                    PDImageXObject.createFromByteArray(document, icWildcard, "wildcard")
                }

                printAttributesSkills(stream, xOffset = 15f)
                printGear(stream, xOffset = 100f)
                printSpecialAbilities(stream, xOffset = 210f)

                printBottomMatter(stream)
            }

            document.save(outFile)
        }
    }

    private fun pageSetup(stream: PDPageContentStream) {
        val matrix = Matrix.getTranslateInstance(0f, height)
        stream.transform(matrix)
        stream.rectangle(0f, 0f, width, height)
    }

    private fun printTopMatter(stream: PDPageContentStream, wildcardImage: () -> PDImageXObject) {
        if (enemy.isWildcard) {
            stream.drawImage(wildcardImage(), 12f, -37f, 24f, 24f)
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
    }

    private fun printAttributesSkills(stream: PDPageContentStream, xOffset: Float) {
        var yOffset = BODY_START
        yOffset += stream.smallText("Attributes", xPos = xOffset, yPos = yOffset)
        yOffset += stream.map(
            enemy.attributes.asMap(),
            xPos1 = xOffset,
            xPos2 = xOffset + 50,
            yPos = yOffset
        )
        yOffset += SECTION_SPACE

        if (enemy.skills.isNotEmpty()) {
            yOffset += stream.smallText("Skills", xPos = xOffset, yPos = yOffset)
            yOffset += stream.map(
                enemy.sortedSkills,
                xPos1 = xOffset,
                xPos2 = xOffset + 50,
                yPos = yOffset
            )
            yOffset += SECTION_SPACE
        }
    }

    private fun printGear(stream: PDPageContentStream, xOffset: Float) {
        var yOffset = BODY_START
        if (enemy.gear.isNotEmpty()) {
            yOffset += stream.smallText("Gear", xPos = xOffset, yPos = yOffset)
            yOffset += stream.annotatedList(enemy.gear, xPos = xOffset, yPos = yOffset, maxWidth = 100f)
            yOffset += SECTION_SPACE
        }
    }

    private fun printSpecialAbilities(stream: PDPageContentStream, xOffset: Float) {
        var yOffset = BODY_START
        if (enemy.specialAbilities.isNotEmpty()) {
            yOffset += stream.smallText("Special Abilities", xPos = xOffset, yPos = yOffset)
            yOffset += stream.annotatedList(
                enemy.specialAbilities,
                xPos = xOffset,
                yPos = yOffset,
                maxWidth = width - 210f
            )
            yOffset += SECTION_SPACE
        }
    }


    private fun printBottomMatter(stream: PDPageContentStream) {
        if (enemy.isWildcard) {
            stream.smallText("Wounds", xPos = 100f, yPos = height - 45f)
            stream.boxes(
                contents = Array(3) { "${-(it + 1)}" },
                xPos = 100f,
                yPos = height - 33
            )
            stream.text("INC", xPos = 164f, yPos = height - 31)
            stream.smallText("Fatigue", xPos = 229f, yPos = height - 45f, align = RIGHT)
            stream.boxes(
                contents = Array(2) { "${it - 2}" },
                xPos = 189f,
                yPos = height - 33
            )

        } else {
            stream.smallText("Wounds, etc", xPos = 100f, yPos = height - 45f)
            stream.boxes(
                contents = Array(10) { "${it + 1}" },
                xPos = 100f,
                yPos = height - 33
            )
        }

        stream.smallText(content = "Pace", xPos = width - 95, yPos = height - 38f, align = RIGHT)
        stream.text(content = enemy.pace, xPos = width - 95, yPos = height - 27f, align = RIGHT)
        stream.smallText(content = "Parry", xPos = width - 65, yPos = height - 38f, align = RIGHT)
        stream.text(content = enemy.parry, xPos = width - 65, yPos = height - 27f, align = RIGHT)
        stream.smallText(content = "Toughness", xPos = width - 15, yPos = height - 38f, align = RIGHT)
        stream.text(content = enemy.toughness, xPos = width - 15, yPos = height - 27f, align = RIGHT)
    }
}
