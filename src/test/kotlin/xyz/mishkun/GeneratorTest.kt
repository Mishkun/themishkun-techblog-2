package xyz.mishkun

import com.github.ajalt.clikt.testing.test
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.xmlunit.matchers.CompareMatcher.isIdenticalTo
import java.io.File

@Tag("acceptance")
class GeneratorTest {

    @field:TempDir
    lateinit var workDir: File

    private val sourcesDir get() = workDir.resolve("sources")

    private val targetDir get() = workDir.resolve("target")

    @Test
    fun `should generate a blank page`() {
        generateSimpleSourceDirectoryStructure()
        val generator = Generator()
        generator.test("${sourcesDir.absolutePath} ${targetDir.absolutePath}")
        assertThat(
            targetDir.resolve("subdir/helloworld.html").readText(), isIdenticalTo(
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

    @Test
    fun `should generate index page`() {
        generateSimpleSourceDirectoryStructure()
        val generator = Generator()
        generator.test("${sourcesDir.absolutePath} ${targetDir.absolutePath}")
        assertThat(
            targetDir.resolve("index.html").readText(), isIdenticalTo(
                """
            <html>
            <head>
            <meta charset="UTF-8"/>
            <title>My Site</title>
            </head>
            <body>
            <h1>Welcome to my site!</h1>
            <ul>
                <li><a href="subdir/helloworld.html">helloworld</a></li>
            </ul>
            </body>
            </html>
        """.trimIndent()
            ).ignoreWhitespace()
        )
    }

    private fun generateSimpleSourceDirectoryStructure(): File {
        val sourcesDir = sourcesDir.resolve("subdir").apply { mkdirs() }
        sourcesDir.resolve("helloworld.md").writeText(
            """
            # Hello World!
            
            This is a blank page
        """.trimIndent()
        )
        return sourcesDir
    }
}
