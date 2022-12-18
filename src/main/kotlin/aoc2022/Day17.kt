package aoc2022

import tools.Day
import tools.Point
import kotlin.math.max

class Day17(test: Int? = null) : Day(2022, 17, test) {
    override fun solvePart1() = solve(2022)
    override fun solvePart2(): Long {
        solve(0)
        val (rockOffset, rockStep, sizeStep) = params!!
        val rocks = 1000000000000 - rockOffset
        return (rocks / rockStep - 1) * sizeStep + solve((rockOffset + rockStep + (rocks % rockStep)).toInt())
    }

    fun solve(rocks: Int) = Board().apply {
        var commandIndex = 0
        var rockIndex = 0
        var rockX = 2
        var rockY = 3
        var rock = rockContent[rockIndex]
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

                // use to find params TODO find params programmatically
                if (findParams) patterns.getOrPut((rockIndex % rockContent.size) to commandIndex) { mutableListOf() }.add(Point(rockIndex, size()))

                // reduce time
                clean()

                rockIndex++
                rock = rockContent[rockIndex % rockContent.size]
                rockX = 2
                rockY = size() + 3
            }
            if (findParams) {
                params = patterns.values.filter { it.size >= 3 }
                    .firstNotNullOfOrNull { list -> list.zipWithNext().map { it.second - it.first }.distinct().singleOrNull()?.let { Triple(list.first().x, it.x, it.y) } }
                if (params != null) break
            }
        }
    }.size()

    data class rock(val width: Int, val height: Int, val content: String) {
        fun isRock(x: Int, y: Int) = x in 0 until width && y in 0 until height && content[y * width + x] == '#'
    }

    private val rockContent = listOf(
        rock(4, 1, "####"),
        rock(3, 3, ".#.###.#."),
        rock(3, 3, "###..#..#"),
        rock(1, 4, "####"),
        rock(2, 2, "####"),
    )

    private var params: Triple<Int, Int, Int>? = null
    private val commands = input

    class Board {
        data class Cell(var content: Char)

        private val columns = MutableList(7) { mutableMapOf<Int, Cell>() }

        override fun toString() = size().let { size ->
            (0 until size).joinToString("\n") { y ->
                "|" + (0..6).joinToString("") { x -> get(x, size - y - 1).content.toString() } + "|"
            } + "\n+-------+"
        }

        fun toString(rock: rock, bottomX: Int, bottomY: Int) = max(size(), bottomY + rock.height).let { size ->
            (0 until size).joinToString("\n") {
                val y = size - it - 1
                "|" + (0..6).joinToString("") { x -> if (rock.isRock(x - bottomX, y - bottomY)) "@" else get(x, y).content.toString() } + "|"
            } + "\n+-------+"
        }

        fun size() = columns.maxOf { column -> column.filter { it.value.content == '#' }.maxOfOrNull { it.key + 1 } ?: 0 }
        fun isValid(rock: rock, bottomX: Int, bottomY: Int) = isValid(bottomX, bottomY) && isValid(bottomX + rock.width - 1, bottomY + rock.height - 1) &&
                (0 until rock.width).all { x ->
                    (0 until rock.height).all { y ->
                        !rock.isRock(x, y) || get(x + bottomX, y + bottomY).content != '#'
                    }
                }

        operator fun get(x: Int, y: Int) = getOrNull(x, y) ?: throw Error("invalid cell : x=$x y=$y")

        fun clean() {
            val min = columns.minOf { column -> column.filter { it.value.content == '#' }.maxOfOrNull { it.key } ?: 0 } - 100 // 0,1 cause issues, 2 is OK, 100 is large
            columns.indices.forEach { x -> columns[x] = columns[x].filterKeys { it >= min }.toMutableMap() }
        }

        private fun isValid(x: Int, y: Int) = x in 0 until 7 && y >= 0
        private fun getOrNull(x: Int, y: Int) = if (isValid(x, y)) columns[x].getOrPut(y) { Cell('.') } else null
    }
}
