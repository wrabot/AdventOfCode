package aoc2017

import Day
import tools.board.Direction4
import tools.board.toBoard

class Day19(test: Int? = null) : Day(test) {
    override fun solvePart1() = word
    override fun solvePart2() = steps

    private val word = StringBuilder()
    private var steps = 1

    init {
        val board = lines.toBoard { it }
        var p = board.xy.first { board[it] == '|' }
        var d = Direction4.South
        while (true) {
            p += d.xy
            when (val c = board.getOrNull(p) ?: break) {
                ' ' -> break
                '+' -> d = if (board[p + d.left.xy] != ' ') d.left else d.right
                in 'A'..'Z' -> word.append(c)
            }
            steps++
        }
    }
}
