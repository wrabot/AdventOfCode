package aoc2015

import Day

class Day20(test: Int? = null) : Day(2015, 20, test) {
    override fun solvePart1(): Int {
        var house = 2
        while (true) {
            if (presents(house) >= goal) return house
            house++
        }
    }

    override fun solvePart2() = Unit

    private fun presents(house: Int): Int {
        var presents = house
        for (i in 1..(house + 1) / 2) {
            if (house % i == 0) presents += i
        }
        return presents * 10
    }


    private val goal = input.toInt()
}
