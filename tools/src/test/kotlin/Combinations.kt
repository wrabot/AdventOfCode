package tools.math

import tools.combinations
import tools.enumerate
import kotlin.test.Test
import kotlin.test.assertEquals

class Combinations {
    @Test
    fun testCombinationsFull() {
        assertEquals(
            listOf(
                listOf(1, 2, 3),
                listOf(1, 3, 2),
                listOf(2, 1, 3),
                listOf(2, 3, 1),
                listOf(3, 1, 2),
                listOf(3, 2, 1),
            ),
            listOf(1, 2, 3).combinations()
        )
    }

    @Test
    fun testCombinationsNotFull() {
        assertEquals(
            listOf(
                listOf(1, 2),
                listOf(1, 3),
                listOf(2, 1),
                listOf(2, 3),
                listOf(3, 1),
                listOf(3, 2),
            ),
            listOf(1, 2, 3).combinations(2)
        )
    }

    @Test
    fun testEnumerate() {
        val result = mutableListOf<List<Int>>()
        enumerate(2, 3) { result.add(it) }
        assertEquals(
            listOf(
                listOf(0, 0, 0),
                listOf(0, 0, 1),
                listOf(0, 1, 0),
                listOf(0, 1, 1),
                listOf(1, 0, 0),
                listOf(1, 0, 1),
                listOf(1, 1, 0),
                listOf(1, 1, 1),
            ),
            result
        )
    }
}
