package aoc2015

import tools.Day
import java.util.*

class Day6 : Day(2015, 6) {
    override fun getPart1(): Any {
        val binaryLights = BitSet(size * size)
        orders.forEach {
            for (i in it.tl.first..it.br.first) {
                for (j in it.tl.second..it.br.second) {
                    val index = i * size + j
                    when (it.type) {
                        LightOrder.Type.toggle -> binaryLights.flip(index)
                        LightOrder.Type.on -> binaryLights.set(index)
                        LightOrder.Type.off -> binaryLights.clear(index)
                    }
                }
            }
        }
        return binaryLights.cardinality()
    }

    override fun getPart2(): Any {
        val lights = Array(size * size) { 0 }
        orders.forEach {
            for (i in it.tl.first..it.br.first) {
                for (j in it.tl.second..it.br.second) {
                    val index = i * size + j
                    when (it.type) {
                        LightOrder.Type.toggle -> lights[index] += 2
                        LightOrder.Type.on -> lights[index]++
                        LightOrder.Type.off -> if (lights[index] > 0) lights[index]--
                    }
                }
            }
        }
        return lights.sum()
    }

    data class LightOrder(val type: Type, val tl: Pair<Int, Int>, val br: Pair<Int, Int>) {
        @Suppress("EnumEntryName")
        enum class Type { toggle, on, off }
    }

    private val size = 1000
    private val orders = lines.map { it.removePrefix("turn ").split(" ") }.map { (type, tl, _, br) ->
        LightOrder(LightOrder.Type.valueOf(type), tl.toIntPair(), br.toIntPair())
    }

    private fun String.toIntPair() = split(",").map { it.toInt() }.zipWithNext().first()
}
