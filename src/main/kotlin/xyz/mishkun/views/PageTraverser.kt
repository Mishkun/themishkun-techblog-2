package xyz.mishkun.views

import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.meta
import kotlinx.html.stream.createHTML
import kotlinx.html.title
import kotlinx.html.unsafe
import xyz.mishkun.parser.CopyAndModifyTraverser
import java.io.File

class PageTraverser(sourceDir: File, targetDir: File) : CopyAndModifyTraverser(sourceDir, targetDir) {
    override fun newName(source: File): String = source.nameWithoutExtension + ".html"

    override fun modify(source: File, target: File) {
        if (source.extension == "md") {
            target.writeText(render(source.readText()))
        }
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
