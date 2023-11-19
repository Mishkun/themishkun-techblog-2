package xyz.mishkun.views

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.xmlunit.matchers.CompareMatcher.isIdenticalTo
import xyz.mishkun.parser.FromSourceToTarget
import java.io.File

class PageTraverserTest {

    @field:TempDir
    lateinit var sourceDir: File

    @Test
    fun `should rename file to html`() {
        val source = File("source.md")
        val traverser = PageTraverser(FromSourceToTarget(File("someDir"),File("someDir")))
        val target = traverser.newName(source)
        assertThat(target, equalTo("source.html"))
    }

    @Test
    fun `should render a complete html page`() {
        val source = sourceDir.resolve("source.md").apply {
            writeText(
                """
            # Hello World!
            
            This is a blank page
        """.trimIndent()
            )
        }
        val traverser = PageTraverser(FromSourceToTarget(sourceDir, sourceDir))
        val target = sourceDir.resolve("source.html")
        traverser.traverse(source)
        assertThat(
            target.readText(), isIdenticalTo(
                """
            <html>
            <head>
            <meta charset="UTF-8"/>
            <title>My Site</title>
            </head>
            <body>
            <h1>Hello World!</h1>
            <p>This is a blank page</p>
            </body>
            </html>
        """.trimIndent()
            ).ignoreWhitespace()
        )
    }
}
