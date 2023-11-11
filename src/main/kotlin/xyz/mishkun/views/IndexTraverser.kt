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
import java.io.File

class IndexTraverser: FileTraverser {

    val index = mutableListOf<String>()
    override fun shouldTraverse(file: File): Boolean = file.extension == "md"

    override fun newName(oldName: File): String = oldName.name

    override fun traverse(source: File, target: File) {
        index += source.nameWithoutExtension
    }

    fun dumpIndex(target: File) = createHTML(xhtmlCompatible = true).html{
        head {
            meta { charset = "UTF-8" }
            title { +"My Site" }
        }
        body {
            h1 { +"Welcome to my site!" }
            ul {
                index.forEach {
                    li {
                        a(href = "blog/$it.html") { +it }
                    }
                }
            }
        }
    }.let { target.writeText(it) }
}
