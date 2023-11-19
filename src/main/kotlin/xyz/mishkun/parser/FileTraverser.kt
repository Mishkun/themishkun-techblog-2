package xyz.mishkun.parser

import java.io.File

interface FileTraverser {
    fun traverse(file: File)
}
