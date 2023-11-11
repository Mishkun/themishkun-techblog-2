package xyz.mishkun.views

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.xmlunit.matchers.CompareMatcher.isIdenticalTo
import java.io.File

class IndexTraverserTest {

    @field:TempDir
    lateinit var sourceDir: File

    @Test
    fun `should traverse any md file inside pages directory`() {
        val traverser = IndexTraverser(sourceDir)
        assertThat(traverser.shouldTraverse(File("pages/file.md")), equalTo(true))
    }

    @Test
    fun `should not traverse files outside pages directory`() {
        val traverser = IndexTraverser(sourceDir)
        assertThat(traverser.shouldTraverse(File("file")), equalTo(false))
        assertThat(traverser.shouldTraverse(File("other/file")), equalTo(false))
    }

    @Test
    fun `should generate index page`() {
        val traverser = IndexTraverser(sourceDir)
        traverser.traverse(sourceDir.resolve("someFile1.md"))
        traverser.traverse(sourceDir.resolve("pages/someOtherFile.md"))
        traverser.dumpIndex(sourceDir.resolve("index.html"))
        assertThat(
            sourceDir.resolve("index.html").readText(), isIdenticalTo(
                """
            <html>
            <head>
            <meta charset="UTF-8"/>
            <title>My Site</title>
            </head>
            <body>
            <h1>Welcome to my site!</h1>
            <ul>
                <li><a href="someFile1.html">someFile1</a></li>
                <li><a href="pages/someOtherFile.html">someOtherFile</a></li>
            </ul>
            </body>
            </html>
            """
            ).ignoreWhitespace()
        )
    }
}
