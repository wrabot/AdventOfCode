package aoc2015

import tools.Day

class Day14(test: Int? = null) : Day(2015, 14, test) {
    override fun solvePart1() = reindeer.maxOf { it.distance(duration) }
    override fun solvePart2(): Any {
        for (t in 1..duration) {
            reindeer.forEach { it.position = it.distance(t) }
            val max = reindeer.maxOf { it.position }
            reindeer.filter { it.position == max }.forEach { it.points++ }
        }
        return reindeer.maxOf { it.points }
    }

    data class Reindeer(val name: String, val speed: Int, val running: Int, val resting: Int) {
        private val cycle = running + resting
        var points = 0
        var position = 0
        fun distance(duration: Int) = ((duration / cycle) * running + (duration % cycle).coerceAtMost(running)) * speed
    }

    private val regex = "(.+) can fly (\\d*) km/s for (.+) seconds, but then must rest for (.+) seconds.".toRegex()
    private val duration = lines[0].toInt()
    private val reindeer = lines.drop(1).map { line ->
        regex.matchEntire(line)!!.groupValues.let { Reindeer(it[1], it[2].toInt(), it[3].toInt(), it[4].toInt()) }
    }
}
