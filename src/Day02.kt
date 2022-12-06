import Shape.*
import GameState.*

fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            it[0].toShape() to it[2].toShape()
        }.sumOf { (theirShape, myShape) ->
            myShape.evaluateAgainst(theirShape).score + myShape.score
        }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            it[0].toShape() to it[2].toStrategy()
        }.sumOf { (theirShape, myStrategy) ->
            val myShape: Shape = theirShape.chooseStrategy(myStrategy)
            myStrategy.score + myShape.score
        }
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

enum class GameState(val score: Int)  {
    LOSE(0), DRAW(3), WIN(6)
}

fun Char.toShape(): Shape = when(this) {
    'A', 'X' -> ROCK
    'B', 'Y' -> PAPER
    'C', 'Z' -> SCISSORS
    else -> throw RuntimeException("Bad letter.")
}

fun Char.toStrategy(): GameState = when(this) {
    'X' -> LOSE
    'Y' -> DRAW
    'Z' -> WIN
    else -> throw RuntimeException("Bad letter.")
}

fun Shape.evaluateAgainst(otherShape: Shape): GameState =
    when (this) {
        ROCK -> {
            when (otherShape) {
                ROCK -> DRAW
                PAPER -> LOSE
                SCISSORS -> WIN
            }
        }
        PAPER -> {
            when (otherShape) {
                ROCK -> WIN
                PAPER -> DRAW
                SCISSORS -> LOSE
            }
        }
        SCISSORS -> {
            when (otherShape) {
                ROCK -> LOSE
                PAPER -> WIN
                SCISSORS -> DRAW
            }
        }
    }

fun Shape.chooseStrategy(strategy: GameState): Shape =
    when (this) {
        ROCK -> {
            when (strategy) {
                WIN -> PAPER
                DRAW -> ROCK
                LOSE -> SCISSORS
            }
        }
        PAPER -> {
            when (strategy) {
                WIN -> SCISSORS
                DRAW -> PAPER
                LOSE -> ROCK
            }
        }
        SCISSORS -> {
            when (strategy) {
                WIN -> ROCK
                DRAW -> SCISSORS
                LOSE -> PAPER
            }
        }
    }