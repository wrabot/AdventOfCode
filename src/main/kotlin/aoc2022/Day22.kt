package aoc2022

import Day
import tools.board.Board
import tools.board.Board.XY
import tools.board.Direction
import tools.board.Direction.*
import tools.board.directions4

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1() =
        solve { State(XY((width + it.position.x) % width, (height + it.position.y) % height), it.directionIndex) }

    override fun solvePart2() = solve { mapping[it.position] ?: it }

    // mapping for part 2 from input when invalid cell occurs
    private val mapping = mutableMapOf<XY, State>().apply {
        if (lines.size < 200) {
            // test1
            val blockSize = 4
            repeat(blockSize) { blockIndex ->
                //4->6
                this[XY(16, 4 + blockIndex)] = State(XY(15 - blockIndex, 8), 1)
                //5->2
                this[XY(8 + blockIndex, 12)] = State(XY(3 - blockIndex, 7), 3)
                //3->1
                this[XY(4 + blockIndex, -1)] = State(XY(8, blockIndex), 0)
            }
        } else {
            // input
            val blockSize = 50
            repeat(blockSize) { blockIndex ->
                //1->6
                this[XY(50 + blockIndex, -1)] = State(XY(0, 150 + blockIndex), 0)
                //6->5
                this[XY(150, 150 + blockIndex)] = State(XY(50 + blockIndex, 149), 3)
                //2->6
                this[XY(100 + blockIndex, -1)] = State(XY(blockIndex, 199), 3)
                //4->1
                this[XY(-1, 100 + blockIndex)] = State(XY(50, 49 - blockIndex), 0)
                //1->4
                this[XY(-1, blockIndex)] = State(XY(0, 149 - blockIndex), 0)
                //4->3
                this[XY(blockIndex, -1)] = State(XY(50, 50 + blockIndex), 0)
                //6->2
                this[XY(blockIndex, 200)] = State(XY(100 + blockIndex, 0), 1)
                //2->3
                this[XY(100 + blockIndex, 200)] = State(XY(99, 50 + blockIndex), 2)
                //6->1
                this[XY(-1, 150 + blockIndex)] = State(XY(50 + blockIndex, 0), 1)
                //3->4
                this[XY(-1, 50 + blockIndex)] = State(XY(blockIndex, 100), 1)
                //5->6
                this[XY(50 + blockIndex, 200)] = State(XY(49, 150 + blockIndex), 2)
                //3->2
                this[XY(150, 50 + blockIndex)] = State(XY(100 + blockIndex, 49), 3)
                //2->5
                this[XY(150, blockIndex)] = State(XY(99, 149 - blockIndex), 2)
                //5->2
                this[XY(150, 100 + blockIndex)] = State(XY(149, 49 - blockIndex), 2)
            }
        }
    }

    // common

    private fun solve(mapping: Board<Cell>.(State) -> State): Int {
        val board = createBoard()
        var state = State(start, 0)
        moves.forEach { move ->
            repeat(move.first) {
                board[state.position].move = directions[state.directionIndex].c
                var next = state
                while (true) {
                    next = board.mapping(
                        State(
                            next.position + directions[state.directionIndex].delta,
                            state.directionIndex
                        )
                    )
                    when (board[next.position].content) {
                        '#' -> break
                        '.' -> {
                            state = next
                            break
                        }
                    }
                }
            }
            state = State(
                state.position,
                (directions.size + state.directionIndex + move.second) % directions.size
            )
        }
        return (state.position.y + 1) * 1000 + 4 * (state.position.x + 1) + state.directionIndex
    }

    data class State(val position: XY, val directionIndex: Int)

    data class Cell(val content: Char, var move: Char? = null) {
        override fun toString() = (move ?: content).toString()
    }

    private fun createBoard() = lines.dropLast(2).run {
        val width = maxOf { it.length }
        Board(width, size, joinToString("") { it.padEnd(width, ' ') }.map { Cell(it) })
    }

    private val directions = listOf(East, South, West, North)
    private val start = XY(lines[0].indexOfFirst { it != ' ' }, 0)
    private val moves = lines.last().replace("R", " R ").replace("L", " L ").split(" ").plus(" ")
        .chunked(2) {
            it[0].toInt() to when (it[1]) {
                "R" -> 1
                "L" -> -1
                else -> 0
            }
        }
}
