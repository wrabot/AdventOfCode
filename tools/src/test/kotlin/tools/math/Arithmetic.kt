package tools.math

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Arithmetic {
    @Test
    fun testGcd() {
        assertEquals(12, gcd( 36, 60))
    }

    @Test
    fun testLcm() {
        assertEquals(180, lcm(36, 60))
    }
}
