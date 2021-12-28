package tools

@Suppress("MemberVisibilityCanBePrivate", "unused")
class Board<T>(val width: Int, val height: Int, val cells: List<T>) {
    val points = (0 until height).flatMap { y ->
        (0 until width).map { x ->
            Point(x, y)
        }
    }

    init {
        if (cells.size != width * height) throw Error("invalid board")
    }

    override fun toString() = cells.chunked(width) { it.joinToString("") }.joinToString("\n")

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
