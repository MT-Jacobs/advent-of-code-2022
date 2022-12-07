fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("DayXX_test")
    val part1Result = part1(testInput)
    check(part1Result == 15)

    val input = readInput("DayXX")
    println(part1(input))
    println(part2(input))
}