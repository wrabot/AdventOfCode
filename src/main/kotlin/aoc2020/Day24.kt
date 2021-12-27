package aoc2020

import forEachInput
import tools.log

object Day24 {
    fun solve() = forEachInput(2020, 24, 3) { lines ->
        log("part 1: ")
        val blacks = mutableSetOf<Tile>()
        lines.forEach { Tile(0, 0).goToTile(it).run { if (!blacks.remove(this)) blacks.add(this) } }
        blacks.size.log()

        log("part 2: ")
        val directions = listOf("e", "w", "ne", "nw", "se", "sw")
        val neighbors = mutableMapOf<Tile, List<Tile>>()
        (1..100).fold(blacks.toSet()) { tiles, _ ->
            tiles.flatMap { tile -> neighbors.getOrPut(tile) { directions.map { tile.goToTile(it) } } }
                .groupingBy { it }.eachCount().filter { it.value == 2 || (it.value == 1 && it.key in tiles) }.keys
        }.size.log()
    }

    data class Tile(val x: Int, val y: Int) {
        fun goToTile(path: String): Tile {
            var x = x
            var y = y
            var previous: Char? = null
            path.forEach {
                when (it) {
                    'n' -> y--
                    's' -> y++
                    'e' -> if (previous != 's') x++
                    'w' -> if (previous != 'n') x--
                }
                previous = it
            }
            return Tile(x, y)
        }
    }
}
