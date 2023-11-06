package xyz.mishkun.views

import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

object MarkdownToHtmlConverter {
    fun convert(source: String): String {
        val flavour = GFMFlavourDescriptor()
        val parser = MarkdownParser(flavour)
        val parsedTree = parser.buildMarkdownTreeFromString(source)
        return HtmlGenerator(source, parsedTree, flavour, false).generateHtml()
    }
}