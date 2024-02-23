package aoc2016

import Day
import tools.toWords

class Day3(test: Int? = null) : Day(test) {
    override fun solvePart1() = triangles.count { it.isTriangle() }

    override fun solvePart2() = triangles.chunked(3).map { it.transpose() }.flatten().count { it.isTriangle() }

    private fun List<List<Int>>.transpose() = get(0).indices.map { column -> map { it[column] } }
    
    private fun List<Int>.isTriangle(): Boolean {
        val (a, b, c) = sorted()
        return a + b > c
    }
    
    private val triangles = lines.map { line -> line.toWords().map { it.toInt() }}
}
