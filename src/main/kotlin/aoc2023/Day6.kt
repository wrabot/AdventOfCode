package aoc2023

import Day
import tools.toWords
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

class Day6(test: Int? = null) : Day(2023, 6, test) {
    override fun solvePart1() = racesPart1.fold(1) { acc, race -> acc * race.records1() }

    private val racesPart1 = lines[0].toWords().zip(lines[1].toWords()).drop(1)
        .map { Race(it.first.toLong(), it.second.toLong()) }

    override fun solvePart2() = racePart2.records2().toLong()

    private val racePart2 = Race(
        lines[0].removePrefix("Time:").replace(" ", "").toLong(),
        lines[1].removePrefix("Distance:").replace(" ", "").toLong()
    )

    data class Race(val time: Long, val distance: Long) {
        fun records1() = (1..<time).count { (time - it) * it > distance }
        fun records2(): Double {
            val b = time.toDouble() / 2
            val d = sqrt(b * b - distance)
            return ceil(b + d) - floor(b - d) - 1
        }
    }
}
