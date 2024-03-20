package aoc2017

import Day
import tools.board.Board
import tools.board.Direction4
import tools.board.toBoard

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1() = createBoard(200).solve(10000, 2)

    override fun solvePart2() = createBoard(300).solve(10000000, 1)

    fun Board<Cell>.solve(repeat: Int, stateInc: Int): Int {
        var count = 0
        var position = Board.XY(width / 2, height / 2)
        var direction = Direction4.North
        repeat(repeat) {
            val state = this[position].state
            direction = state.nextDirection(direction)
            this[position].state = state.nextState(stateInc).also {
                if (it == State.Infected) count++
            }
            position += direction.xy
        }
        return count
    }

    enum class State(val text: String) {
        Clean("."), Weakened("W"), Infected("#"), Flagged("F");

        fun nextState(inc: Int) = State.entries[(ordinal + inc) % State.entries.size]
        fun nextDirection(direction: Direction4) = when (this) {
            Clean -> direction.left
            Weakened -> direction
            Infected -> direction.right
            Flagged -> direction.left.left
        }
    }

    data class Cell(var state: State) {
        constructor(infected: Boolean) : this(if (infected) State.Infected else State.Clean)

        override fun toString() = state.text
    }

    private fun createBoard(offset: Int): Board<Cell> {
        val size = 2 * offset + start.width
        return Board(
            size,
            size,
            List(size) { y -> List(size) { x -> Cell(start.getOrNull(x - offset, y - offset) ?: false) } }.flatten()
        )
    }

    val start = lines.toBoard { it == '#' }
}
