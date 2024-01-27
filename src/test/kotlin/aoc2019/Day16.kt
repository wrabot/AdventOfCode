package aoc2019

import Day
import kotlin.math.abs

class Day16(test: Int? = null) : Day(test) {
    override fun solvePart1(): String {
        var signal = lines[0].map { it - '0' }
        repeat(100) {
            signal = signal.indices.map { position ->
                abs(signal.foldIndexed(0) { index, acc, s -> acc + s * pattern(position, index) }) % 10
            }
        }
        return signal.take(8).joinToString("")
    }

    private val pattern = listOf(0, 1, 0, -1)
    private fun pattern(position: Int, index: Int) = pattern[((index + 1) / (position + 1)) % 4]

    override fun solvePart2() = lines.first().run {
        val offset = take(7).toInt()
        require(offset >= length / 2) // to be in triangle matrix with only 1
        var s = repeat(10000).substring(offset).map { it - '0' }.reversed() // compute only after offset
        repeat(100) { s = s.runningReduce { acc, i -> (i + acc) % 10 } } // cumulate from end
        s.takeLast(8).reversed().joinToString("")
    }
}


