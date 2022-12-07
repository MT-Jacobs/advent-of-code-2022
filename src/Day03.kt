fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val splitString: Pair<List<Char>, List<Char>> = it.halve()
            // since we converted to a set, technically we've destroyed some info about the comparments' contents
            val leftCompartment = splitString.first.toSet()
            val rightCompartment = splitString.second.toSet()
            val commonSet = leftCompartment.intersect(rightCompartment)
            assert(commonSet.size == 1) { "yo elf you lied to me about comparments having a single common val" }
            commonSet.first().priority()
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toSet() }
            .chunked(3)
            .sumOf {
                val first = it[0]
                val second = it[1]
                val third = it[2]
                val commonSet = first.intersect(second).intersect(third)
                assert(commonSet.size == 1) { "yo elf you lied to me about elves having a common val" }
                commonSet.first().priority()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val part1Result = part1(testInput)
    check(part1Result == 1 + 27)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private val priorityMap: List<Char> = ('a'..'z') + ('A'..'Z')
private fun Char.priority(): Int {
    val result = priorityMap.indexOf(this) + 1
    assert(result != 0) { "yo elf you didn't give me a char in your rucksack" }
    return result
}