package aoc2020

import tools.Day

class Day17(test: Int? = null) : Day(2020, 17, test) {
    override fun getPart1() = cycle(lines.createCells(null))
    override fun getPart2() = cycle(lines.createCells(0))

    private fun List<String>.createCells(w: Int?) = mapIndexed { y, s ->
        s.mapIndexedNotNull { x, c -> if (c == '#') Position(x, y, 0, w) else null }
    }.flatten().toSet()

    data class Position(val x: Int, val y: Int, val z: Int, val w: Int? = null) {
        fun neighbors(): List<Position> = mutableListOf<Position>().apply {
            for (dx in -1..1) {
                for (dy in -1..1) {
                    for (dz in -1..1) {
                        for (dw in if (w != null) -1..1 else 0..0) {
                            if (dx != 0 || dy != 0 || dz != 0 || dw != 0) {
                                add(Position(x + dx, y + dy, z + dz, w?.let { it + dw }))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun cycle(init: Set<Position>): Int {
        val neighbors = mutableMapOf<Position, List<Position>>()
        return (1..6).fold(init) { cells, _ ->
            cells.flatMap { cell -> neighbors.getOrPut(cell) { cell.neighbors() } }.groupingBy { it }.eachCount()
                .filter { it.value == 3 || (it.value == 2 && it.key in cells) }.keys
        }.count()
    }
}
