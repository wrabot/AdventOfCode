package aoc2022

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import Day

class Day13(test: Int? = null) : Day(2022, 13, test) {
    override fun solvePart1() =
        packets.mapIndexedNotNull { index, pair -> if (compare(pair[0], pair[1]) >= 0) index + 1 else null }.sum()

    override fun solvePart2() =
        (packets.flatten() + divider1 + divider2).sortedWith { left, right -> -compare(left, right) }
            .run { packetIndex(divider1) * packetIndex(divider2) }

    private val packets = input.split("\n\n").map { block ->
        block.split("\n").map { Json.Default.parseToJsonElement(it) }
    }

    private val divider1 = Json.Default.parseToJsonElement("[[2]]")
    private val divider2 = Json.Default.parseToJsonElement("[[6]]")

    private fun List<Any>.packetIndex(packet: Any) = indexOfFirst { compare(it, packet) == 0 } + 1

    private fun compare(left: Any, right: Any): Int = when {
        left is JsonPrimitive && right is JsonPrimitive -> right.int - left.int
        left is JsonArray && right is JsonPrimitive -> compare(left, JsonArray(listOf(right)))
        left is JsonPrimitive && right is JsonArray -> compare(JsonArray(listOf(left)), right)
        left is JsonArray && right is JsonArray -> left.zip(right).map { compare(it.first, it.second) }
            .dropWhile { it == 0 }.run {
                firstOrNull() ?: (right.size - left.size)
            }
        else -> error("!!!!")
    }
}
