package xyz.mishkun

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import xyz.mishkun.parser.FromSourceToTarget
import xyz.mishkun.parser.walk
import xyz.mishkun.views.CopyTraverser
import xyz.mishkun.views.IndexTraverser
import xyz.mishkun.views.PageTraverser
import java.io.File

class Generator : CliktCommand() {
    val sourcesDir by argument(help = "Directory with markdown files").file(mustExist = true, mustBeReadable = true)
    val targetDir by argument(help = "Directory where to put generated html files").file()
    override fun run() {
        echo("Generating site from $sourcesDir to $targetDir")
        cleanTargetDir(targetDir)
        generatePages()
        copyAttachments()
        generateIndex()
    }

    private fun generateIndex() {
        val indexTraverser = IndexTraverser(sourcesDir)
        indexTraverser.walk(sourcesDir)
        indexTraverser.dumpIndex(targetDir.resolve("index.html"))
    }

    private fun copyAttachments() {
        CopyTraverser(sourcesDir, targetDir).walk(sourcesDir)
    }

    private fun generatePages() {
        PageTraverser(FromSourceToTarget(sourcesDir, targetDir)).walk(sourcesDir)
    }

    private fun cleanTargetDir(targetDir: File) {
        targetDir.deleteRecursively()
    }
}
