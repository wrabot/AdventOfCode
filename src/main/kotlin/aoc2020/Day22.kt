package aoc2020

import forEachInput
import tools.log

object Day22 {
    fun solve() = forEachInput(2020, 22, 2) { lines ->
        val deck1 = lines.takeWhile { it.isNotBlank() }.drop(1).map { it.toInt() }.toMutableList()
        val deck2 = lines.takeLastWhile { it.isNotBlank() }.drop(1).map { it.toInt() }.toMutableList()

        log("part 1: ")
        day22part1(deck1.toMutableList(), deck2.toMutableList()).log()

        log("part 2: ")
        day22part2(deck1.toMutableList(), deck2.toMutableList(), 1).log()
    }

    private fun day22part1(deck1: MutableList<Int>, deck2: MutableList<Int>): Pair<Boolean, Int> {
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
        return score(deck1, deck2)
    }

    private fun day22part2(deck1: MutableList<Int>, deck2: MutableList<Int>, level: Int): Pair<Boolean, Int> {
        //log("$level $deck1 $deck2")
        val deck1s = mutableSetOf<List<Int>>()
        val deck2s = mutableSetOf<List<Int>>()
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            if (!deck1s.add(deck1.toList()) && !deck2s.add(deck2.toList())) return Pair(true, 0)
            val card1 = deck1.removeAt(0)
            val card2 = deck2.removeAt(0)
            //log("$level $card1 $card2")
            val player1Wins = if (card1 <= deck1.size && card2 <= deck2.size) day22part2(deck1.take(card1).toMutableList(), deck2.take(card2).toMutableList(), level + 1).first else card1 > card2
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

    private fun score(deck1: List<Int>, deck2: List<Int>): Pair<Boolean, Int> {
        val player1Wins = deck1.isNotEmpty()
        return player1Wins to (if (player1Wins) deck1 else deck2).reversed().mapIndexed { index, card -> (index + 1) * card }.sum()
    }
}
