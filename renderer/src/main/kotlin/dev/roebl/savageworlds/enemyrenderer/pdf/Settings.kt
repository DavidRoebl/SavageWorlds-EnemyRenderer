package dev.roebl.savageworlds.enemyrenderer.pdf

import org.apache.pdfbox.pdmodel.font.PDType1Font

object Settings {
    var hangingIntent: String = "    "
    var pageWidth: Float = 0f
    var pageHeight: Float = 0f
    var font: PDType1Font = PDType1Font.HELVETICA
    var fontBold: PDType1Font = PDType1Font.HELVETICA_BOLD
}
