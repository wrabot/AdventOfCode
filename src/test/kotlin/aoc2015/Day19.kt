package aoc2015

import Day

class Day19(test: Int? = null) : Day(test) {
    override fun solvePart1() = replacements.flatMap { (replacement, regex) ->
        regex.toRegex().findAll(molecule).map { molecule.replaceRange(it.range, replacement) }
    }.distinct().count()

    // Rn, Ar, Y, C are only in right side
    // Rn/Ar are linked together. Y is between Rn & AR. C is before Rn
    // Rules are only growing
    // a => 1 to 2 3
    // b => 1 to 2 Rn 9 Ar where 9 is 1 or 1 Y 2 or 1 Y 2 Y 3.
    // only one solution which is the best or same amount of replacement
    // for b, it adds 1 plus Rn and Ar and for each Y, it adds another one
    // So Rn Ar can be removed and fall back to the rule a with removing Y twice
    override fun solvePart2() = molecule.replace("Rn", "").replace("Ar", "").run {
        count { it.isUpperCase() } - 1 - 2 * count { it == 'Y' }
    }

    // common

    private val replacements = lines.takeWhile { it != "" }
        .associate { it.split(" => ").let { (origin, replacement) -> replacement to origin } }
    private val molecule = lines.last()
}
