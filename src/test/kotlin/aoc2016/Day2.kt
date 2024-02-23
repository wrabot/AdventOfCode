package aoc2016

import Day
import tools.board.Board
import tools.board.Direction4
import tools.board.toBoard

class Day2(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(keypadPart1.lines().toBoard { it })

    val keypadPart1 = """
        123
        456
        789
    """.trimIndent()

    override fun solvePart2() = solve(keypadPart2.lines().toBoard { it })

    val keypadPart2 = """
        ..1..
        .234.
        56789
        .ABC.
        ..D..
    """.trimIndent()

    fun solve(keypad: Board<Char>): String {
        var current = keypad.xy.first { keypad[it] == '5' }
        return lines.joinToString("") { line ->
            line.forEach { dir ->
                val next = current + directions[dir]!!.xy
                if (keypad.getOrNull(next)?.isLetterOrDigit() == true) current = next
            }
            keypad[current].toString()
        }
    }

    private val directions = mapOf(
        'U' to Direction4.North,
        'D' to Direction4.South,
        'L' to Direction4.West,
        'R' to Direction4.East,
    )
}
