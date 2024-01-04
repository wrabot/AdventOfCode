package tools.board

data class LineD(val origin: PointD, val vector: PointD) {
    // return the t of intersection point = origin + vector * t
    fun intersectXY(other: LineD): Double? {
        val dv = vector.x * other.vector.y - vector.y * other.vector.x
        if (dv == 0.0) return null
        val o = other.origin - origin
        return (o.x * other.vector.y - o.y * other.vector.x) / dv
    }

    operator fun get(t: Double) = origin + vector * t
}
