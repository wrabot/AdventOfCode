package aoc2023

import tools.Day

class Day13(test: Int? = null) : Day(2023, 13, test) {
    override fun solvePart1() = maps.sumOf { it.summarize() }

    override fun solvePart2() = maps.sumOf { map ->
        val ignored = map.findBothMirror()
        map.indices.firstNotNullOf { r ->
            map[0].indices.firstNotNullOfOrNull { c ->
                map.toggle(r, c).summarize(ignored).takeIf { it != 0 }
            }
        }
    }

    private fun List<String>.toggle(r: Int, c: Int) = mapIndexed { index, s ->
        if (index != r) s else s.replaceRange(c..c, if (s[c] == '.') "#" else ".")
    }

    private fun List<String>.summarize(ignored: Pair<Int, Int>? = null) =
        findBothMirror(ignored).let { it.first * 100 + it.second }

    private fun List<String>.findBothMirror(ignored: Pair<Int, Int>? = null) =
        findMirror(ignored?.first) to transpose().findMirror(ignored?.second)

    private fun List<String>.findMirror(ignored: Int?) = (1..<size).firstOrNull { index ->
        if (index == ignored) return@firstOrNull false
        val cleaned = if (index <= size / 2) take(index * 2) else takeLast((size - index) * 2)
        cleaned == cleaned.reversed()
    } ?: 0

    private fun List<String>.transpose() = get(0).indices.map { c ->
        map { it[c] }.joinToString("")
    }

    private val maps = input.split("\n\n").map { it.split("\n") }
}
