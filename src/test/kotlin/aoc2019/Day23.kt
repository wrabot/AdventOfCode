package aoc2019

import Day

class Day23(test: Int? = null) : Day(test) {
    override fun solvePart1(): Long {
        val network = Array(50) { Computer(it, code) }
        while (true) {
            for (computer in network) {
                val packet = computer.next() ?: continue
                if (packet.address == 255) return packet.y
                network[packet.address].add(packet)
            }
        }
    }

    override fun solvePart2(): Long {
        var nat: Packet? = null
        var lastY: Long? = null
        val network = Array(50) { Computer(it, code) }
        while (true) {
            for (computer in network) {
                val packet = computer.next() ?: continue
                if (packet.address == 255) nat = packet else network[packet.address].add(packet)
            }
            if (nat != null && network.all { it.isIdle() }) {
                if (lastY == nat.y) return nat.y
                network[0].add(nat)
                lastY = nat.y
            }
        }
    }

    private data class Packet(val address: Int, val x: Long, val y: Long)

    private class Computer(address: Int, code: List<Long>) {
        private val runtime = Runtime(code).apply { input.add(address.toLong()) }
        private val output = mutableListOf<Long>()

        fun isIdle() = runtime.isIdle && runtime.input.isEmpty()

        fun next(): Packet? {
            runtime.execute()?.let { output.add(it) }
            return with(output) { if (size < 3) null else Packet(removeFirst().toInt(), removeFirst(), removeFirst()) }
        }

        fun add(packet: Packet) {
            runtime.input.add(packet.x)
            runtime.input.add(packet.y)
        }
    }

    // modify Day9 runtime with 
    // - one step execute with input list 
    // - default input is -1
    // - idle is when default input is used
    private class Runtime(code: List<Long>) {
        private var offset: Long = 0
        private var relative: Long = 0
        val input = mutableListOf<Long>()

        var isIdle = false
            private set

        fun execute(): Long? {
            val opcode = read().toInt()
            val firstMode = opcode / 100 % 10
            val secondMode = opcode / 1000 % 10
            val thirdMode = opcode / 10000 % 10
            when (opcode % 100) {
                1 -> read3().run {
                    set(
                        third.toAddress(thirdMode),
                        first.toValue(firstMode) + second.toValue(secondMode)
                    )
                }

                2 -> read3().run {
                    set(
                        third.toAddress(thirdMode),
                        first.toValue(firstMode) * second.toValue(secondMode)
                    )
                }

                3 -> set(read().toAddress(firstMode), input.removeFirstOrNull().apply { isIdle = this == null } ?: -1)
                4 -> return read().toValue(firstMode)
                5 -> read2().run { if (first.toValue(firstMode) != 0L) offset = second.toValue(secondMode) }
                6 -> read2().run { if (first.toValue(firstMode) == 0L) offset = second.toValue(secondMode) }
                7 -> read3().run {
                    set(
                        third.toAddress(thirdMode),
                        if (first.toValue(firstMode) < second.toValue(secondMode)) 1 else 0
                    )
                }

                8 -> read3().run {
                    set(
                        third.toAddress(thirdMode),
                        if (first.toValue(firstMode) == second.toValue(secondMode)) 1 else 0
                    )
                }

                9 -> relative += read().toValue(firstMode)

                99 -> offset-- // stay on instruction
                else -> error("invalid opcode $opcode")
            }
            return null
        }

        private fun read() = get(offset++)
        private fun read2() = Pair(read(), read())
        private fun read3() = Triple(read(), read(), read())
        private fun Long.toValue(mode: Int) = when (mode) {
            0 -> get(this)
            1 -> this
            2 -> get(relative + this)
            else -> error("invalid mode $mode")
        }

        private fun Long.toAddress(mode: Int) = when (mode) {
            0 -> this
            2 -> relative + this
            else -> error("invalid mode $mode")
        }

        private var memory = mutableMapOf<Long, Long>().apply {
            code.forEachIndexed { index, value -> set(index.toLong(), value) }
        }

        private fun get(index: Long) = memory.getOrDefault(index, 0)
        private fun set(index: Long, value: Long) {
            memory[index] = value
        }
    }


    val code = lines.first().split(",").map { it.toLong() }
}
