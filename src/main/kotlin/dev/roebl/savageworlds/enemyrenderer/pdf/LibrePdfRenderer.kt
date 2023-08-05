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

