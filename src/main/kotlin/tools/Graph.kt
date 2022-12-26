package tools

import java.util.*

fun <Node : Any> shortPath(start: Node, end: Node, neighbors: (Node) -> List<Node>) =
    shortPath(start, isEnd = { this == end }, neighbors = neighbors)

fun <Node : Any> shortPath(start: Node, end: Node, cost: (Node) -> Int, neighbors: (Node) -> List<Node>) =
    shortPath(start, isEnd = { this == end }, { _, currentCost, destination -> currentCost + cost(destination) }, neighbors)

fun <Node : Any> shortPath(
    start: Node,
    isEnd: Node.() -> Boolean,
    cost: (origin: Node, currentCost: Int, destination: Node) -> Int = { _, currentCost, _ -> currentCost + 1 },
    neighbors: Node.() -> List<Node>
): List<Node> {
    val predecessor = mutableMapOf<Node, Node>()
    val costs = mutableMapOf(start to 0)
    val todo = PriorityQueue<Node>(compareBy { costs[it] })
    todo.offer(start)
    while (true) {
        val currentNode = todo.poll() ?: break
        if (currentNode.isEnd()) return generateSequence(currentNode) { predecessor[it] }.toList().reversed()
        val currentCost = costs[currentNode]!!
        neighbors(currentNode).forEach { next ->
            val nextCost = cost(currentNode, currentCost, next)
            if (nextCost < costs.getOrDefault(next, Int.MAX_VALUE)) {
                costs[next] = nextCost
                todo.offer(next)
                predecessor[next] = currentNode
            }
        }
    }
    return emptyList()
}
