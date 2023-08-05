package dev.roebl.savageworlds.enemyrenderer.pdf

import dev.roebl.savageworlds.enemyrenderer.model.Enemy
import java.io.File
import java.net.URL

interface PdfRenderer {
    fun render(enemy: Enemy, outFile: File, icWildcard: ByteArray)
}