package aoc2018

import Day

class Day19(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val registers = IntArray(6)
        while (registers[pointer] < program.size) {
            registers.execute(program[registers[pointer]])
            registers[pointer]++
        }
        return registers[0]
    }

    override fun solvePart2(): Int {
        // display program more clearly
        //program.forEachIndexed { index, instruction -> println("$index $instruction") }
        // reverse for a = 1
        /*
        a = 0
        b = 10551383
        c = 1
        do {
            e = 1
            do {
                if (c * e == b) a += c
                e++
            } while (e <= b)
            c++
        } while (c <= b)
         */
        // => sum of dividers of b
        // if (a == 0) b=983 if (a==1) b=10551383
        val b = 10551383
        return (1..b).sumOf { if (b % it == 0) it else 0 }
    }

    private fun IntArray.execute(instruction: Instruction) = with(instruction) {
        set(
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
    }

    @Suppress("EnumEntryName", "SpellCheckingInspection")
    private enum class Opcode { addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr }
    private data class Instruction(val opcode: Opcode, val a: Int, val b: Int, val c: Int) {
        override fun toString() = c.r + "=" + when (opcode) {
            Opcode.addr -> a.r + "+" + b.r
            Opcode.addi -> a.r + "+" + b
            Opcode.mulr -> a.r + "*" + b.r
            Opcode.muli -> a.r + "*" + b
            Opcode.banr -> a.r + "&&" + b.r
            Opcode.bani -> a.r + "&&" + b
            Opcode.borr -> a.r + "||" + b.r
            Opcode.bori -> a.r + "||" + b
            Opcode.setr -> a.r
            Opcode.seti -> a
            Opcode.gtir -> "if($a>${b.r}) 1 else 0"
            Opcode.gtri -> "if(${a.r}>$b}) 1 else 0"
            Opcode.gtrr -> "if(${a.r}>${b.r}) 1 else 0"
            Opcode.eqir -> "if($a==${b.r}) 1 else 0"
            Opcode.eqri -> "if(${a.r}==$b) 1 else 0"
            Opcode.eqrr -> "if(${a.r}==${b.r}) 1 else 0"
        }

        val Int.r get() = 'a' + this
    }

    private val pointer = lines.first().split(" ").last().toInt()
    private val program = lines.drop(1).map { line ->
        val (opcode, a, b, c) = line.split(" ")
        Instruction(Opcode.valueOf(opcode), a.toInt(), b.toInt(), c.toInt())
    }
}
