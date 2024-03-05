package aoc2016

import Day

class Day16(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(272)
    override fun solvePart2() = solve(35651584)

    fun solve(size: Int): String {
        val disk = BooleanArray(size * 2) { input.getOrNull(it) == '1' }
        var length = input.length
        while (length < size) {
            for (i in 1..length) disk[length + i] = !disk[length - i]
            length = 2 * length + 1
        }
        length = size
        do {
            length /= 2
            repeat(length) { disk[it] = !(disk[2 * it] xor disk[2 * it + 1]) }
        } while (length % 2 == 0)
        return disk.take(length).joinToString("") { if (it) "1" else "0" }
    }
}
