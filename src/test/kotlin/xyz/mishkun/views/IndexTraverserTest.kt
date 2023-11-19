package xyz.mishkun.views

import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.xmlunit.matchers.CompareMatcher.isIdenticalTo
import java.io.File

class IndexTraverserTest {

    @field:TempDir
    lateinit var sourceDir: File

    @Test
    fun `should generate index page`() {
        val traverser = IndexTraverser(sourceDir)
        traverser.traverse(sourceDir.resolve("someFile1.html"))
        traverser.traverse(sourceDir.resolve("pages/someOtherFile.html"))
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
