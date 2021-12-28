package aoc2021

import tools.Day

class Day21(test: Int? = null) : Day(2021, 21, test) {
    data class Player(var position: Int, var score: Int = 0) {
        fun move(dice: Int) {
            position = (position + dice - 1) % 10 + 1
            score += position
        }
    }

    override fun getPart1(): Any {
        val player1 = Player(player1Init)
        val player2 = Player(player2Init)
        var round = 1
        var result = 0
        while (true) {
            player1.move(round * 9 - 3)
            if (player1.score >= 1000) {
                result = player2.score * round * 3
                break
            }
            round++

            player2.move(round * 9 - 3)
            if (player2.score >= 1000) {
                result = player1.score * round * 3
                break
            }
            round++
        }
        return result
    }

    data class PlayerState(val position: Int, val score: Int = 0)
    data class GameState(val players: List<PlayerState>)

    override fun getPart2(): Any {
        var universes = mapOf(GameState(listOf(PlayerState(player1Init), PlayerState(player2Init))) to 1L)
        val diceSums =
            (1..3).flatMap { dice1 -> (1..3).flatMap { dice2 -> (1..3).map { dice3 -> dice1 + dice2 + dice3 } } }
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
        return playerWins.maxOrNull()!!
    }

    private val player1Init = lines[0].toInt()
    private val player2Init = lines[1].toInt()
}
