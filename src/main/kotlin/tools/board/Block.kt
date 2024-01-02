package tools.board

import tools.range.hasIntersection

data class Block(val start: Point, val end: Point) {
    fun intersect(other: Block) = Block(
        Point(start.x.coerceAtLeast(other.start.x), start.y.coerceAtLeast(other.start.y), start.z.coerceAtLeast(other.start.z)),
        Point(end.x.coerceAtMost(other.end.x), end.y.coerceAtMost(other.end.y), end.z.coerceAtMost(other.end.z))
    ).takeUnless { it.start.x > it.end.x || it.start.y > it.end.y || it.start.z > it.end.z }

    fun size() = (end.x.toLong() - start.x + 1) * (end.y.toLong() - start.y + 1) * (end.z.toLong() - start.z + 1)

    init {
        require((end - start).run { x >= 0 && y >= 0 && z >= 0 }) { "start not before end $this" }
    }

    fun hasIntersection(other: Block) = xRange.hasIntersection(other.xRange) &&
            yRange.hasIntersection(other.yRange) && zRange.hasIntersection(other.zRange)

    val xRange = start.x..end.x
    val yRange = start.y..end.y
    val zRange = start.z..end.z
}
