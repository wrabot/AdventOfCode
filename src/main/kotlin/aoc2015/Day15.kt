package aoc2015

import tools.Day

class Day15(test: Int? = null) : Day(2015, 15, test) {
    override fun solvePart1() = max(0, 0, ::propertiesScore)
    override fun solvePart2() = max(0, 0) {
        if (ingredients.sumOf { it.calories * it.quantity } == 500) propertiesScore() else 0
    }

    private fun max(index: Int, quantity: Int, score: () -> Int): Int = if (index < ingredients.size) {
        (0..100 - quantity).maxOf {
            ingredients[index].quantity = it
            max(index + 1, quantity + it, score)
        }
    } else {
        score()
    }

    data class Ingredient(val name: String, val properties: List<Int>, val calories: Int) {
        var quantity = 0
        override fun toString() = "$name $quantity"
    }

    private val regex = "(.+): capacity (.+), durability (.+), flavor (.+), texture (.+), calories (.+)".toRegex()
    private val ingredients = lines.map { line ->
        regex.matchEntire(line)!!.groupValues.run {
            Ingredient(get(1), subList(2, 6).map { it.toInt() }, get(6).toInt())
        }
    }

    private fun propertiesScore() = (0..3).map { index ->
        ingredients.sumOf { it.properties[index] * it.quantity }.coerceAtLeast(0)
    }.reduce { acc, i -> acc * i }
}
