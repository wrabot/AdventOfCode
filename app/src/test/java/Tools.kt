import java.math.BigInteger

fun log(message: Any?) = System.err.println("!!!$message")

fun List<Char>.log(width: Int) = apply {
    log(chunked(width) { it.joinToString("") }.joinToString("\n"))
}

fun <T> T.log() = apply { log(this) }

fun Iterable<BigInteger>.sum() = reduce { acc, bi -> acc + bi }

fun Iterable<String>.toInts() = map { it.toInt() }
