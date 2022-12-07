
fun main() {
    fun part1(input: List<String>): Int {
        return input.count {
            val (elf1, elf2) = it.toSectionAssignmentPairs()
            elf2.within(elf1) || elf1.within(elf2)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count {
            val (elf1, elf2) = it.toSectionAssignmentPairs()
            elf1.overlaps(elf2)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val part1Result = part1(testInput)
    check(part1Result == 2)
    val part2Resut = part2(testInput)
    check(part2Resut == 3)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private fun String.toSectionAssignmentPairs(): Pair<IntRange, IntRange> {
    val assignments = this.split(',')
    val firstAssignment = assignments[0].toSectionAssignment()
    val secondAssignment = assignments[1].toSectionAssignment()
    return firstAssignment to secondAssignment
}

private fun String.toSectionAssignment(): IntRange {
    val rangeEnds = this.split('-').map { it.toInt() }
    return IntRange(rangeEnds[0], rangeEnds[1])
}