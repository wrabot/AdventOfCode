package aoc2021

import forEachInput
import log

object Day16 {
    fun solve() = forEachInput(2021, 16, 3) { lines ->
        lines.map {
            val bits = it.map { digit ->
                digit.toString().toInt(16).toString(2).padStart(4, '0')
            }.joinToString("")
            val packet = parsePacket(bits).packet
            log("part 1: ")
            packet.sumVersions().log()
            log("part 2: ")
            packet.evaluate().log()
        }
    }

    open class Packet
    data class Literal(val version: Int, val value: Long) : Packet()
    data class Operator(val version: Int, val type: Int, val packets: List<Packet>) : Packet()
    data class ParseResult(val packet: Packet, val offset: Int)

    private fun Packet.sumVersions(): Int = when (this) {
        is Literal -> version
        is Operator -> version + packets.sumOf { it.sumVersions() }
        else -> 0
    }

    private fun Packet.evaluate(): Long = when (this) {
        is Literal -> value
        is Operator -> when (type) {
            0 -> packets.sumOf { it.evaluate() }
            1 -> packets.fold(1L) { acc, value -> acc * value.evaluate() }
            2 -> packets.minOf { it.evaluate() }
            3 -> packets.maxOf { it.evaluate() }
            5 -> if (packets[0].evaluate() > packets[1].evaluate()) 1 else 0
            6 -> if (packets[0].evaluate() < packets[1].evaluate()) 1 else 0
            7 -> if (packets[0].evaluate() == packets[1].evaluate()) 1 else 0
            else -> throw Error("invalid operator type")
        }
        else -> throw Error("invalid packet type")
    }

    private fun parsePacket(input: String): ParseResult {
        val version = input.substring(0, 3).toInt(2)
        val type = input.substring(3, 6).toInt(2)
        val packetPayload = input.substring(6)
        var offset = 6
        return when (type) {
            4 -> {
                val blocks = packetPayload.chunked(5)
                val count = blocks.indexOfFirst { it.startsWith('0') } + 1
                val value = blocks.take(count).joinToString("") { it.drop(1) }.toLong(2)
                ParseResult(Literal(version, value), offset + count * 5)
            }
            else -> {
                val packets = mutableListOf<Packet>()
                if (packetPayload[0] == '0') {
                    val length = packetPayload.substring(1, 16).toInt(2)
                    offset += 16
                    var operatorPayload = packetPayload.substring(16, 16 + length)
                    offset += length
                    while (operatorPayload.isNotEmpty()) {
                        val result = parsePacket(operatorPayload)
                        packets.add(result.packet)
                        operatorPayload = operatorPayload.substring(result.offset)
                    }
                } else {
                    val count = packetPayload.substring(1, 12).toInt(2)
                    offset += 12
                    var operatorPayload = packetPayload.substring(12)
                    repeat(count) {
                        val result = parsePacket(operatorPayload)
                        packets.add(result.packet)
                        operatorPayload = operatorPayload.substring(result.offset)
                        offset += result.offset
                    }
                }
                ParseResult(Operator(version, type, packets), offset)
            }
        }
    }
}
