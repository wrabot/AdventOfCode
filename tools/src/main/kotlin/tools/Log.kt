package tools

fun log(message: Any?) = System.err.println(message)
fun <T> T.log() = apply { log(this) }
