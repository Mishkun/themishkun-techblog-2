package xyz.mishkun.generator

import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.xmlunit.matchers.CompareMatcher.isSimilarTo
import xyz.mishkun.views.MarkdownToHtmlConverter

class ParseToHtmlTest {

    @Test
    fun `should parse simple markdown to html`() {
        val markdown = """
            # Hello World!
            
            This is a blank page
        """.trimIndent()
        val html = MarkdownToHtmlConverter.convert(markdown)
        print(html)
        assertThat(
            html, isSimilarTo(
                """
            <body>
            <h1>Hello World!</h1>
            <p>This is a blank page</p>
            </body>
        """.trimIndent()
            ).ignoreWhitespace()
        )
    }
}
