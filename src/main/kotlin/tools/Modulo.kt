@file:Suppress("unused")

package tools

fun Long.times(multiplier: Long, modulo: Long): Long {
    if (this < 0) error("negative multiplicand")
    if (multiplier < 0) error("negative multiplier")
    if (modulo < 0) error("negative modulo")
    var res = 0L
    var a = this % modulo
    var b = multiplier % modulo
    while (true) {
        if (b and 1L > 0L) res = (res + a) % modulo
        b = b shr 1
        if (b == 0L) return res
        a = a shl 1
        if (a < 0) error("overflow")
        a %= modulo
    }
}

fun Long.inv(modulo: Long): Long = inverse(this, modulo, 1, 0).let { if (it < 0) it + modulo else it }
private tailrec fun inverse(v: Long, n: Long, s: Long, t: Long): Long =
    if (v == 1L) s else inverse(n % v, v, t - n / v * s, s)

fun chineseRemainder(input: List<Pair<Long, Long>>): Long {
    val np = input.fold(1L) { acc, i -> acc * i.second }
    return input.fold(0L) { acc, (v, n) -> (np / n).let { (acc + v.times(it.inv(n), np).times(it, np)).mod(np) } }
}
