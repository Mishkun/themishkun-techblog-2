package xyz.mishkun.parser

import java.io.File

interface FileTraverser {
    fun shouldTraverse(file: File): Boolean
    fun newName(oldName: File): String
    fun traverse(source: File, target: File)
}