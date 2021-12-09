@file:Suppress("MemberVisibilityCanBePrivate", "unused")

import java.math.BigInteger
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

data class Point(val x: Int, val y: Int)

class Board<T>(val width: Int, val height: Int, private val cells: List<T>) {
    val points = (0 until height).flatMap { y ->
        (0 until width).map { x ->
            Point(x, y)
        }
    }

    init {
        if (cells.size != width * height) throw Error("invalid board")
    }

    fun isValid(x: Int, y: Int) = x in 0 until width && y in 0 until height
    fun getOrNull(x: Int, y: Int) = if (isValid(x, y)) cells[y * width + x] else null
    operator fun get(x: Int, y: Int) = getOrNull(x, y) ?: throw Error("invalid cell")
    fun isValid(point: Point) = isValid(point.x, point.y)
    fun getOrNull(point: Point) = getOrNull(point.x, point.y)
    operator fun get(point: Point) = get(point.x, point.y)

    fun neighbors4(point: Point) =
        listOfNotNull(
            Point(point.x + 1, point.y),
            Point(point.x - 1, point.y),
            Point(point.x, point.y + 1),
            Point(point.x, point.y - 1)
        ).filter { isValid(it) }

    fun neighbors8(point: Point) =
        neighbors4(point) + listOfNotNull(
            Point(point.x + 1, point.y + 1),
            Point(point.x - 1, point.y + 1),
            Point(point.x + 1, point.y - 1),
            Point(point.x - 1, point.y - 1)
        ).filter { isValid(it) }
}

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
