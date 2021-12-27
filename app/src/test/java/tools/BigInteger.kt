package tools

import java.math.BigInteger

fun Iterable<BigInteger>.sum() = reduce { acc, bi -> acc + bi }
