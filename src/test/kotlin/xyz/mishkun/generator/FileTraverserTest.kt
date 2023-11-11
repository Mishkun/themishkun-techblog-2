package xyz.mishkun.generator

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.io.FileMatchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import xyz.mishkun.parser.FileTraverser
import xyz.mishkun.parser.FileTree
import java.io.File

class FileTraverserTest {

    @field:TempDir
    lateinit var sourceDir: File

    @field:TempDir
    lateinit var targetDir: File

    @Test
    fun `should traverse files`() {
        sourceDir.resolve("source.txt").writeText("Hello World!")
        val traverser = FileTree(sourceDir, targetDir, object : FileTraverser {
            override fun newName(oldName: File): String =
                oldName.nameWithoutExtension.reversed() + "." + oldName.extension

            override fun shouldTraverse(file: File): Boolean = file.isFile

            override fun traverse(source: File, target: File) {
                target.writeText(source.readText().reversed())
            }
        })
        traverser.walk()
        val targetFile = targetDir.resolve("ecruos.txt")
        assertThat(targetFile, FileMatchers.anExistingFile())
        assertThat(targetFile.readText(), equalTo("!dlroW olleH"))
    }

    @Test
    fun `should traverse files in subdirs`() {
        val subdir = sourceDir.resolve("subdir").apply { mkdirs() }
       subdir.resolve("source.txt").writeText("Hello World!")
        val traverser = FileTree(sourceDir, targetDir, object : FileTraverser {
            override fun newName(oldName: File): String =
                oldName.nameWithoutExtension.reversed() + "." + oldName.extension

            override fun shouldTraverse(file: File): Boolean = file.isFile

            override fun traverse(source: File, target: File) {
                target.writeText(source.readText().reversed())
            }
        })
        traverser.walk()
        val targetFile = targetDir.resolve("subdir/ecruos.txt")
        assertThat(targetFile, FileMatchers.anExistingFile())
        assertThat(targetFile.readText(), equalTo("!dlroW olleH"))
    }

    @Test
    fun `should do multiple traversals`() {
        sourceDir.resolve("source.txt").writeText("Hello World!")
        val traverser = FileTree(sourceDir, targetDir, object : FileTraverser {
            override fun newName(oldName: File): String =
                oldName.nameWithoutExtension.reversed() + "." + oldName.extension

            override fun shouldTraverse(file: File): Boolean = file.isFile

            override fun traverse(source: File, target: File) {
                target.writeText(source.readText().reversed())
            }
        }, object : FileTraverser {
            override fun newName(oldName: File): String =
                oldName.nameWithoutExtension + "_copy." + oldName.extension

            override fun shouldTraverse(file: File): Boolean = file.isFile

            override fun traverse(source: File, target: File) {
                target.writeText(source.readText())
            }
        })
        traverser.walk()
        val targetFile = targetDir.resolve("ecruos.txt")
        assertThat(targetFile, FileMatchers.anExistingFile())
        assertThat(targetFile.readText(), equalTo("!dlroW olleH"))
        val targetFile2 = targetDir.resolve("source_copy.txt")
        assertThat(targetFile2, FileMatchers.anExistingFile())
        assertThat(targetFile2.readText(), equalTo("Hello World!"))
    }
}
