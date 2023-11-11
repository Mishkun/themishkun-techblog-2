package xyz.mishkun.utils

import java.io.File

fun File.pathWithoutExtension(): String = this.path.substringBeforeLast(".")
