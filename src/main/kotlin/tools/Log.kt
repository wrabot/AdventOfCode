package tools

fun log(message: Any?) = System.err.println(message)

fun <T> T.log() = apply { log(this) }

fun List<Char>.log(width: Int) = apply {
    log(chunked(width) { it.joinToString("") }.joinToString("\n"))
}

