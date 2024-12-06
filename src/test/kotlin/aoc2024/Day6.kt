package aoc2024

import Day
import tools.board.Direction4
import tools.board.toBoard

class Day6(test: Int? = null) : Day(test) {
    private val board = lines.toBoard { it }
    private val start = board.xy.first { board[it] == '^' }
    private val positions = mutableSetOf(start)

    init {
        var position = start
        var direction = Direction4.North
        while (true) {
            val next = position + direction.xy
            val content = board.getOrNull(next) ?: break
            if (content == '#') direction = direction.turn(-1) else position = next
            positions.add(position)
        }
    }

    override fun solvePart1() = positions.size

    override fun solvePart2() = positions.parallelStream().filter { obstacle ->
        var position = start
        var direction = Direction4.North
        val trace = mutableSetOf(position to direction)
        do {
            val next = position + direction.xy
            val content = board.getOrNull(next) ?: return@filter false
            if (next == obstacle || content == '#') direction = direction.turn(-1) else position = next
        } while (trace.add(position to direction))
        true
    }.count()
}
