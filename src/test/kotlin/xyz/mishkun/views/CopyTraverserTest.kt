package xyz.mishkun.views

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.io.FileMatchers.anExistingFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class CopyTraverserTest {

    @field:TempDir
    lateinit var sourceDir: File
    @field:TempDir
    lateinit var targetDir: File

    @Test
    fun `should copy files to target dir`() {
        val traverser = CopyTraverser(sourceDir, targetDir)
        sourceDir.resolve("someFile.bin").writeBytes(byteArrayOf(1, 2, 3, 4, 5))
        traverser.traverse(sourceDir.resolve("someFile.bin"))
        assertThat(targetDir.resolve("someFile.bin"), anExistingFile())
        assertThat(targetDir.resolve("someFile.bin").readBytes(), equalTo(byteArrayOf(1, 2, 3, 4, 5)))
    }
}
