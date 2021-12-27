package aoc2021

import forEachInput
import tools.log

object Day3 {
    fun solve() = forEachInput(2021, 3, 1, 2) { lines ->
        log("part 1: ")
        val length = lines[0].length
        val threshold = lines.size / 2
        val gammaBits = (0 until length).map { position ->
            if (lines.count { it[position] == '1' } > threshold) '1' else '0'
        }.joinToString("")
        val epsilonBits = gammaBits.map { if (it == '1') '0' else '1' }.joinToString("")
        (gammaBits.toInt(2) * epsilonBits.toInt(2)).log()

        log("part 2: ")
        val oxygen = rating(lines, '0', '1')
        val co2 = rating(lines, '1', '0')
        (oxygen * co2).log()
    }

    private fun rating(lines : List<String>,mostZeroBit: Char, leastOrEqualZeroBit: Char): Int {
        var current = lines
        for (position in lines[0].indices) {
            if (current.size == 1) break
            val currentThreshold = current.size / 2
            val bit = if (current.count { it[position] == '0' } > currentThreshold) mostZeroBit else leastOrEqualZeroBit
            current = current.filter { it[position] == bit }
        }
        return current.first().toInt(2)
    }
}
