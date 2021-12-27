package aoc2015

import forEachInput
import tools.log
import java.util.*

object Day6 {
    fun solve() = forEachInput(2015, 6, 1) { lines ->
        val orders = lines.map { it.removePrefix("turn ").split(" ") }.map { (type, tl, _, br) ->
            LightOrder(LightOrder.Type.valueOf(type), tl.toIntPair(), br.toIntPair())
        }
        val size = 1000

        log("part 1: ")
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
        binaryLights.cardinality().log()

        log("part 2: ")
        val lights = Array<Int>(size * size) { 0 }
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
        lights.sum().log()
    }

    data class LightOrder(val type: Type, val tl: Pair<Int, Int>, val br: Pair<Int, Int>) {
        enum class Type { toggle, on, off }
    }

    private fun String.toIntPair() = split(",").map { it.toInt() }.zipWithNext().first()
}
