package aoc2022

import Day
import tools.geometry.Point
import kotlin.math.max

class Day17(test: Int? = null) : Day(test) {
    override fun solvePart1() = Board().solve(2022)
    override fun solvePart2() = Board().run {
        solve(0)
        val rocks = 1000000000000 - rockIndex
        with(params!!) { (rocks / rockStep) * sizeStep + solve(rockIndex + (rocks % rockStep).toInt()) }
    }

    private fun Board.solve(rocks: Int) = apply {
        val patterns = mutableMapOf<Pair<Int, Int>, MutableList<Point>>()
        val findParams = rocks <= 0
        while (findParams || rockIndex < rocks) {
            when (commands[commandIndex++]) {
                '<' -> if (isValid(rock, rockX - 1, rockY)) rockX--
                '>' -> if (isValid(rock, rockX + 1, rockY)) rockX++
                else -> error("!!!")
            }
            if (commandIndex == commands.length) commandIndex = 0

            if (isValid(rock, rockX, rockY - 1)) {
                rockY--
            } else {
                for (x in 0 until rock.width) {
                    for (y in 0 until rock.height) {
                        if (rock.isRock(x, y)) get(x + rockX, y + rockY).content = '#'
                    }
                }

                // use to find params
                if (findParams)
                    patterns.getOrPut((rockIndex % rockContent.size) to commandIndex) { mutableListOf() }
                        .add(Point(rockIndex, size()))

                // reduce time
                clean()

                rockIndex++
                rockX = 2
                rockY = size() + 3
            }
            if (findParams) {
                params = patterns.values.filter { it.size >= 3 }
                    .firstNotNullOfOrNull { list ->
                        list.zipWithNext().map { it.second - it.first }.distinct()
                            .singleOrNull()?.let { Params(it.x, it.y) }
                    }
                if (params != null) break
            }
        }
    }.size()

    data class Rock(val width: Int, val height: Int, val content: String) {
        fun isRock(x: Int, y: Int) = x in 0 until width && y in 0 until height && content[y * width + x] == '#'
    }

    data class Params(val rockStep: Int, val sizeStep: Int)

    private var params: Params? = null
    private val commands = input

    class Board {
        val rockContent = listOf(
            Rock(4, 1, "####"),
            Rock(3, 3, ".#.###.#."),
            Rock(3, 3, "###..#..#"),
            Rock(1, 4, "####"),
            Rock(2, 2, "####"),
        )

        var commandIndex = 0
        var rockIndex = 0
            set(value) {
                field = value
                rock = rockContent[rockIndex % rockContent.size]
            }
        var rockX = 2
        var rockY = 3

        var rock = rockContent[0]
            private set

        data class Cell(var content: Char)

        private val columns = MutableList(7) { mutableMapOf<Int, Cell>() }

        override fun toString() = size().let { size ->
            (0 until size).joinToString("\n") { y ->
                "|" + (0..6).joinToString("") { x -> get(x, size - y - 1).content.toString() } + "|"
            } + "\n+-------+"
        }

        fun toString(rock: Rock, bottomX: Int, bottomY: Int) = max(size(), bottomY + rock.height).let { size ->
            (0 until size).joinToString("\n") {
                val y = size - it - 1
                "|" + (0..6).joinToString("") { x ->
                    if (rock.isRock(x - bottomX, y - bottomY)) "@" else get(
                        x,
                        y
                    ).content.toString()
                } + "|"
            } + "\n+-------+"
        }

        fun size() =
            columns.maxOf { column -> column.filter { it.value.content == '#' }.maxOfOrNull { it.key + 1 } ?: 0 }

        fun isValid(rock: Rock, bottomX: Int, bottomY: Int) =
            isValid(bottomX, bottomY) && isValid(bottomX + rock.width - 1, bottomY + rock.height - 1) &&
                    (0 until rock.width).all { x ->
                        (0 until rock.height).all { y ->
                            !rock.isRock(x, y) || get(x + bottomX, y + bottomY).content != '#'
                        }
                    }

        operator fun get(x: Int, y: Int) = getOrNull(x, y) ?: throw Error("invalid cell : x=$x y=$y")

        fun clean() {
            val min = columns.minOf { column ->
                column.filter { it.value.content == '#' }.maxOfOrNull { it.key } ?: 0
            } - 100 // 0,1 cause issues, 2 is OK, 100 is large
            columns.indices.forEach { x -> columns[x] = columns[x].filterKeys { it >= min }.toMutableMap() }
        }

        private fun isValid(x: Int, y: Int) = x in 0 until 7 && y >= 0
        private fun getOrNull(x: Int, y: Int) = if (isValid(x, y)) columns[x].getOrPut(y) { Cell('.') } else null
    }
}
