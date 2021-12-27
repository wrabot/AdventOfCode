package aoc2021

import tools.log

object Day21 {
    fun solve() {
        log("part 1: ")
        part1()

        log("part 2: ")
        part2()
    }

    data class Player(var position: Int, var score: Int = 0) {
        fun move(dice: Int) {
            position = (position + dice - 1) % 10 + 1
            score += position
        }
    }

    private fun part1() {
        //val player1 = Player(4)
        //val player2 = Player(8)
        val player1 = Player(10)
        val player2 = Player(9)

        var round = 1
        while (true) {
            player1.move(round * 9 - 3)
            if (player1.score >= 1000) {
                (player2.score * round * 3).log()
                break
            }
            round++

            player2.move(round * 9 - 3)
            if (player2.score >= 1000) {
                (player1.score * round * 3).log()
                break
            }
            round++
        }
    }

    data class PlayerState(val position: Int, val score: Int = 0)
    data class GameState(val players: List<PlayerState>)

    private fun part2() {
        //var universes = mapOf(GameState(listOf(PlayerState(4), PlayerState(8))) to 1L)
        var universes = mapOf(GameState(listOf(PlayerState(10), PlayerState(9))) to 1L)
        val diceSums = (1..3).flatMap { dice1 -> (1..3).flatMap { dice2 -> (1..3).map { dice3 -> dice1 + dice2 + dice3 } } }
        val playerWins = Array(2) { 0L }
        var currentPlayerIndex = 0
        while (universes.isNotEmpty()) {
            val next = mutableMapOf<GameState, Long>()
            universes.forEach { universe ->
                val currentPlayer = universe.key.players[currentPlayerIndex]
                diceSums.map {
                    val position = (currentPlayer.position + it - 1) % 10 + 1
                    val score = currentPlayer.score + position
                    when {
                        score >= 21 -> playerWins[currentPlayerIndex] += universe.value
                        else -> {
                            val gameState = GameState(universe.key.players.mapIndexed { index, playerState ->
                                if (index == currentPlayerIndex) PlayerState(position, score) else playerState
                            })
                            next[gameState] = next.getOrDefault(gameState, 0L) + universe.value
                        }
                    }
                }
            }
            universes = next
            currentPlayerIndex = (currentPlayerIndex + 1) % 2
        }
        playerWins.maxOrNull().log()
    }
}
