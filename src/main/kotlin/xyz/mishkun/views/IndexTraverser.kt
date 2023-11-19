package xyz.mishkun.views

import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.li
import kotlinx.html.meta
import kotlinx.html.stream.createHTML
import kotlinx.html.title
import kotlinx.html.ul
import xyz.mishkun.parser.FileTraverser
import xyz.mishkun.utils.pathWithoutExtension
import java.io.File

class IndexTraverser(val sourceDir: File) : FileTraverser {

    val index = mutableListOf<String>()
    override fun traverse(file: File) {
        if (file.extension == "md") {
            index += file.relativeTo(sourceDir).pathWithoutExtension()
        }
    }

    fun dumpIndex(target: File) = createHTML(xhtmlCompatible = true).html {
        head {
            meta { charset = "UTF-8" }
            title { +"My Site" }
        }
        body {
            h1 { +"Welcome to my site!" }
            ul {
                index.forEach {
                    li {
                        a(href = "$it.html") { +it.substringAfterLast("/") }
                    }
                }
            }
        }
    }.let { target.writeText(it) }
}
