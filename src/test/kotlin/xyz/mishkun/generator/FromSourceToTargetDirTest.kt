package xyz.mishkun.generator

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.io.FileMatchers
import org.junit.jupiter.api.Test
import xyz.mishkun.parser.FromSourceToTarget
import java.io.File

class FromSourceToTargetDirTest {

    val sourceDir = File("source")
    val targetDir = File("target")

    @Test
    fun `should generate file from source to target dir`() {
        val input = sourceDir.resolve("source.txt")
        val converter = FromSourceToTarget(sourceDir, targetDir)
        val targetFile = converter.convert(input)
        assertThat(targetFile, FileMatchers.aFileNamed(equalTo("source.txt")))
        assertThat(targetFile, FileMatchers.aFileWithAbsolutePath(startsWith(targetDir.absolutePath)))
    }

    @Test
    fun `should generate file from source to target dir mirroring structure`() {
        val input = sourceDir.resolve("subdir/source.txt")
        val converter = FromSourceToTarget(sourceDir, targetDir)
        val targetFile = converter.convert(input)
        assertThat(targetFile, FileMatchers.aFileNamed(equalTo("source.txt")))
        assertThat(targetFile, FileMatchers.aFileWithAbsolutePath(startsWith(targetDir.resolve("subdir").absolutePath)))
    }
}
