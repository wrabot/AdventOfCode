package tools.range

fun <T> List<T>.combinations(length: Int = size): List<List<T>> = if (length <= 0 && isEmpty()) listOf(emptyList()) else
    flatMap { first -> minus(first).combinations(length - 1).map { listOf(first) + it } }

fun <T> enumerate(base: Int, length: Int, prefix: List<Int> = emptyList(), block: (List<Int>) -> T): List<T> =
    if (length <= 0) listOf(block(prefix)) else (0 until base).flatMap {
        enumerate(base, length - 1, prefix + it, block)
    }

fun <T> List<T>.selectCombinations(): List<List<T>> {
    if (isEmpty()) return listOf(emptyList())
    val first = take(1)
    val others = drop(1).selectCombinations()
    return others + others.map { first + it }
}

fun <T> List<T>.orderCombinations(): List<List<T>> {
    if (isEmpty()) return listOf(emptyList())
    return flatMap { first -> minus(first).orderCombinations().map { listOf(first) + it } }
}

fun <T> List<T>.runOrderCombinations(path: List<T>, block: (List<T>) -> Boolean): Boolean {
    if (isEmpty()) return block(path)
    return any { first -> block(path) && minus(first).runOrderCombinations(path + first, block) }
}

