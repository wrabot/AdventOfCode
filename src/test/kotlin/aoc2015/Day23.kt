package aoc2015

import Day
import tools.text.toWords

class Day23(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(intArrayOf(0, 0))
    override fun solvePart2() = solve(intArrayOf(1, 0))

    fun solve(registers: IntArray): Int {
        var index = 0
        while (index < lines.size) {
            val line = lines[index++]
            when (line.take(3)) {
                "hlf" -> registers[line.register] /= 2
                "tpl" -> registers[line.register] *= 3
                "inc" -> registers[line.register]++
                "jmp" -> index += line.offset
                "jie" -> if (registers[line.register] % 2 == 0) index += line.offset
                "jio" -> if (registers[line.register] == 1) index += line.offset
            }
        }
        return registers[1]
    }

    private val String.register get() = this[4] - 'a'
    private val String.offset get() = toWords().last().toInt() - 1
}
