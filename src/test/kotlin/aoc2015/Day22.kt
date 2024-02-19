package aoc2015

import Day
import tools.graph.shortPath
import tools.toWords

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1() = minCost(false)
    override fun solvePart2() = minCost(true)

    private fun minCost(hard: Boolean) = shortPath(
        initialState,
        isEnd = { didIWin() == true }, // I win
        cost = { _, next -> next.spell?.run { cost.toDouble() } ?: 0.0 }
    ) {
        when {
            didIWin() == false -> emptyList() // boss wins
            spell == null -> validSpells.map { myTurn(it, hard) }// my turn
            else -> listOf(bossTurn())// boss turn
        }
    }.sumOf { it.spell?.cost ?: 0 }

    private val initialState = State(
        enemyDamage = lines[1].toWords().last().toInt(),
        enemyHP = lines[0].toWords().last().toInt(),
        myHP = 50,
        mana = 500,
        armor = 0,
        shield = 0,
        poison = 0,
        recharge = 0,
        spell = null
    )

    enum class Spell(val cost: Int) { Missile(53), Drain(73), Shield(113), Poison(173), Recharge(229) }

    data class State(
        val enemyDamage: Int,
        val enemyHP: Int,
        val myHP: Int,
        val mana: Int,
        val armor: Int,
        val shield: Int,
        val poison: Int,
        val recharge: Int,
        val spell: Spell?
    ) {
        val validSpells = Spell.entries.filter { it.cost <= mana }
            .filter { shield <= 1 || it != Spell.Shield }
            .filter { poison <= 1 || it != Spell.Poison }
            .filter { recharge <= 1 || it != Spell.Recharge }

        fun didIWin() = if (enemyHP <= 0) true else if (myHP <= 0) false else null

        fun myTurn(spell: Spell, hard: Boolean): State {
            val s = if (hard) copy(myHP = myHP - 1) else this
            if (s.myHP <= 0) return s
            return with(s.startTurn(spell)) {
                when (spell) {
                    Spell.Missile -> copy(enemyHP = enemyHP - 4)
                    Spell.Drain -> copy(enemyHP = enemyHP - 2, myHP = myHP + 2)
                    Spell.Shield -> copy(armor = armor + 7, shield = 6)
                    Spell.Poison -> copy(poison = 6)
                    Spell.Recharge -> copy(recharge = 5)
                }
            }
        }

        fun bossTurn() = with(startTurn(null)) { copy(myHP = myHP - (enemyDamage - armor).coerceAtLeast(1)) }

        private fun startTurn(spell: Spell?) = copy(
            enemyHP = if (poison > 0) enemyHP - 3 else enemyHP,
            mana = (if (recharge > 0) mana + 101 else mana) - (spell?.cost ?: 0),
            armor = if (shield == 1) armor - 7 else armor,
            shield = shield.decrement(),
            poison = poison.decrement(),
            recharge = recharge.decrement(),
            spell = spell,
        )

        private fun Int.decrement() = (this - 1).coerceAtLeast(0)
    }
}
