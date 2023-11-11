package xyz.mishkun.views

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.xmlunit.matchers.CompareMatcher.isIdenticalTo
import java.io.File

class IndexTraverserTest {

    @Test
    fun `should traverse any md file inside pages directory`() {
        val traverser = IndexTraverser()
        assertThat(traverser.shouldTraverse(File("pages/file.md")), equalTo(true))
    }

    @Test
    fun `should not traverse files outside pages directory`() {
        val traverser = IndexTraverser()
        assertThat(traverser.shouldTraverse(File("file")), equalTo(false))
        assertThat(traverser.shouldTraverse(File("other/file")), equalTo(false))
    }

    @Test
    fun `should not rename files`() {
        val traverser = IndexTraverser()
        assertThat(traverser.newName(File("pages/someFile")), equalTo("someFile"))
    }

    @Test
    fun `should generate index page`() {
        val traverser = IndexTraverser()
        traverser.traverse(File("someFile.md"), File("blog/someFile.md"))
        traverser.dumpIndex(File("index.html"))
        assertThat(
            File("index.html").readText(), isIdenticalTo(
                """
            <html>
            <head>
            <meta charset="UTF-8"/>
            <title>My Site</title>
            </head>
            <body>
            <h1>Welcome to my site!</h1>
            <ul>
                <li><a href="blog/someFile.html">someFile</a></li>
            </ul>
            </body>
            </html>
            """
            ).ignoreWhitespace()
        )
    }
}
