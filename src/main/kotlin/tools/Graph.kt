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
    val todo = TreeMap<Int, MutableList<Node>>()
    todo[0] = mutableListOf(start)
    while (true) {
        val (currentCost, nodes) = todo.firstEntry() ?: break
        val currentNode = nodes.first()
        if (currentNode.isEnd()) return generateSequence(currentNode) { predecessor[it] }.toList().reversed()
        todo.remove(currentCost, currentNode)
        neighbors(currentNode).forEach { nextNode ->
            val oldCost = costs[nextNode]
            val nextCost = cost(currentNode, currentCost, nextNode)
            if (nextCost < (oldCost ?: Int.MAX_VALUE)) {
                predecessor[nextNode] = currentNode
                costs[nextNode] = nextCost
                todo.getOrPut(nextCost) { mutableListOf() }.add(nextNode)
                if (oldCost != null) todo.remove(oldCost, nextNode)
            }
        }
    }
    return emptyList()
}

private fun <Node : Any> TreeMap<Int, MutableList<Node>>.remove(cost: Int, node: Node) = apply {
    this[cost]!!.let {
        it.remove(node)
        if (it.isEmpty()) remove(cost)
    }
}
