package xyz.mishkun

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
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
        // TODO("Not yet implemented")
    }

    private fun copyAttachments() {
        // TODO("Not yet implemented")
    }

    private fun generatePages() {
        // TODO("Not yet implemented")
    }

    private fun cleanTargetDir(targetDir: File) {
        targetDir.deleteRecursively()
    }
}
