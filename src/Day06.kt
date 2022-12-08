fun main() {
    fun part1(input: List<Char>): Int {
        return input.windowed(4).withIndex().first {
            it.value.toSet().size == 4
        }.index + 4
    }

    fun part2(input: List<Char>): Int {
        return input.windowed(14).withIndex().first {
            it.value.toSet().size == 14
        }.index + 14
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test").first().toCharArray().asList()
    val part1Result = part1(testInput)
    check(part1Result == 7)

    val input = readInput("Day06").first().toCharArray().asList()
    println(part1(input))
    println(part2(input))
}