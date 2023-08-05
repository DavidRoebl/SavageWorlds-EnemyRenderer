package dev.roebl.savageworlds.enemyrenderer

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.boolean
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import dev.roebl.savageworlds.enemyrenderer.cli.InputException
import dev.roebl.savageworlds.enemyrenderer.model.Enemy
import dev.roebl.savageworlds.enemyrenderer.pdf.RenderEngine
import dev.roebl.savageworlds.enemyrenderer.pdf.RenderFactory
import org.apache.pdfbox.io.MemoryUsageSetting
import org.apache.pdfbox.multipdf.PDFMergerUtility
import java.io.File
import java.io.IOException
import java.time.OffsetDateTime


class EnemyRenderer : CliktCommand(
    // todo
) {
    private val inputDir: String by option().prompt("Path to folder containing enemy JSON files")
        .help("Path to folder containing enemy JSON files.")
    private val selectorFile: String by option().prompt("Path to file with list of the names of enemy JSONs to process")
        .help("File that has all enemy names (=filenames) that should be processed. Expects all files to be valid JSON enemies.")
    private val outputDir: String by option().prompt("Path to folder where PDF files should be stored")
        .help("Output directory. Generated PDF files will be stored there, existing files may be overwritten.")
    private val mergeOutput: Boolean by option().boolean().prompt("Merge output PDFs to a single PDF for printing?")

    private val icWildcard = this::class.java.classLoader.getResource("wildcard.jpg")!!.readBytes()


    override fun run() = try {
        val files = try {
            File(selectorFile).readLines()
        } catch (e: IOException) {
            throw InputException("Cannot open selector file at path '$selectorFile'", e)
        }

        val renderEngine = RenderFactory.get(RenderEngine.PDF_BOX)
        val gson = Gson()

        files.forEach { rawFileName ->
            val enemyFileName = rawFileName.removeSuffix(".json")
            if (enemyFileName.isBlank()) {
                return@forEach // do not process
            }

            val enemyFile = File(inputDir, "$enemyFileName.json")

            val reader = try {
                enemyFile.reader()
            } catch (e: IOException) {
                throw InputException("Could not open enemy file: '$enemyFile'", e)
            }

            val enemy = try {
                gson.fromJson(reader, Enemy::class.java)
            } catch (e: JsonSyntaxException) {
                throw InputException("Invalid JSON input in file '$enemyFile'", e)
            } catch (e: JsonIOException) {
                throw InputException("Could not read file at '$enemyFile'", e)
            }

            val outFile = File(outputDir, "$enemyFileName.pdf")
            renderEngine.render(enemy, outFile, icWildcard)
            println("Rendered $enemyFileName.")
        }

        if (mergeOutput) {
            val merger = PDFMergerUtility()
            val mergeFile = "merged-$timestamp.pdf"
            merger.destinationFileName = File(outputDir, mergeFile).absolutePath
            merger.addSources(
                files.map { File(outputDir, "$it.pdf").inputStream() }
            )
            merger.mergeDocuments(MemoryUsageSetting.setupMixed(50_000_000))
            println("Merged all new PDFs to '$mergeFile'.")
        }

        println("Finished successfully.")
    } catch (t: Throwable) {
        println(
            """
            Unable to process enemies to PDFs
            Cause: ${t.message}
        """.trimIndent()
        )
    }

    private val timestamp: String
        get() = OffsetDateTime.now().toString()

}

fun main(args: Array<String>) = EnemyRenderer().main(args)


/*
--input-dir "D:\Google Drive\Pen & Paper\Savage Worlds\Slipstream\season one\Enemies\json"
--selector-file "D:\Google Drive\Pen & Paper\Savage Worlds\Slipstream\season one\Enemies\resources\toProcess.list"
--output-dir "D:\Google Drive\Pen & Paper\Savage Worlds\Slipstream\season one\Enemies\pdf"
--merge-output false
 */