@file:Suppress("PrivatePropertyName")

package aoc2022

import tools.board.Board
import tools.Day
import tools.board.Point

class Day23(test: Int? = null) : Day(2022, 23, test) {
    override fun solvePart1(): Int {
        val board = createBoard()
        repeat(10) { board.nextTurn(it) }
        val elves = board.points.filter { point -> board[point].isElf() }
        val xRange = elves.minOf { it.x }..elves.maxOf { it.x }
        val yRange = elves.minOf { it.y }..elves.maxOf { it.y }
        return board.points.count { it.x in xRange && it.y in yRange && board[it].isFree() }
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
                Cell(lines.getOrNull(y - size / 2 + initialHeight / 2)?.getOrNull(x - size / 2 + initialWidth / 2) ?: '.')
            }
        })
    }

    private fun Board<Cell>.nextTurn(turnIndex: Int): Boolean {
        val directions = directions[turnIndex % directions.size]
        val movingElves = points.filter { point -> this[point].isElf() && neighbors8(point).any { this[it].isElf() } }
        if (movingElves.isEmpty()) return false
        val moves = movingElves.mapNotNull { origin -> directions.find { areFree(origin, it.first) }?.let { origin to it.second } }
            .groupBy({ it.first + it.second }, { it.first }).mapNotNull { it.value.singleOrNull()?.to(it.key) }
        moves.forEach {
            this[it.first].setFree()
            this[it.second].setElf()
        }
        return true
    }

    private fun Board<Cell>.areFree(origin: Point, delta: List<Point>) = delta.all { this[origin + it].isFree() }

    private val E = Point(1, 0)
    private val W = Point(-1, 0)
    private val S = Point(0, 1)
    private val N = Point(0, -1)
    private val SE = Point(1, 1)
    private val SW = Point(-1, 1)
    private val NE = Point(1, -1)
    private val NW = Point(-1, -1)

    private val directions = listOf(
        listOf(
            listOf(N, NE, NW) to N,
            listOf(S, SE, SW) to S,
            listOf(W, NW, SW) to W,
            listOf(E, NE, SE) to E,
        ),
        listOf(
            listOf(S, SE, SW) to S,
            listOf(W, NW, SW) to W,
            listOf(E, NE, SE) to E,
            listOf(N, NE, NW) to N,
        ),
        listOf(
            listOf(W, NW, SW) to W,
            listOf(E, NE, SE) to E,
            listOf(N, NE, NW) to N,
            listOf(S, SE, SW) to S,
        ),
        listOf(
            listOf(E, NE, SE) to E,
            listOf(N, NE, NW) to N,
            listOf(S, SE, SW) to S,
            listOf(W, NW, SW) to W,
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
