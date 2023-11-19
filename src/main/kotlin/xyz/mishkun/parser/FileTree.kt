package xyz.mishkun.parser

import java.io.File

class FileRenamer(val renamer: (File) -> String) {
    fun rename(file: File): File = file.parentFile?.resolve(renamer(file)) ?: File(renamer(file))
}

class FromSourceToTarget(private val sourceDir: File, private val targetDir: File) {
    fun convert(sourceFile: File): File = targetDir.resolve(sourceFile.relativeTo(sourceDir))
}
