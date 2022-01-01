package aoc2020

import tools.Day

class Day21(test: Int? = null) : Day(2020, 21, test) {
    override fun solvePart1() = foods.sumOf { it.first.minus(foodWithAllergens).size }
    override fun solvePart2() = foodWithAllergens.joinToString(",")

    private val foods = lines.map { it.removeSuffix(")").split(" (contains ") }.map { (ingredients, allergens) ->
        ingredients.split(" ") to allergens.split(", ")
    }
    private val allergens = foods.flatMap { it.second }.distinct()

    private val allergensMap = allergens.associateWith { allergen ->
        foods.filter { allergen in it.second }.map { it.first }
            .reduce { acc, list -> (acc intersect list).toList() }.toMutableList()
    }.apply {
        while (true) {
            var modified = false
            values.mapNotNull { it.singleOrNull() }.forEach { found ->
                forEach { if (it.value.size != 1) modified = modified || it.value.remove(found) }
            }
            if (!modified) break
        }
    }

    private val foodWithAllergens = allergensMap.toSortedMap().flatMap { it.value } // sorted for part2
}
