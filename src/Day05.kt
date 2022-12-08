import java.util.Queue

fun main() {
    fun part1(input: List<String>): String {
        val cratesInput: List<String> = input.takeWhile { it != "" }
        val cratesOnly: List<String> = cratesInput.dropLast(1)
        val instructionsInput: List<String> = input.subList(cratesInput.size+1, input.size)

        val stacks: List<ArrayDeque<Char>> = cratesInput.last()
            .toList()
            .mapIndexed { index, c -> index to c }
            .mapNotNull { (index, c) ->
                c.digitToIntOrNull()?.let { index }
            }.map { crateColumPosition ->
                cratesOnly.mapNotNull {
                        crateRow -> crateRow.getOrNull(crateColumPosition).takeUnless { it == ' ' }
                }.toCollection(ArrayDeque())
            }

        instructionsInput.map {
            val words = it.split(' ')
            Instruction(
                count = words[1].toInt(),
                // Converting number to 0-indexing
                sourceIndex = words[3].toInt() - 1,
                destIndex = words[5].toInt() - 1,
            )
        }.map { it.run {
            repeat(count) {
                stacks[destIndex].addFirst(stacks[sourceIndex].removeFirst())
            }
        } }

        return stacks.map { it.first() }.toCharArray().concatToString()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val part1Result = part1(testInput)
    check(part1Result == "CMZ")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

data class Instruction(
    val count: Int,
    val sourceIndex: Int,
    val destIndex: Int,
)