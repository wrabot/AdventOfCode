package aoc2015

import Day
import java.util.*

class Day6 : Day(2015, 6) {
    override fun solvePart1(): Any {
        val binaryLights = BitSet(size * size)
        orders.forEach {
            for (i in it.xRange) {
                for (j in it.yRange) {
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

    override fun solvePart2(): Any {
        val lights = Array(size * size) { 0 }
        orders.forEach {
            for (i in it.xRange) {
                for (j in it.yRange) {
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

    data class LightOrder(val type: Type, val xRange: IntRange, val yRange: IntRange) {
        @Suppress("EnumEntryName")
        enum class Type { toggle, on, off }
    }

    private val size = 1000
    private val orders = lines.map {
        val (type, top, left, bottom, right) = it.removePrefix("turn ").split(" through ", " ", ",")
        LightOrder(LightOrder.Type.valueOf(type), left.toInt()..right.toInt(), top.toInt()..bottom.toInt())
    }
}
