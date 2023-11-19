package xyz.mishkun.generator

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.io.FileMatchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import xyz.mishkun.parser.FileTraverser
import xyz.mishkun.parser.walk
import java.io.File

class FileTraverserTest {

    @field:TempDir
    lateinit var sourceDir: File

    @field:TempDir
    lateinit var targetDir: File

    @Test
    fun `should traverse files`() {
        sourceDir.resolve("source.txt").writeText("Hello World!")
        val targetFile = targetDir.resolve("ecruos.txt")
        val traverser = object : FileTraverser {
            override fun traverse(file: File) {
                if (file.isFile) {
                    targetFile.writeText(file.readText().reversed())
                }
            }
        }
        traverser.walk(sourceDir)
        assertThat(targetFile, FileMatchers.anExistingFile())
        assertThat(targetFile.readText(), equalTo("!dlroW olleH"))
    }
}
