package tools.geometry

data class LineD(val origin: PointD, val vector: PointD) {
    // return null or the t of intersection point : origin + vector * t == other.origin + other.vector * u
    fun intersectXY(other: LineD): Double? {
        val dv = vector.x * other.vector.y - vector.y * other.vector.x
        if (dv == 0.0) return null
        val o = other.origin - origin
        return (o.x * other.vector.y - o.y * other.vector.x) / dv
    }

    operator fun get(t: Double) = origin + vector * t
}
