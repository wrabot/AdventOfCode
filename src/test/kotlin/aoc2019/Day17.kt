package aoc2019

import Day
import tools.board.Direction4
import tools.board.toBoard
import tools.log

class Day17(test: Int? = null) : Day(test) {
    override fun solvePart1() = board.xy.filter { xy ->
        board[xy] == '#' && board.neighbors4(xy).count { board[it] == '#' } == 4
    }.fold(0) { acc, xy -> acc + xy.x * xy.y }

    override fun solvePart2(): Int {
        val chars = Direction4.entries.map { it.c }
        var position = board.xy.first { board[it] in chars }
        var direction = Direction4.entries.first { board[position] == it.c }
        var count = 1
        val command = StringBuilder()
        while (true) {
            when {
                board.getOrNull(position + direction.xy) == '#' -> {
                    position += direction.xy
                    count++
                    continue
                }

                board.getOrNull(position + direction.right.xy) == '#' -> {
                    if (count != 1) {
                        command.append(',')
                        command.append(count)
                        count = 1
                        command.append(',')
                    }
                    position += direction.right.xy
                    direction = direction.right
                    command.append('R')
                }

                board.getOrNull(position + direction.left.xy) == '#' -> {
                    if (count != 1) {
                        command.append(',')
                        command.append(count)
                        count = 1
                        command.append(',')
                    }
                    position += direction.left.xy
                    direction = direction.left
                    command.append('L')
                }

                else -> {
                    command.append(',')
                    command.append(count)
                    break
                }
            }
        }

        // from command
        val main = "A,B,A,C,B,A,B,C,C,B"
        val a = "L,12,L,12,R,4"
        val b = "R,10,R,6,R,4,R,4"
        val c = "R,6,L,12,L,12"
        require(main.replace("A", a).replace("B", b).replace("C", c) == command.toString())

        val runtime = Day9.Runtime(listOf(2L) + code.drop(1))
        runtime.read()
        runtime.write(main)
        runtime.write(a)
        runtime.write(b)
        runtime.write(c)
        return runtime.write("n").lines().last().toInt()
    }

    private fun Day9.Runtime.write(s: String): String {
        s.forEach { execute(it.code.toLong()) }
        return execute('\n'.code.toLong())!!.toInt().toChar().toString() + read()
    }

    private fun Day9.Runtime.read(): String {
        val s = StringBuilder()
        while (true) {
            val o = execute(null) ?: break
            s.append(if (o < 127) o.toInt().toChar() else o.toString())
        }
        return s.toString()
    }

    val code = lines.first().split(",").map { it.toLong() }
    val board = Day9.Runtime(code).read().trim().lines().toBoard { it }
}


