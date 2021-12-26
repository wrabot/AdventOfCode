@file:Suppress("MemberVisibilityCanBePrivate", "unused")

import java.math.BigInteger
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun Any.forEachInput(year : Int, day: Int, vararg inputs: Int, block: (List<String>) -> Unit) {
    inputs.forEach {
        log("load year $year day $day input $it")
        block(javaClass.classLoader!!.getResource("aoc$year/day$day/input$it.txt")!!.readText().lines())
    }
}

data class Point(val x: Int, val y: Int, val z: Int = 0) {
    fun rotateX() = Point(x, -z, y)
    fun rotateY() = Point(z, y, -x)
    fun rotateZ() = Point(-y, x, z)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y, z - other.z)
    operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
}

data class Block(val start: Point, val end: Point) {
    fun intersect(other: Block) = Block(
        Point(start.x.coerceAtLeast(other.start.x), start.y.coerceAtLeast(other.start.y), start.z.coerceAtLeast(other.start.z)),
        Point(end.x.coerceAtMost(other.end.x), end.y.coerceAtMost(other.end.y), end.z.coerceAtMost(other.end.z))
    ).takeUnless { it.start.x > it.end.x || it.start.y > it.end.y || it.start.z > it.end.z }

    fun size() = (end.x.toLong() - start.x + 1) * (end.y.toLong() - start.y + 1) * (end.z.toLong() - start.z + 1)
}

class Board<T>(val width: Int, val height: Int, val cells: List<T>) {
    val points = (0 until height).flatMap { y ->
        (0 until width).map { x ->
            Point(x, y)
        }
    }

    init {
        if (cells.size != width * height) throw Error("invalid board")
    }

    fun log() = log(cells.chunked(width) { it.joinToString("") }.joinToString("\n"))

    fun isValid(x: Int, y: Int) = x in 0 until width && y in 0 until height
    fun getOrNull(x: Int, y: Int) = if (isValid(x, y)) cells[y * width + x] else null
    operator fun get(x: Int, y: Int) = getOrNull(x, y) ?: throw Error("invalid cell")
    fun isValid(point: Point) = isValid(point.x, point.y)
    fun getOrNull(point: Point) = getOrNull(point.x, point.y)
    operator fun get(point: Point) = get(point.x, point.y)

    fun neighbors4(point: Point) =
        listOf(
            Point(point.x + 1, point.y),
            Point(point.x - 1, point.y),
            Point(point.x, point.y + 1),
            Point(point.x, point.y - 1)
        ).filter { isValid(it) }

    fun neighbors8(point: Point) =
        neighbors4(point) + listOf(
            Point(point.x + 1, point.y + 1),
            Point(point.x - 1, point.y + 1),
            Point(point.x + 1, point.y - 1),
            Point(point.x - 1, point.y - 1)
        ).filter { isValid(it) }

    fun zone4(point: Point, predicate: (Point) -> Boolean) =
        zone(point) { neighbors4(it).filter(predicate) }

    fun zone8(point: Point, predicate: (Point) -> Boolean) =
        zone(point) { neighbors8(it).filter(predicate) }

    fun zone(point: Point, neighbors: (Point) -> List<Point>): List<Point> {
        val zone = mutableListOf(point)
        var index = 0
        while (index < zone.size) {
            zone.addAll(neighbors(zone[index++]).filter { it !in zone })
        }
        return zone
    }
}

fun log(message: Any?) = System.err.println(message)

fun List<Char>.log(width: Int) = apply {
    log(chunked(width) { it.joinToString("") }.joinToString("\n"))
}

fun <T> T.log() = apply { log(this) }

@ExperimentalTime
fun <T> logDuration(prefix: String = "", block: () -> T) = measureTimedValue(block).apply { log("$prefix$duration") }.value

fun Iterable<BigInteger>.sum() = reduce { acc, bi -> acc + bi }

fun Iterable<String>.toInts() = map { it.toInt() }

fun <T> repeat(count: Int, init: T, next: (T) -> T): T {
    var t = init
    repeat(count) { t = next(t) }
    return t
}
