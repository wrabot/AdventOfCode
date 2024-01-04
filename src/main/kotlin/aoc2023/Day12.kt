package aoc2023

import Day

class Day12(test: Int? = null) : Day(2023, 12, test) {
    override fun solvePart1() = rows.sumOf { it.arrangements(0, 0, 0) }

    override fun solvePart2() = rows.map { row ->
        Row(List(5) { row.record }.joinToString("?"), List(5) { row.groups }.flatten())
    }.sumOf { it.arrangements(0, 0, 0) }

    data class Row(var record: String, val groups: List<Int>) {
        private val cache = mutableMapOf<Triple<Int, Int, Int>, Long>()

        fun arrangements(groupIndex: Int, offset: Int, prefix: Int): Long =
            cache.getOrPut(Triple(groupIndex, offset, prefix)) {
                if (offset >= record.length) return@getOrPut when (prefix) {
                    0 -> if (groupIndex >= groups.size) 1L else 0L
                    else -> if (groupIndex == groups.lastIndex && groups[groupIndex] == prefix) 1L else 0L
                }
                var count = 0L
                if (record[offset] != '#') {
                    count += when (prefix) {
                        0 -> arrangements(groupIndex, offset + 1, 0)
                        groups.getOrNull(groupIndex) -> arrangements(groupIndex + 1, offset + 1, 0)
                        else -> 0L
                    }
                }
                if (record[offset] != '.' && groupIndex < groups.size && prefix < groups[groupIndex]) {
                    count += arrangements(groupIndex, offset + 1, prefix + 1)
                }
                count
            }
    }

    private val rows = lines.map { line ->
        val (record, groups) = line.split(" ")
        Row(record, groups.split(",").map { it.toInt() })
    }
}
