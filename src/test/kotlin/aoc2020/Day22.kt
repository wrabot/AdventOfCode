package aoc2020

import Day

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        val deck1 = deck1Init.toMutableList()
        val deck2 = deck2Init.toMutableList()
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            val card1 = deck1.removeAt(0)
            val card2 = deck2.removeAt(0)
            if (card1 > card2) {
                deck1 += card1
                deck1 += card2
            } else {
                deck2 += card2
                deck2 += card1
            }
        }
        return score(deck1, deck2).second
    }

    override fun solvePart2() = play(deck1Init.toMutableList(), deck2Init.toMutableList(), 1).second

    private fun play(deck1: MutableList<Int>, deck2: MutableList<Int>, level: Int): Pair<Boolean, Int> {
        //log("$level $deck1 $deck2")
        val deck1s = mutableSetOf<List<Int>>()
        val deck2s = mutableSetOf<List<Int>>()
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            if (!deck1s.add(deck1.toList()) && !deck2s.add(deck2.toList())) return Pair(true, 0)
            val card1 = deck1.removeAt(0)
            val card2 = deck2.removeAt(0)
            //log("$level $card1 $card2")
            val player1Wins = if (card1 <= deck1.size && card2 <= deck2.size) play(
                deck1.take(card1).toMutableList(),
                deck2.take(card2).toMutableList(),
                level + 1
            ).first else card1 > card2
            if (player1Wins) {
                deck1 += card1
                deck1 += card2
            } else {
                deck2 += card2
                deck2 += card1
            }
            //log("$level $deck1 $deck2")
        }
        return score(deck1, deck2)
    }

    private val deck1Init = lines.takeWhile { it.isNotBlank() }.drop(1).map { it.toInt() }.toMutableList()
    private val deck2Init = lines.takeLastWhile { it.isNotBlank() }.drop(1).map { it.toInt() }.toMutableList()

    private fun score(deck1: List<Int>, deck2: List<Int>): Pair<Boolean, Int> {
        val player1Wins = deck1.isNotEmpty()
        return player1Wins to (if (player1Wins) deck1 else deck2).reversed()
            .mapIndexed { index, card -> (index + 1) * card }.sum()
    }
}
