package xyz.mishkun

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import xyz.mishkun.parser.FileTree
import xyz.mishkun.views.PageTraverser

class Generator : CliktCommand() {
    val sourcesDir by argument(help = "Directory with markdown files").file(mustExist = true, mustBeReadable = true)
    val targetDir by argument(help = "Directory where to put generated html files").file()
    override fun run() {
        echo("Generating site from $sourcesDir to $targetDir")
        FileTree(sourcesDir, targetDir, PageTraverser()).walk()
    }
}
