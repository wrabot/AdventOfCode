package aoc2022

import Day
import tools.board.Point

class Day18(test: Int? = null) : Day(2022, 18, test) {
    override fun solvePart1() = faces.size

    override fun solvePart2(): Int {
        val xRange = lavas.minOf { it.x }..lavas.maxOf { it.x }
        val yRange = lavas.minOf { it.y }..lavas.maxOf { it.y }
        val zRange = lavas.minOf { it.z }..lavas.maxOf { it.z }
        val outsides = mutableSetOf<Point>()
        for (x in xRange) {
            for (y in yRange) {
                outsides.add(Point(x, y, zRange.first - 1))
                outsides.add(Point(x, y, zRange.last + 1))
            }
        }
        for (z in zRange) {
            for (y in yRange) {
                outsides.add(Point(xRange.first - 1, y, z))
                outsides.add(Point(xRange.last + 1, y, z))
            }
        }
        for (x in xRange) {
            for (z in zRange) {
                outsides.add(Point(x, yRange.first - 1, z))
                outsides.add(Point(x, yRange.last + 1, z))
            }
        }
        var todo = outsides.toSet()
        while (todo.isNotEmpty()) {
            todo = todo.flatMap { point -> directions.map { point + it }.filter { it.x in xRange && it.y in yRange && it.z in zRange && it !in outsides && it !in lavas } }.toSet()
            outsides.addAll(todo)
        }
        return faces.count { it in outsides }
    }

    private val lavas = lines.map { line ->
        val (x, y, z) = line.split(",").map { it.toInt() }
        Point(x, y, z)
    }.toSet()

    private val directions = listOf(Point(x = -1), Point(x = 1), Point(y = -1), Point(y = 1), Point(z = -1), Point(z = 1))

    private val faces by lazy { lavas.flatMap { lava -> directions.map {lava + it }.filter { it !in lavas } } }
}
