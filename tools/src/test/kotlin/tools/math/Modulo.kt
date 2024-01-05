package tools.math

import kotlin.test.Test
import kotlin.test.assertEquals

class Modulo {
    @Test
    fun testTimes() {
        assertEquals(1021806769582L, 56353336663L.times(454352252L, 7645435225654L))
    }

    @Test
    fun testInv() {
        assertEquals(3032969833125L, 56353336663L.inv(7645435225654L))
    }

    @Test
    fun testChineseRemainder() {
        assertEquals(23, chineseRemainder(listOf(2L to 3L, 3L to 5L, 2L to 7L)))
    }
}