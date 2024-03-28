package aoc2019

import Day
import tools.board.toBoard
import tools.board.toGraph
import tools.log

class Day18(test: Int? = null) : Day(test) {
    override fun solvePart1() = lines.toBoard { it }.run {
        toGraph(
            isStart = { this == '@' || isLowerCase() },
            isEnd = { isLowerCase() },
            isWall = { this == '#' },
        ) { Link(it.map(cells::get)) }
    }.getKeysPart1('@')

    private val cachePart1 = mutableMapOf<Pair<Char, Set<Char>>, Int>()
    private fun Map<Char, Map<Char, Link>>.getKeysPart1(
        current: Char,
        found: Set<Char> = emptySet(),
        distance: Int = 0
    ): Int = distance + cachePart1.getOrPut(current to found) {
        if (found.size == keys.size - 1) 0 else this[current].orEmpty()
            .filter { it.key !in found && found.containsAll(it.value.keys) }
            .minOf { getKeysPart1(it.key, found + it.key, it.value.distance) }
    }

    override fun solvePart2() = lines.toBoard { it }.run {
        toGraph(
            isStart = { isDigit() || isLowerCase() },
            isEnd = { isLowerCase() },
            isWall = { this == '#' },
        ) { Link(it.map(cells::get)) }
    }.getKeysPart2("1234")

    private val cachePart2 = mutableMapOf<Pair<String, Set<Char>>, Int>()
    private fun Map<Char, Map<Char, Link>>.getKeysPart2(
        bots: String,
        found: Set<Char> = emptySet(),
        distance: Int = 0
    ): Int = distance + cachePart2.getOrPut(bots to found) {
        if (found.size == keys.size - bots.length) 0 else bots.mapNotNull { current ->
            this[current].orEmpty()
                .filter { it.key !in found && found.containsAll(it.value.keys) }
                .minOfOrNull { getKeysPart2(bots.replace(current, it.key), found + it.key, it.value.distance) }
        }.min()
    }

    // common

    private data class Link(val distance: Int, val keys: List<Char>) {
        constructor(path: List<Char>) : this(path.size, path.filter { it.isUpperCase() }.map { it.lowercase().first() })
    }
}


