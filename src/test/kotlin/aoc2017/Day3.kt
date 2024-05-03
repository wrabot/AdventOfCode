package aoc2017

import Day
import tools.board.Board.XY
import tools.board.Direction4
import tools.board.Direction8
import kotlin.math.sqrt

class Day3(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        // top rigth index is 4*layer*layer if 0 indexed
        val index = address - 1
        val layer = sqrt(index.toDouble() / 4).toInt()
        // distance is triangular waveform on layer
        var distance = index % (2 * layer)
        if (distance < layer) distance = 2 * layer - distance
        return distance
    }

    override fun solvePart2(): Int {
        var position = XY(0, 0)
        val values = mutableMapOf(position to 1)
        var direction = Direction4.South
        while (true) {
            val left = position + direction.left.xy
            position = if (left !in values) left.also { direction = direction.left } else position + direction.xy
            val value = Direction8.entries.sumOf { values.getOrDefault(position + it.xy, 0) }
            if (value > address) return value
            values[position] = value
        }
    }

    private val address = input.toInt()
}
