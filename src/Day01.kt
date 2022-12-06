fun main() {
    fun part1(input: List<String>): Int {
        return input.asSequence()
            .map { it.toIntOrNull() }
            .chunkByNotNull()
            .map { it.sum() }
            .max()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val part1Result = part1(testInput)
    check(part1Result == 15)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun <T> Sequence<T?>.chunkByNotNull(): Sequence<List<T>> = sequence {
    var grouping = mutableListOf<T>()
    for (item in iterator()) {
        if (item != null) {
            grouping.add(item)
        } else {
            yield(grouping)
            grouping = mutableListOf()
        }
    }
    yield(grouping)
}

fun <T> Sequence<T>.chunkByPredicate(predicate: (T) -> Boolean): Sequence<List<T>> = sequence {
    var grouping = mutableListOf<T>()
    for (item in iterator()) {
        if (predicate(item)) {
            grouping.add(item)
        } else {
            yield(grouping)
            grouping = mutableListOf()
        }
    }
    yield(grouping)
}