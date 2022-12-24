package tools

fun <Node : Any> shortPath(start: Node, end: Node, neighbors: (Node) -> List<Node>) =
    shortPath2(start, end, neighbors) { _, currentCost, _ -> currentCost + 1 }

fun <Node : Any> shortPath(start: Node, end: Node, cost: (Node) -> Int, neighbors: (Node) -> List<Node>) =
    shortPath2(start, end, neighbors) { _, currentCost, destination -> currentCost + cost(destination) }

fun <Node : Any> shortPath2(start: Node, end: Node, neighbors: (Node) -> List<Node>, cost: (origin: Node, currentCost: Int, destination: Node) -> Int): List<Node> {
    val todo = mutableMapOf(start to 0)
    val done = mutableSetOf<Node>()
    val predecessor = mutableMapOf<Node, Node>()
    while (true) {
        val (currentNode, currentCost) = todo.minByOrNull { it.value } ?: break
        if (currentNode == end) break
        todo.remove(currentNode)
        done.add(currentNode)
        neighbors(currentNode).forEach { next ->
            if (next !in done) {
                val nextCost = cost(currentNode, currentCost, next)
                if (nextCost < todo.getOrDefault(next, Int.MAX_VALUE)) {
                    todo[next] = nextCost
                    predecessor[next] = currentNode
                }
            }
        }
    }
    return generateSequence(end) { predecessor[it] }.toList().reversed()
}
