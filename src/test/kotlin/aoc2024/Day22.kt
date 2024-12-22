package aoc2024

import Day

class Day22(test: Int? = null) : Day(test) {
    private val allSecrets = lines.map { generateSequence(it.toLong(), ::nextSecret).take(2001).toList() }
    private fun nextSecret(secret: Long) = (secret xor (secret * 64) % 16777216L)
        .let { it xor (it / 32) % 16777216L }
        .let { it xor (it * 2048) % 16777216L }

    override fun solvePart1() = allSecrets.sumOf { it[2000] }

    override fun solvePart2(): Int {
        val gains = mutableMapOf<List<Int>, Int>()
        for (secrets in allSecrets) {
            val done = mutableSetOf<List<Int>>()
            val prices = secrets.map { (it % 10).toInt() }
            prices.zipWithNext { a, b -> b - a }.windowed(4).forEachIndexed { index, sequence ->
                if (done.add(sequence)) gains[sequence] = gains.getOrDefault(sequence, 0) + prices[index + 4]
            }
        }
        return gains.maxOf { it.value }
    }
}
