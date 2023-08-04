package dev.roebl.savageworlds.enemyrenderer.pdf

import com.lowagie.text.*
import com.lowagie.text.alignment.HorizontalAlignment
import com.lowagie.text.pdf.PdfWriter
import dev.roebl.savageworlds.enemyrenderer.model.Enemy
import java.awt.Color
import java.io.File
import java.net.URL


private val small = FontFactory.getFont(FontFactory.HELVETICA, 7f, Color.BLACK)
private val medium = FontFactory.getFont(FontFactory.HELVETICA, 9f, Color.BLACK)
private val large = FontFactory.getFont(FontFactory.HELVETICA, 12f, Color.BLACK)

class LibrePdfRenderer : PdfRenderer {
    override fun render(enemy: Enemy, outFile: File, icWildcard: String) {

        val outStream = outFile.outputStream()
        val pdfDocument = Document(PageSize.A6.rotate(), 10f, 10f, 10f, 10f)
        val pdfWriter = PdfWriter.getInstance(pdfDocument, outStream)

        pdfDocument.open()
        pdfDocument.apply {
            Chunk("foobar").also { add(it) }
            Chunk("foobar").also { add(it) }
            Chunk("foobar").also { add(it) }
            Paragraph().apply {
                if(enemy.isWildcard) {

                    add(Jpeg(File(icWildcard).readBytes()))
                    add(Chunk("[W]", large))
                } else {
                    add(Chunk("   ", large))
                }
                add(Chunk(" "))
                add(Chunk(enemy.name, large))
            }.also { add(it) }

            Table(4).apply {
                setHorizontalAlignment(HorizontalAlignment.LEFT)
                width = 100f
                backgroundColor = Color.GREEN
                Cell(
                    Table(2).apply {
                        borderWidth = 0f
                        enemy.skills.forEach { (skill, die) ->
                            addCell(Cell(Chunk(skill, small)).apply { borderWidth = 0f })
                            addCell(Cell(Chunk(die.name, medium)).apply { borderWidth = 0f })
                        }
                    }).also { addCell(it) }
                addCell(Cell("foobaretc").apply { borderWidth = 2f })
                addCell(Cell("foobaretc").apply { borderWidth = 2f })
                addCell(Cell("foobaretc").apply { borderWidth = 2f })
            }.also { add(it) }

        }
        pdfDocument.close()

//        pdfDocument.compose {
//            Paragraph(enemy.name, Style(fontFamily = BaseFont.HELVETICA_BOLD))
//            Table(2, 1) {
//                Cell(content =
//                Table(2, enemy.skills.size) {
//                    raw.apply {
//                        setWidths(floatArrayOf(0.7f, 0.3f))
//                        padding = 1f
//                        spacing = -3f
//                    }
//                    enemy.skills.forEach { (skill, die) ->
//                        Cell(skill) {
//                            width = 0.5f
//                        }
//                        Cell(die.name)
//                    }
//                }
//                )
//            }
//        }
        pdfWriter.close()
    }
}
