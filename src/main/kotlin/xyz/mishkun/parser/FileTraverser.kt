package xyz.mishkun.parser

import java.io.File

interface FileTraverser {
    fun traverse(file: File)
}

fun FileTraverser.walk(sourceDir: File) {
    for (source in sourceDir.walk()) {
        traverse(source)
    }
}
