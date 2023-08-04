package dev.roebl.savageworlds.enemyrenderer

import com.google.gson.GsonBuilder
import dev.roebl.savageworlds.enemyrenderer.model.Enemy
import dev.roebl.savageworlds.enemyrenderer.pdf.RenderEngine
import dev.roebl.savageworlds.enemyrenderer.pdf.RenderFactory
import java.awt.Color
import java.io.File

private val inFolder = "D:\\Google Drive\\Pen & Paper\\Savage Worlds\\Slipstream\\season one\\Enemies\\json"
private val outFolder = "D:\\Google Drive\\Pen & Paper\\Savage Worlds\\Slipstream\\season one\\Enemies\\pdf"
private val files = listOf(
    "example"
)
private val resourcesFolder = "D:\\Google Drive\\Pen & Paper\\Savage Worlds\\Slipstream\\season one\\Enemies\\resources"
private val iconsFolder = File(resourcesFolder, "icons").absolutePath

fun main(args: Array<String>) {
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()
    val icWildcard = File(iconsFolder, "wildcard.jpg").absolutePath
    val renderEngine = RenderFactory.get(RenderEngine.PDF_BOX)

    files.forEach { enemyFileName ->
        val file = File(inFolder, "$enemyFileName.json")
        val reader = file.reader()
        val enemy = gson.fromJson(reader, Enemy::class.java)
        println(enemy)

        val outFile = File(outFolder, "$enemyFileName.pdf")
        renderEngine.render(enemy, outFile, icWildcard)
    }
}

    fun color(alpha: Int = 0xFF, red: Int = 0, green: Int = 0, blue: Int = 0): Color = Color(red, green, blue, alpha)
