package aoc2020

import forEachInput
import tools.log

object Day21 {
    fun solve() = forEachInput(2020, 21, 2) { lines ->
        log("part 1: ")
        val foods = lines.map { it.removeSuffix(")").split(" (contains ") }.map { (ingredients, allergens) ->
            ingredients.split(" ") to allergens.split(", ")
        }
        val allergens = foods.flatMap { it.second }.distinct()
        val allergensMap = allergens.map { allergen ->
            allergen to foods.filter { allergen in it.second }.map { it.first }.reduce { acc, list -> (acc intersect list).toList() }.toMutableList()
        }.toMap()
        while (true) {
            var modified = false
            allergensMap.values.mapNotNull { it.singleOrNull() }.forEach { found ->
                allergensMap.forEach { if (it.value.size != 1) modified = modified || it.value.remove(found) }
            }
            if (!modified) break
        }
        val foodWithAllergens = allergensMap.toSortedMap().flatMap { it.value } // sorted for part2

        log("part 1: ")
        foods.map { it.first.minus(foodWithAllergens).size }.sum().log()

        log("part 2: ")
        foodWithAllergens.joinToString(",").log()
    }
}
