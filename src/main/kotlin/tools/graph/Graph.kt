@file:Suppress("unused")

package tools.graph

data class ValuedEdge(val source: Int, val destination: Int, val value: Double)

@JvmName("valueEdgeToGraphWiz")
fun List<ValuedEdge>.toGraphWiz() = map { Triple(it.source, it.destination, it.value) }.toGraphWiz()

@JvmName("tripleToGraphWiz")
fun List<Triple<Any, Any, Any?>>.toGraphWiz() = joinToString("\n", "digraph {\n", "\n}") {
    "${it.first} -> ${it.second}" + if (it.third == null) "" else " [label=${it.third}]"
}

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
