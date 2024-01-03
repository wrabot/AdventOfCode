package tools.graph

data class ValuedEdge(val source: Int, val destination: Int, val value: Double)

fun bfs(size: Int, start: Int, block: (Int) -> List<Int>) {
    val done = BooleanArray(size)
    val todo = mutableListOf(start)
    while (true) {
        val current = todo.removeFirstOrNull() ?: break
        done[current] = true
        todo.addAll(block(current).filterNot { done[it] })
    }
}

fun dfs(size: Int, start: Int, block: (Int) -> List<Int>) {
    val done = BooleanArray(size)
    val todo = mutableListOf(start)
    while (true) {
        val current = todo.removeLastOrNull() ?: break
        done[current] = true
        todo.addAll(block(current).filterNot { done[it] })
    }
}
