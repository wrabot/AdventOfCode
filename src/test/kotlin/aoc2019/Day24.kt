package aoc2019

import Day
import tools.board.Board
import tools.board.toBoard
import tools.log

class Day24(var test: Int? = null) : Day(test) {
    override fun solvePart1(): Long {
        var current = grid
        val previous = mutableSetOf(current.toString())
        while (true) {
            current = with(current) {
                Board(width, height, cells.indices.map { index ->
                    nextCell(cells[index], neighbors4(xy[index]).count { this[it] == '#' })
                })
            }
            if (!previous.add(current.toString())) return current.cells.foldIndexed(0L) { index, acc, c ->
                if (c == '#') acc + (1L shl index) else acc
            }
        }
    }

    override fun solvePart2(): Int {
        val times = if (test == null) 200 else 10
        var boards = Array(times + 1) { Board(5, 5, (0..24).map { '.' }) }
        boards[times / 2] = grid
        repeat(times) { boards = boards.next() }
        return boards.sumOf { it.cells.countCell() }
    }

    // common

    private fun nextCell(cell: Char, neighbors: Int) = when (neighbors) {
        1 -> '#'
        2 -> if (cell == '.') '#' else '.'
        else -> '.'
    }

    private val grid = lines.toBoard { it }
    
    // part 2

    private fun Array<Board<Char>>.next() = Array(size) { level ->
        val upBoard = getOrNull(level + 1)
        val currentBoard = get(level)
        val downBoard = getOrNull(level - 1)
        Board(5, 5, (0..24).map { index ->
            if (index == center) return@map '.'
            val up = upBoard?.upNeighbors(index) ?: 0
            val current = currentBoard.currentNeighbors(index)
            val down = downBoard?.downNeighbors(index) ?: 0
            nextCell(currentBoard.cells[index], up + down + current)
        })
    }

    private fun Board<Char>.upNeighbors(index: Int): Int {
        val neighbors = upDown.filter { index in it.value }.map { it.key }
        return neighbors.count { cells[it] == '#' }
    }

    private fun Board<Char>.currentNeighbors(index: Int) = neighbors4(xy[index]).count { this[it] == '#' }

    private fun Board<Char>.downNeighbors(index: Int): Int {
        val neighbors = upDown[index] ?: return 0
        return neighbors.count { cells[it] == '#' }
    }

    private fun indexOf(x: Int, y: Int) = grid.indexOf(Board.XY(x, y))
    private fun List<Char>.countCell() = count { it == '#' }

    private val center = indexOf(2, 2)
    private val upDown = mapOf(
        indexOf(1, 2)!! to (0..4).map { indexOf(0, it)!! },
        indexOf(3, 2)!! to (0..4).map { indexOf(4, it)!! },
        indexOf(2, 1)!! to (0..4).map { indexOf(it, 0)!! },
        indexOf(2, 3)!! to (0..4).map { indexOf(it, 4)!! },
    )
}
