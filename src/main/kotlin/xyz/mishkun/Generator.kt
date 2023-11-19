package xyz.mishkun

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import xyz.mishkun.parser.FileTree
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
        FileTree(sourcesDir, indexTraverser).walk()
        indexTraverser.dumpIndex(targetDir.resolve("index.html"))
    }

    private fun copyAttachments() {
        FileTree(sourcesDir, CopyTraverser(sourcesDir, targetDir)).walk()
    }

    private fun generatePages() {
        FileTree(sourcesDir, PageTraverser(sourcesDir, targetDir)).walk()
    }

    private fun cleanTargetDir(targetDir: File) {
        targetDir.deleteRecursively()
    }
}
