package tools

fun <Node : Any> shortPath(
    start: Node,
    end: Node,
    cost: (origin: Node, destination: Node) -> Int = { _, _ -> 1 },
    estimatedEndCost: (Node) -> Int = { 0 }, // A*
    neighbors: (Node) -> List<Node>
) = shortPath(start, isEnd = { this == end }, cost, estimatedEndCost, neighbors)

fun <Node : Any> shortPath(
    start: Node,
    isEnd: Node.() -> Boolean,
    cost: (origin: Node, destination: Node) -> Int = { _, _ -> 1 },
    toEndMinimalCost: (Node) -> Int = { 0 }, // A*
    neighbors: Node.() -> List<Node>
): List<Node> {
    val extendedStart = ExtendedNode(start, 0, toEndMinimalCost(start), null)
    val extendedNodes = mutableMapOf(start to extendedStart)
    val todo = mutableListOf<MutableList<ExtendedNode<Node>>>()
    todo.add(extendedStart)
    while (true) {
        val currentExtendedNode = todo.firstOrNull()?.first() ?: return emptyList()
        if (currentExtendedNode.node.isEnd()) return generateSequence(currentExtendedNode) { it.predecessor }.map { it.node }.toList().reversed()
        todo.remove(currentExtendedNode)
        neighbors(currentExtendedNode.node).forEach { nextNode ->
            val newFromStartCost = currentExtendedNode.fromStartCost + cost(currentExtendedNode.node, nextNode)
            val nextExtendedNode = extendedNodes[nextNode]
            if (nextExtendedNode == null) {
                val extendedNode = ExtendedNode(nextNode, newFromStartCost, newFromStartCost + toEndMinimalCost(nextNode), currentExtendedNode)
                extendedNodes[nextNode] = extendedNode
                todo.add(extendedNode)
            } else if (newFromStartCost < nextExtendedNode.fromStartCost) {
                nextExtendedNode.predecessor = currentExtendedNode
                nextExtendedNode.fromStartCost = newFromStartCost
                val newToEndMinimalCost = newFromStartCost + toEndMinimalCost(nextNode)
                if (nextExtendedNode.minimalCost != newToEndMinimalCost) {
                    todo.remove(nextExtendedNode)
                    nextExtendedNode.minimalCost = newToEndMinimalCost
                    todo.add(nextExtendedNode)
                }
            }
        }
    }
}

private data class ExtendedNode<Node : Any>(val node: Node, var fromStartCost: Int, var minimalCost: Int, var predecessor: ExtendedNode<Node>?)

private fun <Node : Any> MutableList<MutableList<ExtendedNode<Node>>>.add(node: ExtendedNode<Node>) {
    val index = binarySearch { it.first().minimalCost - node.minimalCost }
    if (index >= 0) {
        this[index].add(node)
    } else {
        add(-index - 1, mutableListOf(node))
    }
}

private fun <Node : Any> MutableList<MutableList<ExtendedNode<Node>>>.remove(node: ExtendedNode<Node>) {
    val index = binarySearch { it.first().minimalCost - node.minimalCost }
    if (index >= 0) {
        val list = this[index]
        if (list.size <= 1) {
            removeAt(index)
        } else {
            list.remove(node)
        }
    }
}
