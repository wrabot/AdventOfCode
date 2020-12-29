import java.math.BigInteger
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun log(message: Any?) = System.err.println("!!!$message")

fun List<Char>.log(width: Int) = apply {
    log(chunked(width) { it.joinToString("") }.joinToString("\n"))
}

fun <T> T.log() = apply { log(this) }

@OptIn(ExperimentalTime::class)
fun <T> logDuration(prefix: String = "", block: () -> T) = measureTimedValue(block).apply { log("$prefix$duration") }.value

fun Iterable<BigInteger>.sum() = reduce { acc, bi -> acc + bi }

fun Iterable<String>.toInts() = map { it.toInt() }

fun <T> repeat(count: Int, init: T, next: (T) -> T): T {
    var t = init
    repeat(count) { t = next(t) }
    return t
}
