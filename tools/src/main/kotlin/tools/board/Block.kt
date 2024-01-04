package tools.board

import tools.range.hasIntersection

data class Block(val start: Point, val end: Point) {
    fun intersect(other: Block) =
        (Point(
            start.x.coerceAtLeast(other.start.x),
            start.y.coerceAtLeast(other.start.y),
            start.z.coerceAtLeast(other.start.z)
        ) to Point(
            end.x.coerceAtMost(other.end.x),
            end.y.coerceAtMost(other.end.y),
            end.z.coerceAtMost(other.end.z)
        )).takeIf { check(it.first, it.second) }?.run { Block(first, second) }

    fun size() = (end.x.toLong() - start.x + 1) * (end.y.toLong() - start.y + 1) * (end.z.toLong() - start.z + 1)

    init {
        require(check(start, end)) { "start not before end $this" }
    }

    fun hasIntersection(other: Block) = xRange.hasIntersection(other.xRange) &&
            yRange.hasIntersection(other.yRange) && zRange.hasIntersection(other.zRange)

    private fun check(start: Point, end: Point) = (end - start).run { x >= 0 && y >= 0 && z >= 0 }

    val xRange = start.x..end.x
    val yRange = start.y..end.y
    val zRange = start.z..end.z
}
