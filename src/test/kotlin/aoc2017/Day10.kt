package aoc2017

import Day
import java.util.*

class Day10(test: Int? = null) : Day(test) {
    override fun solvePart1() = MutableList(256) { it }.apply {
        knotHashRound(input.split(",").map { it.toInt() }, 1)
    }.let { it[0] * it[1] }

    override fun solvePart2() = knotHash(input)

    private fun knotHash(input: String) = MutableList(256) { it }.apply {
        knotHashRound(input.toList().map { it.code } + listOf(17, 31, 73, 47, 23), 64)
    }.chunked(16).joinToString("") { it.reduce(Int::xor).toString(16).padStart(2, '0') }

    private fun MutableList<Int>.knotHashRound(lengths: List<Int>, round: Int) {
        var currentPosition = 0
        var skipSize = 0
        repeat(round) {
            lengths.forEach {
                subList(0, it).reverse()
                val move = it + skipSize
                Collections.rotate(this, -move)
                currentPosition += move
                skipSize++
            }
        }
        Collections.rotate(this, currentPosition)
    }
}
