package tools

import kotlin.math.abs

data class Point(val x: Int, val y: Int, val z: Int = 0) {
    fun rotateX() = Point(x, -z, y)
    fun rotateY() = Point(z, y, -x)
    fun rotateZ() = Point(-y, x, z)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y, z - other.z)
    operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
    fun distance(other: Point) = (this - other).run { abs(x) + abs(y) }
}
