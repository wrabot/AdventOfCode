package aoc2018

import Day
import tools.match
import kotlin.math.abs

class Day24(test: Int? = null) : Day(test) {
    override fun solvePart1() = abs(fight(0)!!)
    override fun solvePart2(): Int {
        var boost = 0
        while (true) {
            val r = fight(boost++) ?: 0
            if (r > 0) return r
        }
    }

    private fun fight(boost: Int): Int? {
        val immuneSystem = input.split("\n\n")[0].parseArmy("Immune System", boost)
        val infection = input.split("\n\n")[1].parseArmy("Infection", 0)
        while (true) {
            val sortedImmuneSystem = immuneSystem.filter { it.isAlive }.sortedByDescending { it.effectivePower }
            val sortedInfection = infection.filter { it.isAlive }.sortedByDescending { it.effectivePower }
            if (sortedImmuneSystem.isEmpty()) return -sortedInfection.sumOf { it.remaining }
            if (sortedInfection.isEmpty()) return sortedImmuneSystem.sumOf { it.remaining }
            val attacks = mutableMapOf<Units, Units>()
            select(attacks, sortedInfection, sortedImmuneSystem)
            select(attacks, sortedImmuneSystem, sortedInfection)
            var modified = false
            for (attacker in attacks.keys.sortedByDescending { it.initiative }) {
                if (!attacker.isAlive) continue
                if (attacks[attacker]!!.attackBy(attacker)) modified = true
            }
            if (!modified) return null
        }
    }

    private fun select(attacks: MutableMap<Units, Units>, attackers: List<Units>, defenders: List<Units>) {
        val targets = defenders.toMutableList()
        for (attacker in attackers) {
            val target = targets.filter { it.realDamageFrom(attacker) > 0 }.maxByOrNull { it.realDamageFrom(attacker) }
            targets.remove(target ?: continue)
            attacks[attacker] = target
        }
    }

    data class Units(
        val size: Int,
        val hitPoints: Int,
        val attackDamage: Int,
        val attackType: String,
        val initiative: Int,
        val weaknesses: List<String>,
        val immunities: List<String>
    ) {
        var remaining = size
            private set
        val isAlive get() = remaining > 0
        val effectivePower get() = remaining * attackDamage

        override fun toString() = "[$initiative => $remaining]"

        fun realDamageFrom(units: Units) = when (units.attackType) {
            in immunities -> 0
            in weaknesses -> units.effectivePower * 2
            else -> units.effectivePower
        }

        fun attackBy(units: Units): Boolean {
            val previous = remaining
            remaining = (remaining - realDamageFrom(units) / hitPoints).coerceAtLeast(0)
            return previous != remaining
        }
    }

    // parsing

    private val mainRegex =
        "(.*) units each with (.*) hit points(.*) with an attack that does (.*) (.*) damage at initiative (.*)".toRegex()
    private val weaknessesRegex = ".*weak to (\\w+)(?:,? (\\w+))*.*".toRegex()
    private val immunitiesRegex = ".*immune to (\\w+)(?:,? (\\w+))*.*".toRegex()

    private fun createUnits(line: String, boost: Int) = line.match(mainRegex)!!.let {
        Units(
            size = it[0].toInt(),
            hitPoints = it[1].toInt(),
            attackDamage = it[3].toInt() + boost,
            attackType = it[4],
            initiative = it[5].toInt(),
            weaknesses = it[2].match(weaknessesRegex).orEmpty().filter { it.isNotBlank() },
            immunities = it[2].match(immunitiesRegex).orEmpty().filter { it.isNotBlank() },
        )
    }

    private fun String.parseArmy(name: String, boost: Int) = lines().drop(1)
        .mapIndexed { index, line -> createUnits(line, boost) }
        .sortedByDescending { it.initiative }
}
