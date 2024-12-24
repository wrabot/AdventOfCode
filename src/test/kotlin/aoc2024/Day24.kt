package aoc2024

import Day
import tools.debug

class Day24(test: Int? = null) : Day(test) {
    private val parts = input.split("\n\n")
    private val inputs = parts[0].lines().associate {
        val (i, v) = it.split(": ")
        i to (v == "1")
    }
    private val gates = parts[1].lines().associate {
        val (f, o) = it.split(" -> ")
        val (a, g, b) = f.split(" ")
        o to f.split(" ").let { Pair(Gate.valueOf(g), listOf(a, b).sorted()) }
    }.toMutableMap()
    private val outputs = gates.keys.filter { it[0] == 'z' }.sorted()

    private enum class Gate { AND, OR, XOR }

    private val compute = mutableMapOf<String, Boolean>()
    private fun compute(output: String): Boolean =
        inputs[output] ?: compute.getOrPut(output) {
            gates[output]!!.run {
                when (first) {
                    Gate.AND -> compute(second[0]) and compute(second[1])
                    Gate.OR -> compute(second[0]) or compute(second[1])
                    Gate.XOR -> compute(second[0]) xor compute(second[1])
                }
            }
        }

    private fun List<Boolean>.toLong() = foldRight(0L) { v, acc -> acc * 2 + if (v) 1 else 0 }

    private val z = outputs.map { compute(it) }.toLong()

    override fun solvePart1() = z

    override fun solvePart2(): String {
        /*
            y00 XOR x00 -> z00
            
            y01 XOR x01 -> npf
            y00 AND x00 -> wrd
            npf XOR wrd -> z01
            
            y02 XOR x02 -> dgk
            wrd AND npf -> prh
            y01 AND x01 -> tvb
            prh OR tvb -> nkn
            dgk XOR nkn -> z02
            
            y03 XOR x03 -> psp
            dgk AND nkn -> qwd
            x02 AND y02 -> dqj
            qwd OR dqj -> qjq
            psp XOR qjq -> z03
            ... the pattern is ...
            x(i) XOR y(i) -> s(i)
            x(i-1) AND y(i-1) -> t(i)
            s(i-1) AND v(i-1) -> u(i)
            t(i) OR u(i) -> v(i)
            s(i) XOR v(i) -> z(i)
            ...
            x(44) AND y(44) -> t(45)
            s(44) AND v(44) -> u(45)
            t(45) OR u(45) -> z(45)
            
            errors are (x+y) xor z = 100000111110000000011000110000000000 
            4 errors are blocs of 1 et are caused by computation of the most right 1 of a bloc
            
            NB: errors are in 10..35, no special cases from extremities to handles 
            NB: errors seems to be well separated : one in each block
         */
        
        val errors = inputs.toList().groupBy({ it.first[0] }, { it.second }).values.sumOf { it.toLong() } xor z
        var shift = errors.countTrailingZeroBits() // shift after a bloc of 0
        val exchanges = mutableListOf<String>()
        repeat(4) {
            val zi = gates["z$shift"]!!
            when (zi.first) {
                Gate.AND -> {
                    exchanges.add("z$shift")
                    val i = if (zi.second.first().startsWith('x')) {
                        (zi.second.first().drop(1).toInt() + 1).also { debug("exchange z$shift with t$it") }
                    } else {
                        val xor = zi.second.map { gates[it]!! }.first { it.first == Gate.XOR }
                        assert(xor.second.first().startsWith('x'))
                        (xor.second.first().drop(1).toInt() + 1).also { debug("exchange z$shift with u$it") }
                    }
                    exchanges.add(findTorU(i))
                }

                Gate.OR -> {
                    exchanges.add("z$shift")
                    val i = zi.second.map { gates[it]!!.second.first() }
                        .first { it.startsWith('x') }.drop(1).toInt() + 1
                    debug("exchange z$shift with v$i")
                    exchanges.add(findV(i))
                }

                Gate.XOR -> {
                    debug("$shift xor ? $zi")
                    zi.second.forEach { debug("$it or ? ${gates[it]}") }
                    // mmf (s or v) => (AND, [x35, y35]) find t(36)
                    exchanges.add("mmf")
                    exchanges.add(findTorU(36))
                }
            }
            exchanges.debug()

            shift += ((errors shr shift) + 1).countTrailingZeroBits() // shift after a bloc of 1
            shift += (errors shr shift).countTrailingZeroBits() // shift after a bloc of 0
        }

        return exchanges.sorted().joinToString(",")
    }

    private fun findTorU(i: Int) = gates["z$i"]!!.second.map { gates[it]!! }.first { it.first == Gate.OR }.second
        .first { gates[it]!!.first == Gate.XOR }
    
    private fun findV(i: Int) = gates["z$i"]!!.second.first { !gates[it]!!.second.first().startsWith('x') }
}
