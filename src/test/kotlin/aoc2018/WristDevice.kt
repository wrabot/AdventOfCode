package aoc2018

class WristDevice(input: String) {
    fun run(interrupt: (IntArray) -> Boolean = { false }): Int {
        val registers = IntArray(6)
        while (registers[pointer] < program.size) {
            if (interrupt(registers)) break
            registers.execute(program[registers[pointer]])
            registers[pointer]++
        }
        return registers[0]
    }

    fun list() {
        program.forEachIndexed { index, instruction -> println("$index $instruction") }
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

    private val pointer = input.lines().first().split(" ").last().toInt()
    private val program = input.lines().drop(1).map { line ->
        val (opcode, a, b, c) = line.split(" ")
        Instruction(Opcode.valueOf(opcode), a.toInt(), b.toInt(), c.toInt())
    }
}
