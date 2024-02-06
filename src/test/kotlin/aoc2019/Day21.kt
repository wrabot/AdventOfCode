package aoc2019

import Day
import tools.log

class Day21(test: Int? = null) : Day(test) {
    override fun solvePart1() = Day9.Runtime(code).run {
        val program = """
            NOT C J
            AND D J
            NOT A T
            OR T J
            WALK
        """.trimIndent()
        read()
        write(program)
        read().lines().last().toInt()
    }

    override fun solvePart2() = Day9.Runtime(code).run {
        val program = """
            NOT C J
            AND D J
            AND H J
            NOT B T
            AND D T
            OR T J
            NOT A T
            OR T J
            RUN
        """.trimIndent()
        read()
        write(program)
        read().lines().last().toInt()
    }

    private fun Day9.Runtime.write(s: String) {
        s.forEach { execute(it.code.toLong()) }
        execute('\n'.code.toLong())
    }

    private fun Day9.Runtime.read(): String {
        val s = StringBuilder()
        while (true) {
            val o = execute(null) ?: break
            s.append(if (o < 127) o.toInt().toChar() else o.toString())
        }
        return s.toString()
    }

    val code = lines.first().split(",").map { it.toLong() }
}


