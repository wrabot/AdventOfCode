package tools.graph

class FloydWarshall(val size: Int, weights: Map<Pair<Int, Int>, Double>) {
    val indices = 0..<size
    val dist = Array(size) { DoubleArray(size) { Double.POSITIVE_INFINITY } }
    val prev = Array(size) { Array<Int?>(size) { null } }

    override fun toString() = dist.toList().map { it.toList() }.toString()

    init {
        weights.forEach { (u, v), w ->
            dist[u][v] = w
            prev[u][v] = u
        }
        for (v in indices) {
            dist[v][v] = 0.0
            prev[v][v] = v
        }
        for (k in indices)
            for (i in indices)
                for (j in indices) {
                    val d = dist[i][k] + dist[k][j]
                    if (dist[i][j] > d) {
                        dist[i][j] = d
                        prev[i][j] = prev[k][j]
                    }
                }
    }

    fun path(u: Int, v: Int) = path(u, v, emptyList())

    private tailrec fun path(u: Int, v: Int?, path: List<Int>): List<Int> = when {
        v == null -> emptyList()
        u == v -> path
        else -> path(u, prev[u][v], listOf(v) + path)
    }
}
