package xyz.mishkun.generator

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.io.FileMatchers.anExistingFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import xyz.mishkun.parser.CopyAndModifyTraverser
import java.io.File

class CopyAndModifyTest {

    @field:TempDir
    lateinit var sourceDir: File

    @field:TempDir
    lateinit var targetDir: File

    @Test
    fun `should copy through subdirs`() {
        val source = sourceDir.resolve("subdir").apply { mkdirs() }.resolve("source.txt").apply {
            writeText("Hello World!")
        }
        val traverser = object : CopyAndModifyTraverser(sourceDir, targetDir) {
            override fun modify(source: File, target: File) {
                target.writeText(source.readText().reversed())
            }

            override fun newName(source: File): String = source.nameWithoutExtension + ".txt"
        }
        traverser.traverse(source)
        val targetFile = targetDir.resolve("subdir").resolve("source.txt")
        assertThat(targetFile, anExistingFile())
        assertThat(targetFile.readText(), equalTo("!dlroW olleH"))
    }

    @Test
    fun `should copy`() {
        val source = sourceDir.resolve("source.txt").apply { writeText("Hello World!") }
        val traverser = object : CopyAndModifyTraverser(sourceDir, targetDir) {
            override fun modify(source: File, target: File) {
                target.writeText(source.readText().reversed())
            }

            override fun newName(source: File): String = source.nameWithoutExtension + ".txt"
        }
        traverser.traverse(source)
        val targetFile = targetDir.resolve("source.txt")
        assertThat(targetFile, anExistingFile())
        assertThat(targetFile.readText(), equalTo("!dlroW olleH"))
    }
}
