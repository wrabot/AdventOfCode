@file:Suppress("PrivatePropertyName")

package aoc2022

import Day
import tools.board.Board
import tools.board.Direction
import tools.board.Direction.*

class Day23(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val board = createBoard()
        repeat(10) { board.nextTurn(it) }
        val elves = board.xy.filter { point -> board[point].isElf() }
        val xRange = elves.minOf { it.x }..elves.maxOf { it.x }
        val yRange = elves.minOf { it.y }..elves.maxOf { it.y }
        return board.xy.count { it.x in xRange && it.y in yRange && board[it].isFree() }
    }

    override fun solvePart2(): Int {
        val board = createBoard()
        var turnIndex = 0
        while (board.nextTurn(turnIndex)) {
            turnIndex++
        }
        return turnIndex + 1
    }

    private fun createBoard(): Board<Cell> {
        val size = 200 // raise if invalid cell exception
        val initialWidth = lines[0].length
        val initialHeight = lines.size
        return Board(size, size, (0 until size).flatMap { y ->
            (0 until size).map { x ->
                Cell(
                    lines.getOrNull(y - size / 2 + initialHeight / 2)?.getOrNull(x - size / 2 + initialWidth / 2) ?: '.'
                )
            }
        })
    }

    private fun Board<Cell>.nextTurn(turnIndex: Int): Boolean {
        val directions = directions[turnIndex % directions.size]
        val movingElves = xy.filter { point -> this[point].isElf() && neighbors8(point).any { this[it].isElf() } }
        if (movingElves.isEmpty()) return false
        val moves = movingElves.mapNotNull { origin ->
            directions.find { areFree(origin, it.first) }?.let { origin to it.second }
        }
            .groupBy({ it.first + it.second.delta }, { it.first }).mapNotNull { it.value.singleOrNull()?.to(it.key) }
        moves.forEach {
            this[it.first].setFree()
            this[it.second].setElf()
        }
        return true
    }

    private fun Board<Cell>.areFree(origin: Board.XY, directions: List<Direction>) =
        directions.all { this[origin + it.delta].isFree() }


    private val directions = listOf(
        listOf(
            listOf(North, NorthEast, NorthWest) to North,
            listOf(South, SouthEast, SouthWest) to South,
            listOf(West, NorthWest, SouthWest) to West,
            listOf(East, NorthEast, SouthEast) to East,
        ),
        listOf(
            listOf(South, SouthEast, SouthWest) to South,
            listOf(West, NorthWest, SouthWest) to West,
            listOf(East, NorthEast, SouthEast) to East,
            listOf(North, NorthEast, NorthWest) to North,
        ),
        listOf(
            listOf(West, NorthWest, SouthWest) to West,
            listOf(East, NorthEast, SouthEast) to East,
            listOf(North, NorthEast, NorthWest) to North,
            listOf(South, SouthEast, SouthWest) to South,
        ),
        listOf(
            listOf(East, NorthEast, SouthEast) to East,
            listOf(North, NorthEast, NorthWest) to North,
            listOf(South, SouthEast, SouthWest) to South,
            listOf(West, NorthWest, SouthWest) to West,
        ),
    )

    data class Cell(var content: Char) {
        fun isFree() = content == '.'
        fun setFree() {
            content = '.'
        }

        fun isElf() = content == '#'
        fun setElf() {
            content = '#'
        }

        override fun toString() = content.toString()
    }
}
