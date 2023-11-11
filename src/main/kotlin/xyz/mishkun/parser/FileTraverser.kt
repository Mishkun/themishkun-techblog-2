package xyz.mishkun.parser

import java.io.File

interface FileTraverser {
    fun shouldTraverse(file: File): Boolean

    fun traverse(file: File)
}
