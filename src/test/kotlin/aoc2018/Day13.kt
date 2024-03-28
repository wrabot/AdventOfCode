package aoc2018

import Day
import tools.board.Board
import tools.board.Direction4
import tools.board.Direction4.*
import tools.board.toBoard

class Day13(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(true)
    override fun solvePart2() = solve(false)

    private fun solve(stopOnCollision: Boolean): String {
        while (carts.size > 1) {
            for (cart in carts.sortedBy { tracks.indexOf(it.xy) }) {
                cart.moveOn(tracks)
                val collision = carts.find(cart::collidesWith) ?: continue
                carts.remove(collision)
                carts.remove(cart)
                if (stopOnCollision) return cart.toResult()
            }
        }
        return carts.single().toResult()
    }

    data class Cart(var xy: Board.XY, var direction: Direction4) {
        private var turn: Int = 0

        fun moveOn(tracks: Board<Char>) {
            xy += direction.xy
            direction = when (tracks[xy]) {
                '+' -> when (turn++ % 3) {
                    0 -> direction.left
                    2 -> direction.right
                    else -> direction
                }

                '/' -> when (direction) {
                    North, South -> direction.right
                    East, West -> direction.left
                }

                '\\' -> when (direction) {
                    North, South -> direction.left
                    East, West -> direction.right
                }

                ' ' -> error("!!! empty")
                else -> direction
            }
        }

        fun collidesWith(other: Cart) = this != other && xy == other.xy
        fun toResult() = xy.run { "$x,$y" }
    }

    private val tracks = lines.toBoard { it }
    private val carts = tracks.xy.map { it to tracks[it] }.mapNotNull { cell ->
        Cart(cell.first, Direction4.entries.find { it.c == cell.second } ?: return@mapNotNull null)
    }.toMutableList()
}
