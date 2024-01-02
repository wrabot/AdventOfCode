@file:Suppress("unused")

package tools.range

import kotlin.math.max
import kotlin.math.min

fun List<IntRange>.merge() = mutableListOf<IntRange>().also { merge ->
    sortedBy { it.first }.forEach {
        val last = merge.lastOrNull()
        when {
            last == null -> merge.add(it)
            it.first in last -> merge[merge.lastIndex] = last.first..max(last.last, it.last)
            else -> merge.add(it)
        }
    }
}

fun <T : Comparable<T>> OpenEndRange<T>.splitWith(other: OpenEndRange<T>) = when {
    start >= other.endExclusive -> Triple(null, null, this)
    endExclusive <= other.start -> Triple(this, null, null)
    start >= other.start && endExclusive <= other.endExclusive -> Triple(null, this, null)
    start >= other.start -> Triple(null, start..<other.endExclusive, other.endExclusive..<endExclusive)
    endExclusive <= other.endExclusive -> Triple(start..<other.start, other.start..<endExclusive, null)
    else -> Triple(start..<other.start, other, other.endExclusive..<endExclusive)
}

fun LongRange.splitWith(other: LongRange) = when {
    first > other.last -> Triple(null, null, this)
    last < other.first -> Triple(this, null, null)
    first >= other.first && last <= other.last -> Triple(null, this, null)
    first >= other.first -> Triple(null, first..other.last, other.last + 1..last)
    last <= other.last -> Triple(first..<other.first, other.first..last, null)
    else -> Triple(first..<other.first, other, other.last + 1..last)
}
