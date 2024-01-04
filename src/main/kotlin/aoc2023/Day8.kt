package aoc2023

import Day

class Day8(test: Int? = null) : Day(2023, 8, test) {
    override fun solvePart1() = (path("AAA") { it == "ZZZ" }.count() - 1) * directions.size

    override fun solvePart2() = map.keys.filter(::isStart).map { path(it, ::isEnd).checkPath() }
        .fold(directions.size.toLong()) { acc, path -> acc * (path.count() - 1) }

    // Assumption : on a path, stays on same exit after the same number of steps
    private fun Sequence<String>.checkPath() = apply { assert(path(last()) { it == last() }.count() == count()) }

    private fun isStart(s: String) = s.endsWith('A')
    private fun isEnd(s: String) = s.endsWith('Z')
    private fun path(start: String, isEnd: (String) -> Boolean) =
        generateSequence(start) { if (isEnd(it)) null else shortcuts[it] }

    private val directions = lines[0].map { if (it == 'L') 0 else 1 }
    private val map = lines.drop(2).associate {
        it.substring(0..2) to listOf(it.substring(7..9), it.substring(12..14))
    }

    private val shortcuts = map.keys.associateWith {
        directions.fold(it) { position, direction ->
            map[position]!![direction]
        }
    }
}
