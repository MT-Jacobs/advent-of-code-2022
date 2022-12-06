import Shape.*

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it[0].toShape() to it[2].toShape() }.sumOf {
            it.second.scoreAgainst(it.first) + it.second.score
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val part1Result = part1(testInput)
    check(part1Result == 15)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class Shape(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3)
}

fun Char.toShape(): Shape = when(this) {
    'A', 'X' -> ROCK
    'B', 'Y' -> PAPER
    'C', 'Z' -> SCISSORS
    else -> throw RuntimeException("Bad letter.")
}

fun Shape.scoreAgainst(otherShape: Shape): Int =
    when (this) {
        ROCK -> {
            when (otherShape) {
                ROCK -> 3
                PAPER -> 0
                SCISSORS -> 6
            }
        }
        PAPER -> {
            when (otherShape) {
                ROCK -> 6
                PAPER -> 3
                SCISSORS -> 0
            }
        }
        SCISSORS -> {
            when (otherShape) {
                ROCK -> 0
                PAPER -> 6
                SCISSORS -> 3
            }
        }
    }