package aoc2022

import Day
import tools.board.Board
import tools.board.Direction4
import tools.board.toBoard
import tools.graph.shortPath

class Day24(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(0, entrance, exit)
    override fun solvePart2() = solve(solve(solve(0, entrance, exit), exit, entrance), entrance, exit)

    private fun solve(initialTime: Int, start: Board.XY, end: Board.XY) =
        shortPath(State(start, initialTime % boards.size), isEnd = { it.expedition == end }) {
            it.nextStates()
        }.size - 1 + initialTime

    private fun State.nextStates(): List<State> {
        val boardIndex = (boardIndex + 1) % boards.size
        val nextBoard = boards[boardIndex]
        return (nextBoard.neighbors4(expedition) + expedition).mapNotNull {
            if (nextBoard[it].isEmpty()) State(it, boardIndex) else null
        }
    }

    private data class State(val expedition: Board.XY, val boardIndex: Int)

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

    private fun createBoard() = lines.toBoard { c ->
        val d = Direction4.entries.find { it.c == c }
        if (d != null) Cell('.', listOf(d)) else Cell(c)
    }

    private fun Board<Cell>.nextBoard(): Board<Cell> {
        val nextWinds = xy.flatMap { xy ->
            this[xy].winds.map {
                it to Board.XY(modulo(xy.x + it.xy.x, width), modulo(xy.y + it.xy.y, height))
            }
        }.groupBy({ it.second }, { it.first })
        return Board(width, height, xy.map { Cell(this[it].content, nextWinds[it].orEmpty()) })
    }

    private fun modulo(value: Int, size: Int) = (size + value - 3) % (size - 2) + 1

    private data class Cell(
        val content: Char,
        val winds: List<Direction4> = emptyList(),
        var expedition: Boolean = false
    ) {
        fun isEmpty() = content != '#' && winds.isEmpty()

        override fun toString() = when {
            expedition && winds.isNotEmpty() -> "!"
            winds.isNotEmpty() -> (winds.singleOrNull()?.c ?: winds.size).toString()
            expedition -> "E"
            else -> content.toString()
        }
    }
}
