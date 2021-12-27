package aoc2021

import tools.Point
import forEachInput
import tools.log
import kotlin.math.absoluteValue

object Day19 {
    fun solve() = forEachInput(2021, 19, 1, 2) { lines ->
        val scanners = lines.fold(emptyList<List<Point>>()) { scanners, line ->
            when {
                line.isEmpty() -> scanners
                line.startsWith("---") -> scanners.plusElement(mutableListOf())
                else -> scanners.dropLast(1).plusElement(
                    scanners.last() + line.split(",").let { (x, y, z) -> Point(x.toInt(), y.toInt(), z.toInt()) }
                )
            }
        }

        val rotateScanners: MutableList<List<List<Point>>> = scanners.drop(1).map { it.rotate() }.toMutableList()

        log("part 1: ")
        val done = mutableListOf(Point(0, 0, 0) to scanners[0])
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
        done.flatMap { it.second }.distinct().count().log()

        log("part 2: ")
        val positions = done.map { it.first }
        positions.maxOf { a ->
            positions.maxOf {
                (it.x - a.x).absoluteValue + (it.y - a.y).absoluteValue + (it.z - a.z).absoluteValue
            }
        }.log()
    }

    // 48 but really 24
    private fun List<Point>.rotate() =
        (1..3).runningFold(this) { points, _ -> points.map(Point::rotateX) }
            .flatMap { (1..3).runningFold(it) { points, _ -> points.map(Point::rotateY) } }
            .flatMap { (1..2).runningFold(it) { points, _ -> points.map(Point::rotateZ) } }
            //.apply { count().log() }
            .distinct()
    //.apply { count().log() }

    private fun findScanner(rotateScanners: MutableList<List<List<Point>>>, currentScanner: Set<Point>, start: Int): Triple<Int, Point, List<Point>>? {
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
