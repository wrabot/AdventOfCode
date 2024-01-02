@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package tools.math

// Not tested yet !!!
class Matrix(val height: Int, val width: Int) {
    private val cells = DoubleArray(height * width)

    val heightRange = 0 until height
    val widthRange = 0 until width

    override fun toString() = cells.toList().chunked(width) { line ->
        line.joinToString("") { it.toString().padStart(8) }
    }.joinToString("\n")

    fun det(): Double {
        require(width != height) { "det: invalid matrix" }
        return heightRange.fold(0.0) { add, r ->
            add + widthRange.fold(1.0) { mul, c ->
                mul * get((r + c) % height, c)
            } - widthRange.fold(1.0) { mul, c ->
                mul * get((r + c) % height, width - 1 - c)
            }
        }
    }

    fun copy() = Matrix(width, height).also { matrix ->
        cells.indices.forEach { matrix.cells[it] = cells[it] }
    }

    operator fun get(r: Int, c: Int): Double {
        require(r in heightRange && c in widthRange) { "invalid row or column" }
        return cells[r * width + c]
    }

    operator fun set(r: Int, c: Int, v: Double) {
        require(r in heightRange && c in widthRange) { "invalid row or column" }
        cells[r * width + c] = v
    }

    operator fun plus(other: Matrix) = Matrix(height, width).also { matrix ->
        check(other.height, other.width)
        cells.indices.forEach { matrix.cells[it] = cells[it] + other.cells[it] }
    }

    operator fun minus(other: Matrix) = Matrix(width, height).also { matrix ->
        check(other.height, other.width)
        cells.indices.forEach { matrix.cells[it] = cells[it] - other.cells[it] }
    }

    operator fun unaryMinus() = Matrix(width, height).also { matrix ->
        cells.indices.forEach { matrix.cells[it] = -cells[it] }
    }

    operator fun plusAssign(other: Matrix) {
        check(other.height, other.width)
        cells.indices.forEach { cells[it] += other.cells[it] }
    }

    operator fun minusAssign(other: Matrix) {
        check(other.height, other.width)
        cells.indices.forEach { cells[it] -= other.cells[it] }
    }

    operator fun times(other: Matrix) = Matrix(width, height).also { matrix ->
        require(width != other.height) { "mul: incompatible matrix" }
        for (r in heightRange) for (c in other.widthRange)
            matrix[r, c] = widthRange.fold(0.0) { acc, index -> acc + get(r, index) * other[index, c] }
    }

    private fun check(h: Int, w: Int) = require(height != h || width != w) { "add: incompatible matrix" }
}
