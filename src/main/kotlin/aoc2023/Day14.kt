package aoc2023

import tools.board.Board
import Day

class Day14(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val dish = createDish()
        dish.tiltNorth()
        return dish.weight()
    }

    override fun solvePart2(): Int {
        val dish = createDish()
        val cycles = 1000000000
        val done = mutableMapOf<String, Int>()
        for (i in 1..cycles) {
            dish.cycle()
            val dishKey = dish.toString()
            val first = done[dishKey]
            if (first != null) {
                repeat((cycles - first) % (i - first)) {
                    dish.cycle()
                }
                return dish.weight()
            }
            done[dishKey] = i
        }
        return dish.weight() // should not happen !!!
    }

    private fun Board<Cell>.cycle() {
        tiltNorth()
        tiltWest()
        tiltSouth()
        tiltEast()
    }

    private fun Board<Cell>.tiltNorth() = yRange.flatMap { y ->
        xRange.map { x ->
            if (this[x, y].c == 'O') {
                val destination = (y downTo 1).firstOrNull { this[x, it - 1].c != '.' } ?: 0
                this[x, y].c = '.'
                this[x, destination].c = 'O'
            }
        }
    }

    private fun Board<Cell>.tiltWest() = yRange.flatMap { y ->
        xRange.map { x ->
            if (this[x, y].c == 'O') {
                val destination = (x downTo 1).firstOrNull { this[it - 1, y].c != '.' } ?: 0
                this[x, y].c = '.'
                this[destination, y].c = 'O'
            }
        }
    }

    private fun Board<Cell>.tiltSouth() = yRange.reversed().flatMap { y ->
        xRange.map { x ->
            if (this[x, y].c == 'O') {
                val destination = (y..height - 2).firstOrNull { this[x, it + 1].c != '.' } ?: (height - 1)
                this[x, y].c = '.'
                this[x, destination].c = 'O'
            }
        }
    }

    private fun Board<Cell>.tiltEast() = yRange.flatMap { y ->
        xRange.reversed().map { x ->
            if (this[x, y].c == 'O') {
                val destination = (x..width - 2).firstOrNull { this[it + 1, y].c != '.' } ?: (width - 1)
                this[x, y].c = '.'
                this[destination, y].c = 'O'
            }
        }
    }

    private fun createDish() = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it) } })
    private fun Board<Cell>.weight() = yRange.sumOf { y ->
        (height - y) * xRange.count { this[it, y].c == 'O' }
    }

    data class Cell(var c: Char) {
        override fun toString() = c.toString()
    }
}
