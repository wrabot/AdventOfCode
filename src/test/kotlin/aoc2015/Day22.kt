package aoc2015

import Day
import tools.graph.shortPath
import tools.toWords

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1() = minCost(initialState)
    override fun solvePart2() = minCost(initialState.copy(hard = true))

    private fun minCost(start: State) = shortPath(
        start = start,
        isEnd = State::isEnd,
        cost = { _, to -> to.cost.toDouble() },
        neighbors = State::neighbors
    ).sumOf { it.cost }

    private val initialState = State(
        enemyDamage = lines[1].toWords().last().toInt(),
        enemyHP = lines[0].toWords().last().toInt(),
        myHP = 50,
        mana = 500,
    )

    private enum class Spell(val cost: Int) { Missile(53), Drain(73), Shield(113), Poison(173), Recharge(229) }

    private data class State(
        val enemyDamage: Int,
        val enemyHP: Int,
        val myHP: Int,
        val mana: Int,
        val armor: Int = 0,
        val shield: Int = 0,
        val poison: Int = 0,
        val recharge: Int = 0,
        val cost: Int = 0,
        val hard: Boolean = false,
    ) {

        fun isEnd() = didIWin() == true

        fun neighbors() = when {
            didIWin() == false -> emptyList()
            cost == 0 -> validSpells.map { myTurn(it) }
            else -> listOf(bossTurn())
        }

        private val validSpells = Spell.entries.filter { it.cost <= mana }
            .filter { shield <= 1 || it != Spell.Shield }
            .filter { poison <= 1 || it != Spell.Poison }
            .filter { recharge <= 1 || it != Spell.Recharge }

        private fun didIWin() = if (enemyHP <= 0) true else if (myHP <= 0) false else null

        private fun myTurn(spell: Spell): State {
            val s = if (hard) copy(myHP = myHP - 1) else this
            if (s.myHP <= 0) return s
            return with(s.initTurn(spell.cost)) {
                when (spell) {
                    Spell.Missile -> copy(enemyHP = enemyHP - 4)
                    Spell.Drain -> copy(enemyHP = enemyHP - 2, myHP = myHP + 2)
                    Spell.Shield -> copy(armor = armor + 7, shield = 6)
                    Spell.Poison -> copy(poison = 6)
                    Spell.Recharge -> copy(recharge = 5)
                }
            }
        }

        private fun bossTurn() = with(initTurn(0)) { copy(myHP = myHP - (enemyDamage - armor).coerceAtLeast(1)) }

        private fun initTurn(cost: Int) = copy(
            enemyHP = if (poison > 0) enemyHP - 3 else enemyHP,
            mana = (if (recharge > 0) mana + 101 else mana) - cost,
            armor = if (shield == 1) armor - 7 else armor,
            shield = shield.decrement(),
            poison = poison.decrement(),
            recharge = recharge.decrement(),
            cost = cost,
        )

        private fun Int.decrement() = (this - 1).coerceAtLeast(0)
    }
}
