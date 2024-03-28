package aoc2019

import Day
import tools.math.Modular
import tools.math.bi
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class Day22(var test: Int? = null) : Day(test) {
    override fun solvePart1() = techniques.fold(2019) { acc, technique -> technique.move(acc, 10007) }
    
    override fun solvePart2(): Long {
        val size = 119315717514047.bi
        val reduceTechniques = techniques.foldRight(ONE to ZERO) { technique, acc -> technique.reduce(acc, size) }
        return Modular(size).run {
            val reduceRepeat = reduceRepeat(reduceTechniques, 101741582076661.bi)
            reduceRepeat.first * 2020.bi + reduceRepeat.second
        }.longValueExact()
    }

    // a(a(ax+b)+b)+b = a^3x+a^2b+ab+b
    // more generic => a^n + (a^(n-1)+ ... +a + 1)*b
    // a^n + (a^n -1)/(a-1)*b
    private fun Modular.reduceRepeat(c: Pair<BigInteger, BigInteger>, times: BigInteger): Pair<BigInteger, BigInteger> {
        val an = c.first.pow(times)
        return an to (an - ONE) / (c.first - ONE) * c.second
    }

    // common

    private sealed interface Technique {
        fun move(card: Int, size: Int): Int

        // each technique is modulo operation
        // Deal is multiplication
        // Cut is Subtraction
        // new stack is a reverse but is also a multiplication by size-1 minus 1
        // so each is technique is ax+b operation and a list can be reduced to one operation
        fun reduce(c: Pair<BigInteger, BigInteger>, size: BigInteger): Pair<BigInteger, BigInteger>

        data object DealIntoNewStack : Technique {
            override fun move(card: Int, size: Int) = size - 1 - card
            override fun reduce(c: Pair<BigInteger, BigInteger>, size: BigInteger) = Modular(size).run {
                c.first * (size - ONE) to (c.second * (size - ONE) - ONE)
            }
        }

        data class Deal(val increment: BigInteger) : Technique {
            override fun move(card: Int, size: Int) = (card * increment.toInt()).mod(size)
            override fun reduce(c: Pair<BigInteger, BigInteger>, size: BigInteger) = Modular(size).run {
                c.first / increment to c.second / increment
            }
        }

        data class Cut(val offset: BigInteger) : Technique {
            override fun move(card: Int, size: Int) = (card - offset.toInt()).mod(size)
            override fun reduce(c: Pair<BigInteger, BigInteger>, size: BigInteger) = Modular(size).run {
                c.first to (c.second + offset)
            }
        }
    }

    private val techniques = lines.map {
        when {
            it == "deal into new stack" -> Technique.DealIntoNewStack
            it.startsWith("deal with increment") -> Technique.Deal(it.split(' ').last().toBigInteger())
            it.startsWith("cut ") -> Technique.Cut(it.split(' ').last().toBigInteger())
            else -> error("unexpected !!!")
        }
    }
}
