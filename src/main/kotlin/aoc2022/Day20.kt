package aoc2022

import Day

class Day20(test: Int? = null) : Day(2022, 20, test) {
    override fun solvePart1() = lines.map { Cell(it.toLong()) }.mixNumbers(1)

    override fun solvePart2() = lines.map { Cell(it.toLong() * 811589153) }.mixNumbers(10)

    class Cell(val number: Long) // boxing to avoid comparing number

    private fun List<Cell>.mixNumbers(times: Int): Long {
        val mixNumbers = toMutableList()
        repeat(times) {
            forEach { cell ->
                val origin = mixNumbers.indexOf(cell)
                mixNumbers.removeAt(origin)
                val destination = ((origin + cell.number) % mixNumbers.size).toInt()
                mixNumbers.add(if (destination < 0) destination + mixNumbers.size else destination, cell)
            }
        }
        val zero = mixNumbers.indexOfFirst { it.number == 0L }
        return listOf(1000, 2000, 3000).sumOf { mixNumbers[(zero + it) % mixNumbers.size].number }
    }
}
