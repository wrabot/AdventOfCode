package aoc2022

import Day
import tools.board.Board
import tools.board.XY
import tools.board.Direction4
import tools.board.Direction4.*

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1() =
        solve { State(XY((width + it.position.x) % width, (height + it.position.y) % height), it.direction) }

    override fun solvePart2() = solve { mapping[it.position] ?: it }

    // mapping for part 2 from input when invalid cell occurs
    private val mapping = mutableMapOf<XY, State>().apply {
        if (lines.size < 200) {
            // test1
            val blockSize = 4
            repeat(blockSize) { blockIndex ->
                //4->6
                this[XY(16, 4 + blockIndex)] = State(XY(15 - blockIndex, 8), South)
                //5->2
                this[XY(8 + blockIndex, 12)] = State(XY(3 - blockIndex, 7), North)
                //3->1
                this[XY(4 + blockIndex, -1)] = State(XY(8, blockIndex), East)
            }
        } else {
            // input
            val blockSize = 50
            repeat(blockSize) { blockIndex ->
                //1->6
                this[XY(50 + blockIndex, -1)] = State(XY(0, 150 + blockIndex), East)
                //6->5
                this[XY(150, 150 + blockIndex)] = State(XY(50 + blockIndex, 149), North)
                //2->6
                this[XY(100 + blockIndex, -1)] = State(XY(blockIndex, 199), North)
                //4->1
                this[XY(-1, 100 + blockIndex)] = State(XY(50, 49 - blockIndex), East)
                //1->4
                this[XY(-1, blockIndex)] = State(XY(0, 149 - blockIndex), East)
                //4->3
                this[XY(blockIndex, -1)] = State(XY(50, 50 + blockIndex), East)
                //6->2
                this[XY(blockIndex, 200)] = State(XY(100 + blockIndex, 0), South)
                //2->3
                this[XY(100 + blockIndex, 200)] = State(XY(99, 50 + blockIndex), West)
                //6->1
                this[XY(-1, 150 + blockIndex)] = State(XY(50 + blockIndex, 0), South)
                //3->4
                this[XY(-1, 50 + blockIndex)] = State(XY(blockIndex, 100), South)
                //5->6
                this[XY(50 + blockIndex, 200)] = State(XY(49, 150 + blockIndex), West)
                //3->2
                this[XY(150, 50 + blockIndex)] = State(XY(100 + blockIndex, 49), North)
                //2->5
                this[XY(150, blockIndex)] = State(XY(99, 149 - blockIndex), West)
                //5->2
                this[XY(150, 100 + blockIndex)] = State(XY(149, 49 - blockIndex), West)
            }
        }
    }

    // common

    private fun solve(mapping: Board<Cell>.(State) -> State): Int {
        val board = createBoard()
        var state = State(start, East)
        moves.forEach { move ->
            repeat(move.first) {
                board[state.position].move = state.direction.c
                var next = state
                while (true) {
                    next = board.mapping(State(next.position + state.direction.xy, state.direction))
                    when (board[next.position].content) {
                        '#' -> break
                        '.' -> {
                            state = next
                            break
                        }
                    }
                }
            }
            state = State(state.position, state.direction.turn(move.second))
        }
        return (state.position.y + 1) * 1000 + 4 * (state.position.x + 1) + when (state.direction) {
            East -> 0
            North -> 3
            West -> 2
            South -> 1
        }
    }

    private data class State(val position: XY, val direction: Direction4)

    private data class Cell(val content: Char, var move: Char? = null) {
        override fun toString() = (move ?: content).toString()
    }

    private fun createBoard() = lines.dropLast(2).run {
        val width = maxOf { it.length }
        Board(width, size, joinToString("") { it.padEnd(width, ' ') }.map { Cell(it) })
    }

    private val start = XY(lines[0].indexOfFirst { it != ' ' }, 0)
    private val moves = lines.last().replace("R", " R ").replace("L", " L ").split(" ").plus(" ")
        .chunked(2) {
            it[0].toInt() to when (it[1]) {
                "R" -> -1
                "L" -> 1
                else -> 0
            }
        }
}
