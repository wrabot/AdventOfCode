package tools.board

class Board<T>(val width: Int, val height: Int, val cells: List<T>) {
    val points = (0 until height).flatMap { y ->
        (0 until width).map { x ->
            Point(x, y)
        }
    }

    val xRange = 0..<width
    val yRange = 0..<height

    init {
        if (cells.size != width * height) throw Error("invalid board ${cells.size} !=  $width * $height (${width * height})")
    }

    override fun toString() = cells.chunked(width) { it.joinToString("") }.joinToString("\n")

    fun toString(start: Point, end: Point) = cells.chunked(width) {
        it.joinToString("").substring(start.x, end.x + 1)
    }.subList(start.y, end.y + 1).joinToString("\n")

    private fun isValid(x: Int, y: Int) = x in xRange && y in yRange
    fun getOrNull(x: Int, y: Int) = if (isValid(x, y)) cells[y * width + x] else null
    operator fun get(x: Int, y: Int) =
        getOrNull(x, y) ?: throw Error("invalid cell : x=$x y=$y width=$width height=$height")

    private fun isValid(point: Point) = isValid(point.x, point.y)
    fun getOrNull(point: Point) = getOrNull(point.x, point.y)
    operator fun get(point: Point) = get(point.x, point.y)

    fun neighbors4(point: Point) = directions4.map { point + it }.filter { isValid(it) }
    fun neighbors8(point: Point) = directions8.map { point + it }.filter { isValid(it) }

    fun zone4(point: Point, predicate: (Point) -> Boolean) =
        zone(point) { neighbors4(it).filter(predicate) }

    private fun zone(point: Point, neighbors: (Point) -> List<Point>): Set<Point> =
        sortedSetOf<Point>({ p1, p2 -> (p2.y - p1.y) * width + p2.x - p1.x }, point).apply {
            val todo = mutableListOf(point)
            while (true) neighbors(todo.removeFirstOrNull() ?: break).forEach { if (add(it)) todo.add(it) }
        }
}
