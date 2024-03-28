package aoc2023

import Day

class Day2(test: Int? = null) : Day(test) {
    override fun solvePart1() = games.filter { game ->
        game.subsets.all { it.red <= 12 } && game.subsets.all { it.green <= 13 } && game.subsets.all { it.blue <= 14 }
    }.sumOf { it.id }

    override fun solvePart2() = games.sumOf { game ->
        game.subsets.maxOf { it.red } * game.subsets.maxOf { it.green } * game.subsets.maxOf { it.blue }
    }

    // data

    private val games = lines.mapIndexed { id, line ->
        Game(id + 1, line.split(":")[1].split(";").map { subset ->
            var red = 0
            var green = 0
            var blue = 0
            subset.split(",").forEach { group ->
                red += group.getColor("red")
                green += group.getColor("green")
                blue += group.getColor("blue")
            }
            Subset(red, green, blue)
        })
    }

    private data class Game(val id: Int, val subsets: List<Subset>)
    private data class Subset(val red: Int, val green: Int, val blue: Int)

    private fun String.getColor(color: String) = if (endsWith(color)) removeSuffix(color).trim().toInt() else 0
}
