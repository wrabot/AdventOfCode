package aoc2023

import tools.Day
import tools.splitWith

class Day5(test: Int? = null) : Day(2023, 5, test) {
    override fun solvePart1() = seeds.minOfOrNull { seed ->
        maps.fold(seed) { acc, map ->
            map.find { acc in it.sourceRange }?.move(acc) ?: acc
        }
    }!!

    override fun solvePart2() =
        maps.fold(seeds.chunked(2) { (first, length) -> first..<first + length }) { acc, map ->
            acc.flatMap { seedRange ->
                var remaining = listOf(seedRange)
                val newRanges = mutableListOf<LongRange>()
                map.forEach { mapItem ->
                    val notModified = mutableListOf<LongRange>()
                    remaining.forEach { current ->
                        current.splitWith(mapItem.sourceRange).run {
                            first?.let { notModified.add(it) }
                            second?.let { newRanges.add(mapItem.move(it.first)..mapItem.move(it.last)) }
                            third?.let { notModified.add(it) }
                        }
                    }
                    remaining = notModified
                }
                newRanges + remaining
            }.sortedBy { it.first }
        }.first().first

    private val seeds = lines[0].removePrefix("seeds: ").split(" ").map { it.toLong() }
    private val maps = "(?s) map:\n(.*?)\n\n".toRegex().findAll("$input\n\n").map { result ->
        result.groupValues[1].lines().map { line ->
            line.split(" ").let { (destination, source, length) ->
                MapItem(destination.toLong(), source.toLong(), length.toLong())
            }
        }
    }

    data class MapItem(val destination: Long, val source: Long, val length: Long) {
        val sourceRange = source..<source + length

        fun move(s: Long) = s - source + destination
    }
}
