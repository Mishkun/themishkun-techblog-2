package xyz.mishkun.views

import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.meta
import kotlinx.html.stream.createHTML
import kotlinx.html.title
import kotlinx.html.unsafe
import xyz.mishkun.parser.FileTraverser
import java.io.File

class PageTraverser : FileTraverser {
    override fun newName(oldName: File): String = oldName.nameWithoutExtension + ".html"

    override fun shouldTraverse(file: File): Boolean = file.extension == "md"

    override fun traverse(source: File, target: File) {
        target.writeText(render(source.readText()))
    }

    private fun render(source: String): String = createHTML(prettyPrint = false, xhtmlCompatible = true).html {
        head {
            meta { charset = "UTF-8" }
            title { +"My Site" }
        }
        unsafe {
            +MarkdownToHtmlConverter.convert(source)
        }
    }
}
