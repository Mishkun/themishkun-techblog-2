package xyz.mishkun.parser

import java.io.File

abstract class CopyAndModifyTraverser(val fromSourceToTarget: FromSourceToTarget) : FileTraverser {

    override fun traverse(file: File) {
        val target = fromSourceToTarget.convert(file)
            .let { FileRenamer(::newName).rename(it) }
        target.parentFile?.mkdirs()
        modify(file, target)
    }

    abstract fun modify(source: File, target: File)

    abstract fun newName(source: File): String
}
