package aoc2017

import Day
import kotlin.math.abs

class Day11(test: Int? = null) : Day(test) {
    override fun solvePart1() = path.steps()
    override fun solvePart2() = path.indices.maxOf { path.dropLast(it).steps() }

    private fun List<String>.steps(): Int {
        val moves = groupingBy { it }.eachCount().toMutableMap()
        moves.simplify("n", "s")
        moves.simplify("nw", "se")
        moves.simplify("ne", "sw")
        val nw = moves["nw"] ?: 0
        return abs(moves["n"]!! + nw) + abs(moves["ne"]!! - nw)
    }

    private fun MutableMap<String, Int>.simplify(a: String, b: String) {
        val u = remove(a) ?: 0
        val v = remove(b) ?: 0
        set(a, u - v)
    }

    val path = input.split(",")
}
