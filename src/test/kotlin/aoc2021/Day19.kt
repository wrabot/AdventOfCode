package aoc2021

import Day
import tools.geometry.Origin
import tools.geometry.Point
import kotlin.math.absoluteValue

class Day19(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        val rotateScanners: MutableList<List<List<Point>>> = scanners.drop(1).map { it.rotate() }.toMutableList()
        val toTry = mutableListOf(scanners[0])
        while (toTry.isNotEmpty()) {
            val current = toTry.removeAt(0).toSet()
            var start = 0
            while (true) {
                val result = findScanner(rotateScanners, current, start) ?: break
                rotateScanners.removeAt(result.first)
                start = result.first
                done.add(result.second to result.third)
                toTry.add(result.third)
            }
        }
        return done.flatMap { it.second }.distinct().count()
    }

    override fun solvePart2(): Any {
        part1 // force part1
        val positions = done.map { it.first }
        return positions.maxOf { a ->
            positions.maxOf {
                (it.x - a.x).absoluteValue + (it.y - a.y).absoluteValue + (it.z - a.z).absoluteValue
            }
        }.toInt()
    }

    private val scanners = lines.fold(emptyList<List<Point>>()) { scanners, line ->
        when {
            line.isEmpty() -> scanners
            line.startsWith("---") -> scanners.plusElement(mutableListOf())
            else -> scanners.dropLast(1).plusElement(
                scanners.last() + line.split(",").let { (x, y, z) -> Point(x.toInt(), y.toInt(), z.toInt()) }
            )
        }
    }

    private val done = mutableListOf(Origin to scanners[0])

    // 48 but really 24
    private fun List<Point>.rotate() =
        (1..3).runningFold(this) { points, _ -> points.map(Point::rotateX) }
            .flatMap { (1..3).runningFold(it) { points, _ -> points.map(Point::rotateY) } }
            .flatMap { (1..2).runningFold(it) { points, _ -> points.map(Point::rotateZ) } }
            .distinct()

    private fun findScanner(
        rotateScanners: MutableList<List<List<Point>>>,
        currentScanner: Set<Point>,
        start: Int
    ): Triple<Int, Point, List<Point>>? {
        for (index in start until rotateScanners.size) {
            rotateScanners[index].forEach { beacons ->
                currentScanner.drop(11).forEach { refBeacon ->
                    beacons.drop(11).forEach { beacon ->
                        val position = refBeacon - beacon
                        if (beacons.count { it + position in currentScanner } >= 12) {
                            return Triple(index, position, beacons.map { it + position })
                        }
                    }
                }
            }
        }
        return null
    }
}

private fun Point.rotateX() = Point(x, -z, y)
private fun Point.rotateY() = Point(z, y, -x)
private fun Point.rotateZ() = Point(-y, x, z)
