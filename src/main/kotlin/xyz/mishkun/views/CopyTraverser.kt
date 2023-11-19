package xyz.mishkun.views

import xyz.mishkun.parser.FileTraverser
import xyz.mishkun.parser.FromSourceToTarget
import xyz.mishkun.utils.isMarkdown
import java.io.File

class CopyTraverser(val fromSourceToTarget: FromSourceToTarget) : FileTraverser {
    override fun traverse(file: File) {
        if (file.isFile && !file.isMarkdown()) {
            val target = fromSourceToTarget.convert(file)
            target.parentFile?.mkdirs()
            file.copyTo(target, true)
        }
    }
}
