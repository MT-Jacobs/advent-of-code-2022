fun main() {
    fun part1(input: List<String>): Int {
        val system: Filesystem = traverseInfo(input)
        return system.directories.map { it.dataSize }.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val part1Result = part1(testInput)
    println(part1Result)
    check(part1Result == 95437)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

fun traverseInfo(input: List<String>): Filesystem {

    val root = FilesystemObject.Root()
    val objects: MutableList<FilesystemObject> = mutableListOf()
    val directories: MutableList<FilesystemObject.Directory> = mutableListOf()
    val files: MutableList<FilesystemObject.File> = mutableListOf()
    var currentDirectory: FilesystemObject.Directory = root
    input.forEach{
        val command: CLIInfo = it.getCLIInfo()
        println(command)
        when(command) {
            is CLIInfo.CD -> {
                currentDirectory = if (command.name == "/") {
                    root
                } else {
                    currentDirectory.cd(command.name)
                }
            }
            is CLIInfo.DirInfo -> {
                if (currentDirectory[command.name] != null) {
                    // Dir already exists.
                } else {
                    // Dir doesn't exist, add it.
                    val dir = FilesystemObject.Directory(command.name, currentDirectory)

                    currentDirectory[command.name] = dir
                    directories.add(dir)
                    objects.add(dir)
                }
            }
            is CLIInfo.FileInfo -> {
                if (currentDirectory[command.name] != null) {
                    // File already exists.
                } else {
                    // File doesn't exist, add it.
                    val file = FilesystemObject.File(command.name, currentDirectory, command.size)

                    currentDirectory[command.name] = file
                    files.add(file)
                    objects.add(file)
                }
            }
            CLIInfo.LS -> {
                // Nothing to do, it's just a command to start listing stuff.
            }
        }
    }
    return Filesystem(
        root = root,
        objects = objects,
        directories = directories,
        files = files,
    )
}

fun String.getCLIInfo(): CLIInfo {
    val tokens = this.split(' ')
    return when (tokens[0]) {
        "$" -> when (tokens[1]) {
            "cd" -> CLIInfo.CD(tokens[2])
            "ls" -> CLIInfo.LS
            else -> throw RuntimeException("Elf gave me an invalid command")
        }
        "dir" -> CLIInfo.DirInfo(tokens[1])
        else -> {
            val size = tokens[0].toIntOrNull() ?:
                throw RuntimeException("Elf didn't give me a valid number for file size.")
            CLIInfo.FileInfo(tokens[1], size)
        }
    }
}


class Filesystem(
    val root: FilesystemObject.Root,
    val objects: List<FilesystemObject> = emptyList(),
    val directories: List<FilesystemObject.Directory> = emptyList(),
    val files: List<FilesystemObject.File> = emptyList(),
)

sealed interface CLIInfo {
    data class CD(val name: String): CLIInfo

    object LS: CLIInfo {
        override fun toString(): String = "LS()"
    }

    data class FileInfo(
        val name: String,
        val size: Int
    ): CLIInfo

    data class DirInfo(
        val name: String,
    ): CLIInfo
}

enum class FilesystemObjectType {
    DIRECTORY, FILE
}

sealed class FilesystemObject(
    val parent: Directory?,
    val name: String
) {
    abstract val dataSize: Int

    open class Directory(
        name: String,
        parent: Directory? = null,
        private val contents: MutableMap<String, FilesystemObject> = mutableMapOf(),
    ): FilesystemObject(parent, name), MutableMap<String, FilesystemObject> by contents {
        override val dataSize: Int
            get() = contents.values.sumOf { it.dataSize }

        fun cd(name: String): FilesystemObject.Directory {
            return if (name == "..") {
                this.parent ?: throw RuntimeException("Can't cd .. without a parent")
            } else {
                (contents.getOrPut(name) {
                    Directory(name, this)
                } as? Directory) ?: throw RuntimeException("Tried to cd to something that isn't a directory.")
            }
        }
    }

    class Root: Directory("/")

    class File(
        name: String,
        parent: Directory,
        override val dataSize: Int,
    ): FilesystemObject(parent, name)
}