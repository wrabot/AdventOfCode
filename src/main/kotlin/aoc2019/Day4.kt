package aoc2019

import Day

class Day4(test: Int? = null) : Day() {
    override fun solvePart1() = passwords.count { it.count() != it.distinct().count() }
    override fun solvePart2() = passwords.count { password -> password.groupingBy { it }.eachCount().containsValue(2) }
    private val passwords = (147981..691423).map { it.toString().toList() }.filter { it == it.sorted() }
}
