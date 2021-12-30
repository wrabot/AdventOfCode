package aoc2020

import tools.Day

class Day14(test: Int? = null) : Day(2020, 14, test) {
    override fun solvePart1() : Any {
        var maskOff = 0L
        var maskOn = 0L
        val mem = mutableMapOf<Long, Long>()
        lines.forEach { line ->
            when {
                line.startsWith("mask") -> {
                    val mask = line.split(" = ")[1]
                    maskOff = mask.replace('X', '1').toLong(2)
                    maskOn = mask.replace('X', '0').toLong(2)
                }
                line.startsWith("mem") -> {
                    val (address, value) = line.removePrefix("mem[").split("] = ").map { it.toLong() }
                    mem[address] = ((value and maskOff) or maskOn)
                }
            }
        }
        return mem.values.sum()
    }

    override fun solvePart2() : Any {
        var maskOff = 0L
        val maskOn = mutableListOf<Long>()
        val mem = mutableMapOf<Long, Long>()
        lines.forEach { line ->
            when {
                line.startsWith("mask") -> {
                    val mask = line.split(" = ")[1]
                    maskOff = mask.replace('0', '1').replace('X', '0').toLong(2)
                    maskOn.clear()
                    maskOn.add(mask.replace('X', '0').toLong(2))
                    mask.forEachIndexed { index, c ->
                        if (c == 'X') {
                            maskOn.addAll(maskOn.map { it or (1L shl (mask.length - 1 - index)) })
                        }
                    }
                }
                line.startsWith("mem") -> {
                    val (address, value) = line.removePrefix("mem[").split("] = ").map { it.toLong() }
                    maskOn.forEach { mem[(address and maskOff) or it] = value }
                }
            }
        }
        return mem.values.sum()
    }
}
