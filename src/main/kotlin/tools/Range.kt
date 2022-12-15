package tools

import java.lang.Integer.max

fun List<IntRange>.merge() = sortedBy { it.first }.run {
    drop(1).fold(listOf(first())) { acc, range ->
        val last = acc.last()
        if (range.first in acc.last())
            acc.dropLast(1).plusElement(last.first..max(last.last, range.last))
        else
            acc.plusElement(range)
    }
}

fun IntRange.size() = last - first + 1
