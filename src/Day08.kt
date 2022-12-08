fun main() {
    fun part1(input: List<String>): Int {
        return (0 until input.first().length).sumOf { x ->
            val topResult = (input.indices).count { y ->
                println("$x, $y: ${input[x][y]}")
                val result = input.isVisible(x, y)
                val str = if (result) "y" else "n"
                println(str)
                result
            }
            println()
            topResult
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val part1Result = part1(testInput)
    check(part1Result == 21)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun List<String>.isVisible(x: Int, y: Int): Boolean {
    return isVisibleNorth(x, y) || isVisibleSouth(x, y) || isVisibleEast(x, y) || isVisibleWest(x, y)
}

fun List<String>.isVisibleNorth(x: Int, y: Int): Boolean {
    if (x == 0) return true
    return (0 until x).none {
        println("${this[it][y]} >= ${this[x][y]}")
        this[it][y] >= this[x][y]
    }
}

fun List<String>.isVisibleSouth(x: Int, y: Int): Boolean {
    if (x == this.size - 1) return true
    return ((this.size - 1) downTo x+1).none {
        println("${this[it][y]} >= ${this[x][y]}")
        this[it][y] >= this[x][y]
    }
}

fun List<String>.isVisibleWest(x: Int, y: Int): Boolean {
    if (y == 0) return true
    return (0 until y).none {
        println("${this[x][it]} >= ${this[x][y]}")
        this[x][it] >= this[x][y]
    }
}

fun List<String>.isVisibleEast(x: Int, y: Int): Boolean {
    if (y == this.first().length - 1) return true
    return ((this.first().length - 1) downTo y+1).none {
        println("${this[x][it]} >= ${this[x][y]}")
        this[x][it] >= this[x][y]
    }
}