package aoc2017

import Day
import tools.geometry.toPoint
import tools.log
import tools.match

class Day20(test: Int? = null) : Day(test) {
    override fun solvePart1() = particles.withIndex().minBy { it.value.last().manhattan() }.index

    override fun solvePart2(): Int {
        var i = particles.indices.toList()
        val collide = particles.map { false }.toTypedArray()
        val p = particles.map { it[0] }.toTypedArray()
        val v = particles.map { it[1] }.toTypedArray()
        val a = particles.map { it[2] }
        repeat(100) { _ ->
            i = i.filterNot { collide[it] }
            i.forEach { 
                v[it] += a[it]
                p[it] += v[it]
            }
            i.forEachIndexed { index, i1 ->
                i.drop(index + 1).forEach { i2 ->
                    if (p[i1] == p[i2]) {
                        collide[i1] = true
                        collide[i2] = true                        
                    }
                }
            }
        }
        return collide.count { !it }
    }

    private val regex = "p=<(.*)>, v=<(.*)>, a=<(.*)>".toRegex()
    private val particles = lines.map { line -> line.match(regex)!!.map { it.toPoint(",") } }
}
