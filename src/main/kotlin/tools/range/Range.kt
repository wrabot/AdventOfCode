@file:Suppress("unused")

package tools.range

import kotlin.math.max
import kotlin.math.min

fun IntRange.size() = last - first + 1
fun LongRange.size() = last - first + 1

fun IntRange.move(offset: Int) = first + offset..last + offset
fun LongRange.move(offset: Long) = first + offset..last + offset

fun IntRange.hasIntersection(other: IntRange) = first in other || last in other || other.first in this
fun LongRange.hasIntersection(other: LongRange) = first in other || last in other || other.first in this

fun rangeMinMax(a: Int, b: Int) = min(a, b)..max(a, b)
fun rangeMinMax(a: Long, b: Long) = min(a, b)..max(a, b)
