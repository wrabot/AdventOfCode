package tools

import java.util.*

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
    val todo = TreeMap<Int, MutableList<ExtendedNode<Node>>>()
    todo[extendedStart.toEndMinimalCost] = mutableListOf(extendedStart)
    while (true) {
        val currentExtendedNode = todo.firstEntry()?.value?.first() ?: return emptyList()
        if (currentExtendedNode.node.isEnd()) return generateSequence(currentExtendedNode) { it.predecessor }.map { it.node }.toList().reversed()
        todo.remove(currentExtendedNode)
        neighbors(currentExtendedNode.node).forEach { nextNode ->
            val newFromStartCost = currentExtendedNode.fromStartCost + cost(currentExtendedNode.node, nextNode)
            val nextExtendedNode = extendedNodes[nextNode]
            if (nextExtendedNode == null) {
                ExtendedNode(nextNode, newFromStartCost, newFromStartCost + toEndMinimalCost(nextNode), currentExtendedNode).apply {
                    extendedNodes[nextNode] = this
                    todo.add(this)
                }
            } else if (newFromStartCost < nextExtendedNode.fromStartCost) {
                nextExtendedNode.predecessor = currentExtendedNode
                nextExtendedNode.fromStartCost = newFromStartCost
                val newToEndMinimalCost = newFromStartCost + toEndMinimalCost(nextNode)
                if (nextExtendedNode.toEndMinimalCost != newToEndMinimalCost) {
                    todo.remove(nextExtendedNode)
                    nextExtendedNode.toEndMinimalCost = newToEndMinimalCost
                    todo.add(nextExtendedNode)
                }
            }
        }
    }
}

private data class ExtendedNode<Node : Any>(val node: Node, var fromStartCost: Int, var toEndMinimalCost: Int, var predecessor: ExtendedNode<Node>?)

private fun <Node : Any> TreeMap<Int, MutableList<ExtendedNode<Node>>>.add(node: ExtendedNode<Node>) = getOrPut(node.toEndMinimalCost) { mutableListOf() }.add(node)

private fun <Node : Any> TreeMap<Int, MutableList<ExtendedNode<Node>>>.remove(node: ExtendedNode<Node>) = apply {
    this[node.toEndMinimalCost]?.let {
        it.remove(node)
        if (it.isEmpty()) remove(node.toEndMinimalCost)
    }
}
