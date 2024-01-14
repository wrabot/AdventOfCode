package aoc2022

import Day
import tools.board.Board
import tools.board.toBoard
import tools.graph.shortPath

class Day24(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(0, entrance, exit)
    override fun solvePart2() = solve(solve(solve(0, entrance, exit), exit, entrance), entrance, exit)

    private fun solve(initialTime: Int, start: Board.XY, end: Board.XY) =
        shortPath(State(start, initialTime % boards.size), isEnd = { expedition == end }) {
            nextStates()
        }.size - 1 + initialTime

    private fun State.nextStates(): List<State> {
        val boardIndex = (boardIndex + 1) % boards.size
        val nextBoard = boards[boardIndex]
        return (nextBoard.neighbors4(expedition) + expedition).mapNotNull {
            if (nextBoard[it].isEmpty()) State(it, boardIndex) else null
        }
    }

    data class State(val expedition: Board.XY, val boardIndex: Int)

    private val boards = mutableListOf<Board<Cell>>()
    private val entrance = Board.XY(1, 0)
    private val exit: Board.XY

    init {
        var board = createBoard()
        do {
            boards.add(board)
            board = board.nextBoard()
        } while (board.cells != boards.first().cells)
        exit = Board.XY(board.width - 2, board.height - 1)
    }

    // TODO use directions
    private fun createBoard() = lines.toBoard {
        when (it) {
            '>' -> Cell('.', listOf(Board.XY(1, 0)))
            '<' -> Cell('.', listOf(Board.XY(-1, 0)))
            'v' -> Cell('.', listOf(Board.XY(0, 1)))
            '^' -> Cell('.', listOf(Board.XY(0, -1)))
            else -> Cell(it)
        }
    }

    private fun Board<Cell>.nextBoard(): Board<Cell> {
        val nextWinds = xy.flatMap { point ->
            this[point].winds.map {
                it to Board.XY(modulo(point.x + it.x, width), modulo(point.y + it.y, height))
            }
        }.groupBy({ it.second }, { it.first })
        return Board(width, height, xy.map { Cell(this[it].content, nextWinds[it].orEmpty()) })
    }

    private fun modulo(value: Int, size: Int) = (size + value - 3) % (size - 2) + 1

    data class Cell(val content: Char, val winds: List<Board.XY> = emptyList(), var expedition: Boolean = false) {
        fun isEmpty() = content != '#' && winds.isEmpty()

        override fun toString() = when {
            expedition && winds.isNotEmpty() -> "!"
            winds.isNotEmpty() -> when (winds.singleOrNull()) {
                Board.XY(1, 0) -> ">"
                Board.XY(-1, 0) -> "<"
                Board.XY(0, 1) -> "v"
                Board.XY(0, -1) -> "^"
                else -> winds.size.toString()
            }

            expedition -> "E"
            else -> content.toString()
        }
    }
}
