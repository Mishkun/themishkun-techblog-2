package xyz.mishkun

import com.github.ajalt.clikt.testing.test
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.io.FileMatchers.anExistingFile
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
    fun `should clean target dir before copying`() {
        generateSimpleSourceDirectoryStructure()
        val generator = Generator()
        targetDir.resolve("subdir").apply { mkdirs() }.resolve("helloworld.txt").writeText("Hello World!")
        generator.test("${sourcesDir.absolutePath} ${targetDir.absolutePath}")
        assertThat(targetDir.resolve("subdir/helloworld.txt"), not(anExistingFile()))
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

    @Test
    fun `should copy attachments`() {
        val sourcesDir = generateSimpleSourceDirectoryStructure()
        val attachmentsDir = sourcesDir.resolve("attachments").apply { mkdirs() }
        attachmentsDir.resolve("attachment.txt").writeText("Hello World!")
        val generator = Generator()
        generator.test("${sourcesDir.absolutePath} ${targetDir.absolutePath}")
        assertThat(targetDir.resolve("attachments/attachment.txt"), anExistingFile())
        assertThat(targetDir.resolve("attachments/attachment.txt").readText(), equalTo("Hello World!"))
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
