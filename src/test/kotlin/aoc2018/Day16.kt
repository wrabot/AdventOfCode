package aoc2018

import Day

class Day16(test: Int? = null) : Day(test) {
    override fun solvePart1() = samples.count { sample ->
        Opcode.entries.count {
            sample.before.toIntArray().run {
                execute(it, sample.instruction.drop(1))
                toList() == sample.after
            }
        } >= 3
    }

    override fun solvePart2(): Int {
        val opcodesFromSamples = List(Opcode.entries.size) { Opcode.entries.toMutableSet() }
        samples.forEach { sample ->
            opcodesFromSamples[sample.instruction[0]].removeAll(Opcode.entries.filter {
                sample.before.toIntArray().run {
                    execute(it, sample.instruction[1], sample.instruction[2], sample.instruction[3])
                    toList() != sample.after
                }
            }.toSet())
        }
        val todo = opcodesFromSamples.filter { it.size == 1 }.flatten().toMutableList()
        while (true) {
            val current = todo.removeLastOrNull() ?: break
            opcodesFromSamples.filter { it.size != 1 }.forEach {
                if (it.remove(current) && it.size == 1) todo.add(it.first())
            }
        }
        val opcodes = opcodesFromSamples.map { it.single() }
        val registers = IntArray(4)
        program.forEach { registers.execute(opcodes[it[0]], it.drop(1)) }
        return registers[0]
    }

    private enum class Opcode { addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr }

    private fun IntArray.execute(opcode: Opcode, params: List<Int>) = execute(opcode, params[0], params[1], params[2])

    private fun IntArray.execute(opcode: Opcode, a: Int, b: Int, c: Int) = set(
        c,
        when (opcode) {
            Opcode.addr -> get(a) + get(b)
            Opcode.addi -> get(a) + b
            Opcode.mulr -> get(a) * get(b)
            Opcode.muli -> get(a) * b
            Opcode.banr -> get(a) and get(b)
            Opcode.bani -> get(a) and b
            Opcode.borr -> get(a) or get(b)
            Opcode.bori -> get(a) or b
            Opcode.setr -> get(a)
            Opcode.seti -> a
            Opcode.gtir -> if (a > get(b)) 1 else 0
            Opcode.gtri -> if (get(a) > b) 1 else 0
            Opcode.gtrr -> if (get(a) > get(b)) 1 else 0
            Opcode.eqir -> if (a == get(b)) 1 else 0
            Opcode.eqri -> if (get(a) == b) 1 else 0
            Opcode.eqrr -> if (get(a) == get(b)) 1 else 0
        }
    )

    private data class Sample(
        val before: List<Int>,
        val instruction: List<Int>,
        val after: List<Int>
    )

    private val samples = input.split("\n\n\n\n").first().split("\n\n").map {
        val lines = it.lines()
        Sample(
            lines[0].drop(9).dropLast(1).split(", ").map { it.toInt() },
            lines[1].split(" ").map { it.toInt() },
            lines[2].drop(9).dropLast(1).split(", ").map { it.toInt() },
        )
    }

    private val program = input.split("\n\n\n\n").last().lines().map { line -> line.split(" ").map { it.toInt() } }
}
