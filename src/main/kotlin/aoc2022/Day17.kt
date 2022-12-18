package aoc2022

import tools.Day
import kotlin.math.max

class Day17(test: Int? = null) : Day(2022, 17, test) {
    override fun solvePart1() = solve(2022)
    override fun solvePart2() = (((1000000000000 - params[0]) / params[1]) - 1) * params[2] + solve((params[0] + params[1] + ((1000000000000 - params[0]) % params[1])).toInt())

    fun solve(rocks: Int) = Board().apply {
        var commandIndex = 0
        var spriteIndex = 0
        var spriteX = 2
        var spriteY = 3
        var sprite = sprites[spriteIndex]
        while (spriteIndex < rocks) {
            when (commands[commandIndex++]) {
                '<' -> if (isValid(sprite, spriteX - 1, spriteY)) spriteX--
                '>' -> if (isValid(sprite, spriteX + 1, spriteY)) spriteX++
                else -> error("!!!")
            }
            if (commandIndex == commands.length) commandIndex = 0

            if (isValid(sprite, spriteX, spriteY - 1)) {
                spriteY--
            } else {
                for (x in 0 until sprite.width) {
                    for (y in 0 until sprite.height) {
                        if (sprite.isRock(x, y)) get(x + spriteX, y + spriteY).content = '#'
                    }
                }

                // use to find params TODO find params programmatically
                log("$commandIndex ${spriteIndex % sprites.size} ${size()}")
                if (commandIndex == 0) log("$spriteIndex ${spriteIndex % sprites.size} ${size()} ${(spriteIndex - params[0]) / params[1] * params[2] + params[3]}")
                if (commandIndex == 1) log("$spriteIndex ${spriteIndex % sprites.size} ${size()} ${(spriteIndex - params[0]) / params[1] * params[2] + params[3]}")

                // reduce time
                clean()

                spriteIndex++
                sprite = sprites[spriteIndex % sprites.size]
                spriteX = 2
                spriteY = size() + 3
            }
        }
    }.size()

    data class Sprite(val width: Int, val height: Int, val content: String) {
        fun isRock(x: Int, y: Int) = x in 0 until width && y in 0 until height && content[y * width + x] == '#'
    }

    private val sprites = listOf(
        Sprite(4, 1, "####"),
        Sprite(3, 3, ".#.###.#."),
        Sprite(3, 3, "###..#..#"),
        Sprite(1, 4, "####"),
        Sprite(2, 2, "####"),
    )

    private val params = lines[0].split(" ").map { it.toInt() }
    private val commands = lines[1]

    class Board {
        data class Cell(var content: Char)

        private val columns = MutableList(7) { mutableMapOf<Int, Cell>() }

        override fun toString() = size().let { size ->
            (0 until size).joinToString("\n") { y ->
                "|" + (0..6).joinToString("") { x -> get(x, size - y - 1).content.toString() } + "|"
            } + "\n+-------+"
        }

        fun toString(sprite: Sprite, bottomX: Int, bottomY: Int) = max(size(), bottomY + sprite.height).let { size ->
            (0 until size).joinToString("\n") {
                val y = size - it - 1
                "|" + (0..6).joinToString("") { x -> if (sprite.isRock(x - bottomX, y - bottomY)) "@" else get(x, y).content.toString() } + "|"
            } + "\n+-------+"
        }

        fun size() = columns.maxOf { column -> column.filter { it.value.content == '#' }.maxOfOrNull { it.key + 1 } ?: 0 }
        fun isValid(sprite: Sprite, bottomX: Int, bottomY: Int) = isValid(bottomX, bottomY) && isValid(bottomX + sprite.width - 1, bottomY + sprite.height - 1) &&
                (0 until sprite.width).all { x ->
                    (0 until sprite.height).all { y ->
                        !sprite.isRock(x, y) || get(x + bottomX, y + bottomY).content != '#'
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
