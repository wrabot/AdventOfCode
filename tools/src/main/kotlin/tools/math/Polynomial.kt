package tools.math

fun List<Double>.polynomialCoefficients(): List<Double> =
    if (all { it == 0.0 }) emptyList() else zipWithNext().map { it.second - it.first }.polynomialCoefficients() + last()

fun List<Double>.polynomial(x: Double) = reversed().foldRightIndexed(0.0) { i, c, acc -> acc * (x + i) / (i + 1) + c }
