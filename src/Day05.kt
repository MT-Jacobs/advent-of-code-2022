import java.util.Queue

fun main() {
    fun part1(input: List<String>): String {
        return getJob(input).run {
            instructions.map { it.run {
                repeat(count) {
                    stacks[destIndex].addFirst(stacks[sourceIndex].removeFirst())
                }
            } }
            stacks.map { it.first() }.toCharArray().concatToString()
        }
    }

    fun part2(input: List<String>): String {
        return getJob(input).run {
            instructions.map { it.run {
                val liftedStack: List<Char> = sequence {
                    repeat(count) {
                        yield(stacks[sourceIndex].removeFirst())
                    }
                }.toList().reversed()
                stacks[destIndex].addAll(liftedStack)
                liftedStack.forEach { box ->
                    stacks[destIndex].addFirst(box)
                }
            } }

            stacks.map { it.first() }.toCharArray().concatToString()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val part1Result = part1(testInput)
    check(part1Result == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun getJob(input: List<String>): JobSetup {
    val cratesInput: List<String> = input.takeWhile { it != "" }
    val instructionsInput: List<String> = input.subList(cratesInput.size+1, input.size)

    val stacks: List<ArrayDeque<Char>> = cratesInput.toCrateStacks()
    val instructions: List<Instruction> = instructionsInput.map {
        val words = it.split(' ')
        Instruction(
            count = words[1].toInt(),
            // Converting number to 0-indexing
            sourceIndex = words[3].toInt() - 1,
            destIndex = words[5].toInt() - 1,
        )
    }

    return JobSetup(stacks, instructions)
}

data class JobSetup(
    val stacks: List<ArrayDeque<Char>>,
    val instructions: List<Instruction>,
)
data class Instruction(
    val count: Int,
    val sourceIndex: Int,
    val destIndex: Int,
)

fun List<String>.toCrateStacks(): List<ArrayDeque<Char>> {
    val cratesOnly: List<String> = this.dropLast(1)
    return this.last()
        .toList()
        .mapIndexed { index, c -> index to c }
        .mapNotNull { (index, c) ->
            c.digitToIntOrNull()?.let { index }
        }.map { crateColumPosition ->
            cratesOnly.mapNotNull {
                    crateRow -> crateRow.getOrNull(crateColumPosition).takeUnless { it == ' ' }
            }.toCollection(ArrayDeque())
        }
}