package xyz.mishkun.generator

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.io.FileMatchers.aFileNamed
import org.hamcrest.io.FileMatchers.aFileWithAbsolutePath
import org.junit.jupiter.api.Test
import xyz.mishkun.FileRenamer
import java.io.File

class FileRenamerTest {

    @Test
    fun `should rename a file`() {
        val file = File("source.txt")
        val renamer = FileRenamer { oldFile -> oldFile.nameWithoutExtension.reversed() + "." + oldFile.extension }
        val newFile = renamer.rename(file)
        assertThat(newFile, aFileNamed(equalTo("ecruos.txt")))
    }

    @Test
    fun `should rename a file inside a directory`() {
        val subdir = File("subdir")
        val file = subdir.resolve("source.txt")
        val renamer = FileRenamer { oldFile -> oldFile.nameWithoutExtension.reversed() + "." + oldFile.extension }
        val newFile = renamer.rename(file)
        assertThat(newFile, aFileNamed(equalTo("ecruos.txt")))
        assertThat(newFile, aFileWithAbsolutePath(startsWith(subdir.absolutePath)))
    }
}