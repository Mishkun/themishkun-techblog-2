package xyz.mishkun.generator

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import xyz.mishkun.utils.pathWithoutExtension
import java.io.File

class PathWithoutExtensionTest {

    @Test
    fun `should return path without extension`() {
        val path = File("src/test/kotlin/xyz/mishkun/generator/PathWithoutExtensionTest.kt")
        val expected = "src/test/kotlin/xyz/mishkun/generator/PathWithoutExtensionTest"
        assertThat(path.pathWithoutExtension(), equalTo(expected))
    }
}
