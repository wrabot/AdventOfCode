package tools

import java.math.BigInteger

fun Iterable<BigInteger>.sum() = reduce { acc, bi -> acc + bi }

fun Long.modTimes(other: Long, mod: Long): Long {
    var res = 0L
    var ta = this % mod
    var tb = other % mod
    while (true) {
        if (tb and 1L > 0L) res = (res + ta) % mod
        tb = tb shr 1
        if (tb == 0L) return res
        ta = (ta shl 1) % mod
    }
}
