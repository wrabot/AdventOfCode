package tools

fun <Node : Any> shortPath(start: Node, end: Node, neighbors: (Node) -> Map<Node, Int>): List<Node> {
    val todo = mutableMapOf(start to 0)
    val done = mutableSetOf<Node>()
    val predecessor = mutableMapOf<Node, Node>()
    while (true) {
        val current = todo.minByOrNull { it.value } ?: break
        if (current.key == end) return generateSequence(end) { predecessor[it] }.toList().reversed()
        todo.remove(current.key)
        done.add(current.key)
        neighbors(current.key).forEach { next ->
            val cost = current.value + next.value
            if (next.key !in done && todo.getOrDefault(next.key, Int.MAX_VALUE) > cost) {
                todo[next.key] = cost
                predecessor[next.key] = current.key
            }
        }
    }
    return emptyList()
}
