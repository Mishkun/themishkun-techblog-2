package xyz.mishkun.utils

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class ExtensionTest {
    @Test
    fun `should detect md files`() {
        assertThat(File("someFile.md").isMarkdown(), equalTo(true))
        assertThat(File("someFile.MD").isMarkdown(), equalTo(true))
    }

    @Test
    fun `should detect non md files`() {
        assertThat(File("someFile.bin").isMarkdown(), equalTo(false))
    }
}
