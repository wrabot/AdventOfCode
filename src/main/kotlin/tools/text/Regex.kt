package tools.text

private val wordsRegex = Regex("\\w+")
fun String.toWords() = wordsRegex.findAll(this).map { it.value }
fun Regex.groupValues(input: CharSequence) = matchEntire(input)?.groupValues.orEmpty().drop(1)
