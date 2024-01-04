package tools.geometry

data class PointD(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0) {
    operator fun minus(other: PointD) = PointD(x - other.x, y - other.y, z - other.z)
    operator fun plus(other: PointD) = PointD(x + other.x, y + other.y, z + other.z)
    operator fun times(factor: Double) = PointD(x * factor, y * factor, z * factor)
    operator fun div(factor: Double) = PointD(x / factor, y / factor, z / factor)
}
