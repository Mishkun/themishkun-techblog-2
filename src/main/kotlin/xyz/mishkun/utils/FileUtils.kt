package xyz.mishkun.utils

import java.io.File

fun File.pathWithoutExtension(): String = this.path.substringBeforeLast(".")

fun File.isMarkdown(): Boolean = this.extension.lowercase() == "md"
