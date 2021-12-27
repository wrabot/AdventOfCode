package aoc2020

import forEachInput
import tools.log

object Day19 {
    fun solve() = forEachInput(2020, 19, 2) { lines ->
        val rules = lines.takeWhile { it.isNotBlank() }.map { it.split(": ") }.map { (id, rule) ->
            id to rule.replace("\"", "").split(" | ").map { it.split(" ") }
        }.toMap()
        val messages = lines.takeLastWhile { it.isNotBlank() }

        log("part 1: ")
        val regex0 = rules.createRegex("0").toRegex()
        messages.count { it.matches(regex0) }.log()

        log("part 2: ")
        // rule 8 and 11 only used by rule 0
        // 0: 8 11
        // 8: 42 | 8
        // 11: 42 31 | 42 11 31
        // the rule 0 can be transform as 0: 42 8 31 | 42 0 31
        val regex42 = rules.createRegex("42")
        val regex31 = rules.createRegex("31")
        val regex8 = "$regex42+".toRegex()
        val regex0Recursive = "$regex42(.*)$regex31".toRegex()
        messages.count { regex0Recursive.matches(it) && regex8.matches(it.recursiveMatches(regex0Recursive)) }.log()
    }

    private fun CharSequence.recursiveMatches(regex: Regex): CharSequence =
        regex.matchEntire(this)?.groupValues?.getOrNull(1)?.recursiveMatches(regex) ?: this

    private fun Map<String, List<List<String>>>.createRegex(id: String): String =
        this[id]?.joinToString("|", "(?:", ")") { rule -> rule.joinToString("") { createRegex(it) } } ?: id
}
