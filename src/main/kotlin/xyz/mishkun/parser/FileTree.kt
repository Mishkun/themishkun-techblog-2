package xyz.mishkun.parser

import java.io.File

class FileTree(
    private val sourceDir: File,
    private val targetDir: File,
    private vararg val traversers: FileTraverser
) {
    fun walk() {
        for (source in sourceDir.walk()) {
            for (traverser in traversers) {
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
}

class FileRenamer(val renamer: (File) -> String) {
    fun rename(file: File): File = file.parentFile?.resolve(renamer(file)) ?: File(renamer(file))
}

class FromSourceToTarget(private val sourceDir: File, private val targetDir: File) {
    fun convert(sourceFile: File): File = targetDir.resolve(sourceFile.relativeTo(sourceDir))
}
