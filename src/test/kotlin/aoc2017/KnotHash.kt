package aoc2017

import java.util.*


fun knotHash(input: String) = MutableList(256) { it }.apply {
    knotHashRound(input.toList().map { it.code } + listOf(17, 31, 73, 47, 23), 64)
}.chunked(16).joinToString("") { it.reduce(Int::xor).toString(16).padStart(2, '0') }

fun MutableList<Int>.knotHashRound(lengths: List<Int>, round: Int) {
    var currentPosition = 0
    var skipSize = 0
    repeat(round) {
        lengths.forEach {
            subList(0, it).reverse()
            val move = it + skipSize
            Collections.rotate(this, -move)
            currentPosition += move
            skipSize++
        }
    }
    Collections.rotate(this, currentPosition)
}
