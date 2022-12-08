fun main() {
    fun part1(input: List<String>): Int {
        return (0 until input.first().length).sumOf { x ->
            (input.indices).count { y ->
                input.isVisible(x, y)
            }
        }
    }

    fun part2(input: List<String>): Int {
        input.scoreViewSouth(1,1)
        return (0 until input.first().length).maxOf { x ->
            (input.indices).maxOf { y ->
                input.scoreView(x, y)
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val part1Result = part1(testInput)
    check(part1Result == 21)
    val part2Result = part2(testInput)
    println(part2Result)
    check(part2Result == 8)

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
        this[it][y] >= this[x][y]
    }
}

fun List<String>.isVisibleSouth(x: Int, y: Int): Boolean {
    if (x == this.size - 1) return true
    return ((this.size - 1) downTo x+1).none {
        this[it][y] >= this[x][y]
    }
}

fun List<String>.isVisibleWest(x: Int, y: Int): Boolean {
    if (y == 0) return true
    return (0 until y).none {
        this[x][it] >= this[x][y]
    }
}

fun List<String>.isVisibleEast(x: Int, y: Int): Boolean {
    if (y == this.first().length - 1) return true
    return ((this.first().length - 1) downTo y+1).none {
        this[x][it] >= this[x][y]
    }
}

fun List<String>.scoreView(x: Int, y: Int): Int {
    val n = scoreViewNorth(x, y)
    val s = scoreViewSouth(x, y)
    val e = scoreViewEast(x, y)
    val w = scoreViewWest(x, y)
    return n * s * e * w
}

fun List<String>.scoreViewNorth(x: Int, y: Int): Int {
    if (x == 0) return 0
    var score = 0
    run loop@{
        (x - 1 downTo 0).forEach {
            score += 1
            if (this[it][y] >= this[x][y]) {
                return@loop
            }
        }
    }
    return score
}

fun List<String>.scoreViewSouth(x: Int, y: Int): Int {
    if (x == this.size - 1) return 0
    var score = 0
    run loop@ {
        (x+1 until this.size).forEach {
            score += 1
            if (this[it][y] >= this[x][y]) {
                return@loop
            }
        }
    }
    return score
}

fun List<String>.scoreViewWest(x: Int, y: Int): Int {
    if (y == 0) return 0
    var score = 0
    run loop@{
        (y - 1 downTo 0).forEach {
            score += 1
            if (this[x][it] >= this[x][y]) {
                return@loop
            }
        }
    }
    return score
}

fun List<String>.scoreViewEast(x: Int, y: Int): Int {
    if (y == this.first().length - 1) return 0
    var score = 0
    run loop@{
        (y + 1 until this.first().length).forEach {
            score += 1
            if (this[x][it] >= this[x][y]) {
                return@loop
            }
        }
    }
    return score
}