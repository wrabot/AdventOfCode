package tools

fun <T> List<T>.combinations(length: Int = size): List<List<T>> = if (length <= 0 && isEmpty()) listOf(emptyList()) else
    flatMap { first -> minus(first).combinations(length - 1).map { listOf(first) + it } }

fun <T> enumerate(base: Int, length: Int, prefix: List<Int> = emptyList(), block: (List<Int>) -> T): List<T> =
    if (length <= 0) listOf(block(prefix)) else (0 until base).flatMap {
        enumerate(base, length - 1, prefix + it, block)
    }

