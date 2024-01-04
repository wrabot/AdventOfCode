package aoc2023

import aoc2023.Day7.HandType.*
import Day

class Day7(test: Int? = null) : Day(2023, 7, test) {
    override fun solvePart1() = solve("?23456789TJQKA") // ? => no joker

    override fun solvePart2() = solve("J23456789TQKA")

    private fun solve(cardsOrder: String) = lines
        .map { line ->
            line.split(" ").let { (cards, bid) ->
                Hand(cards.map { cardsOrder.indexOf(it) }, bid.toInt())
            }
        }
        .sorted()
        .foldIndexed(0) { index, acc, hand ->
            acc + (index + 1) * hand.bid
        }

    enum class HandType { HighCard, OnePair, TwoPair, ThreeKind, FullHouse, FourKind, FiveKind }
    data class Hand(val cards: List<Int>, val bid: Int) : Comparable<Hand> {
        private val type = cards.groupingBy { it }.eachCount().let { counts ->
            val joker = counts[0] ?: 0
            val others = counts.filter { it.key != 0 }.values.sortedDescending()
            val primarySize = others.getOrNull(0) ?: 0
            val hasSecondaryPair = others.getOrNull(1) == 2
            when (primarySize + joker) {
                5 -> FiveKind
                4 -> FourKind
                3 -> if (hasSecondaryPair) FullHouse else ThreeKind
                2 -> if (hasSecondaryPair) TwoPair else OnePair
                else -> HighCard
            }
        }

        override fun compareTo(other: Hand) =
            type.compareTo(other.type).takeIf { it != 0 } ?: cards.zip(other.cards).map { it.first - it.second }
                .find { it != 0 } ?: 0
    }
}
