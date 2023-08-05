package dev.roebl.savageworlds.enemyrenderer.pdf

import dev.roebl.savageworlds.enemyrenderer.model.Enemy
import java.io.File

interface PdfRenderer {
    fun render(enemy: Enemy, outFile: File, icWildcard: ByteArray)
}