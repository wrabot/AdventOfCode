@file:Suppress("PrivatePropertyName")

package aoc2022

import tools.Board
import tools.Day
import tools.Point

class Day24(test: Int? = null) : Day(2022, 24, test, true) {
    override fun solvePart1() = solve(0, entrance, exit)
    override fun solvePart2() = solve(solve(solve(0, entrance, exit), exit, entrance), entrance, exit)

    private fun solve(initialTime: Int, start: Point, end: Point): Int {
        val done = mutableSetOf<State>()
        val todo = mutableListOf(State(initialTime, start, initialTime % boards.size))
        while (todo.isNotEmpty()) {
            val state = todo.removeFirst()
            if (state.expedition == end) return state.time
            if (state !in done) {
                done.add(state)
                todo.addAll(state.nextStates())
            }
        }
        return -1
    }

    private fun State.nextStates(): List<State> {
        val boardIndex = (boardIndex + 1) % boards.size
        val nextBoard = boards[boardIndex]
        return (nextBoard.neighbors4(expedition) + expedition).mapNotNull {
            if (nextBoard[it].isEmpty()) State(time + 1, it, boardIndex) else null
        }
    }

    data class State(val time: Int, val expedition: Point, val boardIndex: Int) {
        override fun equals(other: Any?) = other is State && expedition == other.expedition && boardIndex == other.boardIndex
        override fun hashCode(): Int {
            var result = expedition.hashCode()
            result = 31 * result + boardIndex
            return result
        }
    }

    private val boards = mutableListOf<Board<Cell>>()
    private val entrance = Point(1, 0)
    private val exit: Point

    init {
        var board = createBoard()
        do {
            boards.add(board)
            board = board.nextBoard()
        } while (board.cells != boards.first().cells)
        exit = Point(board.width - 2, board.height - 1)
    }

    private fun createBoard() = Board(lines[0].length, lines.size, lines.flatMap { line ->
        line.map {
            when (it) {
                '>' -> Cell('.', listOf(Point(1, 0)))
                '<' -> Cell('.', listOf(Point(-1, 0)))
                'v' -> Cell('.', listOf(Point(0, 1)))
                '^' -> Cell('.', listOf(Point(0, -1)))
                else -> Cell(it)
            }
        }
    })

    private fun Board<Cell>.nextBoard(): Board<Cell> {
        val nextWinds = points.flatMap { point ->
            this[point].winds.map {
                it to Point(modulo(point.x + it.x, width), modulo(point.y + it.y, height))
            }
        }.groupBy({ it.second }, { it.first })
        return Board(width, height, points.map { Cell(this[it].content, nextWinds[it].orEmpty()) })
    }

    private fun modulo(value: Int, size: Int) = (size + value - 3) % (size - 2) + 1

    data class Cell(val content: Char, val winds: List<Point> = emptyList(), var expedition: Boolean = false) {
        fun isEmpty() = content != '#' && winds.isEmpty()

        override fun toString() = when {
            expedition && winds.isNotEmpty() -> "!"
            winds.isNotEmpty() -> when (winds.singleOrNull()) {
                Point(1, 0) -> ">"
                Point(-1, 0) -> "<"
                Point(0, 1) -> "v"
                Point(0, -1) -> "^"
                else -> winds.size.toString()
            }
            expedition -> "E"
            else -> content.toString()
        }
    }
}
