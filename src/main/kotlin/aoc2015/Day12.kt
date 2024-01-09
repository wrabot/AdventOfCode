package aoc2015

import kotlinx.serialization.json.*
import Day

class Day12(test: Int? = null) : Day(test) {
    override fun solvePart1() = Json.parseToJsonElement(input).sum(false)
    override fun solvePart2() = Json.parseToJsonElement(input).sum(true)

    fun JsonElement.sum(rejectRed: Boolean): Int = when (this) {
        is JsonPrimitive -> if (isString) 0 else content.toIntOrNull() ?: 0
        is JsonArray -> sumOf { it.sum(rejectRed) }
        is JsonObject ->
            if (rejectRed && values.any { it is JsonPrimitive && it.content == "red" })
                0
            else
                values.sumOf { it.sum(rejectRed) }
    }
}
