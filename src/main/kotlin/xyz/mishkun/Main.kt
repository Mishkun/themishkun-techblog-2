package xyz.mishkun

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import kotlinx.html.title
import kotlinx.html.unsafe
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import java.io.File

object MarkdownToHtmlConverter {
    fun convert(source: String): String {
        val flavour = GFMFlavourDescriptor()
        val parser = MarkdownParser(flavour)
        val parsedTree = parser.buildMarkdownTreeFromString(source)
        return HtmlGenerator(source, parsedTree, flavour, false).generateHtml()
    }
}

class FileRenamer(val renamer: (File) -> String) {
    fun rename(file: File): File = file.parentFile?.resolve(renamer(file)) ?: File(renamer(file))
}

class FromSourceToTarget(private val sourceDir: File, private val targetDir: File) {
    fun convert(sourceFile: File): File = targetDir.resolve(sourceFile.relativeTo(sourceDir))
}

class FileTree(
    private val sourceDir: File,
    private val targetDir: File,
    private val traverser: FileTraverser
) {
    fun walk() {
        sourceDir.walk().forEach { source ->
            if (traverser.shouldTraverse(source)) {
                val target = FromSourceToTarget(sourceDir, targetDir).convert(source)
                    .let { FileRenamer(traverser::newName).rename(it) }
                target.parentFile?.mkdirs()
                println("Converting $source to $target")
                traverser.traverse(source, target)
            }
        }
    }
}

interface FileTraverser {
    fun shouldTraverse(file: File): Boolean
    fun newName(oldName: File): String
    fun traverse(source: File, target: File)
}

class PageTraverser : FileTraverser {
    override fun newName(oldName: File): String = oldName.nameWithoutExtension + ".html"

    override fun shouldTraverse(file: File): Boolean = file.extension == "md"

    override fun traverse(source: File, target: File) {
        target.writeText(render(source.readText()))
    }

    private fun render(source: String): String = createHTML(prettyPrint = false).html {
        head {
            unsafe {
                +"""<meta charset="UTF-8"/>"""
            }
            title { +"My Site" }
        }
        unsafe {
            +MarkdownToHtmlConverter.convert(source)
        }
    }
}

class Generator : CliktCommand() {
    val sourcesDir by argument(help = "Directory with markdown files").file(mustExist = true, mustBeReadable = true)
    val targetDir by argument(help = "Directory where to put generated html files").file()
    override fun run() {
        echo("Generating site from $sourcesDir to $targetDir")
        FileTree(sourcesDir, targetDir, PageTraverser()).walk()
    }
}

fun main(args: Array<String>) {
    Generator().main(args)
}
