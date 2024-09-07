package aoc2016

import Day
import tools.XY
import tools.board.Direction4

class Day1(test: Int? = null) : Day(test) {
    override fun solvePart1() = instructions.fold(XY(0, 0) to Direction4.North) { (xy, dir), (turn, blocks) ->
        dir.turn(turn).let { xy + it.xy * blocks to it }
    }.first.manhattan()

    override fun solvePart2(): Int {
        val set = mutableSetOf<XY>()
        var position = XY(0, 0)
        var direction = Direction4.North
        instructions.forEach { (turn, blocks) ->
            direction = direction.turn(turn)
            repeat(blocks) {
                position += direction.xy
                if (!set.add(position)) return position.manhattan()
            }
        }
        return 0
    }

    private val instructions = input.split(", ").map { (if (it[0] == 'L') 1 else -1) to it.drop(1).toInt() }
}
