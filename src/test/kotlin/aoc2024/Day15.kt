package aoc2024

import Day
import tools.XY
import tools.board.Board
import tools.board.CharCell
import tools.board.Direction4
import tools.board.toBoard

class Day15(test: Int? = null) : Day(test) {
    private val parts = input.split("\n\n")
    private val directions = parts[1].lines().flatMap { line ->
        line.map { d -> Direction4.entries.first { it.c == d } }
    }

    override fun solvePart1(): Int {
        val map = parts[0].lines().toBoard { CharCell(it) }
        val start = map.xy.first { map[it].c == '@' }

        var current = start
        directions.forEach { d ->
            var size = 1
            while (map[current + d.xy * size].c != '.') {
                if (map[current + d.xy * size].c == '#') return@forEach
                size++
            }
            repeat(size) {
                map[current + d.xy * (size - it)].c = map[current + d.xy * (size - it - 1)].c
            }
            map[current].c = '.'
            current += d.xy
        }

        return map.xy.filter { map[it].c == 'O' }.sumOf { it.x + 100 * it.y }
    }

    override fun solvePart2(): Int {
        val map = parts[0].lines().map {
            it.replace("#", "##")
                .replace(".", "..")
                .replace("O", "[]")
                .replace("@", "@.")
        }.toBoard { CharCell(it) }
        val start = map.xy.first { map[it].c == '@' }

        var current = start
        directions.forEach { d ->
            if (d.xy.x != 0) {
                var size = 1
                while (map[current + d.xy * size].c != '.') {
                    if (map[current + d.xy * size].c == '#') return@forEach
                    size++
                }
                repeat(size) {
                    map[current + d.xy * (size - it)].c = map[current + d.xy * (size - it - 1)].c
                }
            } else {
                if (!canMoveY(map, current, d.xy)) return@forEach
                moveY(map, current, d.xy)
            }
            map[current].c = '.'
            current += d.xy
            map[current].c = '@'
        }

        return map.xy.filter { map[it].c == '[' }.sumOf { it.x + 100 * it.y }
    }

    private fun canMoveY(map: Board<CharCell>, position: XY, direction: XY): Boolean {
        val next = position + direction
        return when (val c = map[next].c) {
            '.' -> true
            '#' -> false
            else -> {
                val w = if (c == '[') Direction4.East.xy else Direction4.West.xy
                canMoveY(map, next, direction) && canMoveY(map, next + w, direction)
            }
        }
    }

    private fun moveY(map: Board<CharCell>, position: XY, direction: XY) {
        val next = position + direction
        val w = when (map[next].c) {
            '[' -> Direction4.East.xy
            ']' -> Direction4.West.xy
            else -> null
        }
        if (w != null) {
            moveY(map, next, direction)
            moveY(map, next + w, direction)
        }
        map[next].c = map[position].c
        map[position].c = '.'
    }
}
