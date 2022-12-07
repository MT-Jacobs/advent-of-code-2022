import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <T> Sequence<T?>.chunkByNotNull(): Sequence<List<T>> = sequence {
    var grouping = mutableListOf<T>()
    for (item in iterator()) {
        if (item != null) {
            grouping.add(item)
        } else {
            yield(grouping)
            grouping = mutableListOf()
        }
    }
    yield(grouping)
}

fun <T> Sequence<T>.chunkByPredicate(predicate: (T) -> Boolean): Sequence<List<T>> = sequence {
    var grouping = mutableListOf<T>()
    for (item in iterator()) {
        if (predicate(item)) {
            grouping.add(item)
        } else {
            yield(grouping)
            grouping = mutableListOf()
        }
    }
    yield(grouping)
}

fun String.halve(): Pair<List<Char>, List<Char>> {
    val splitString = this.toList().chunked(this.length / 2)
    return splitString[0] to splitString[1]
}

fun IntRange.within(otherRange: IntRange): Boolean =
    otherRange.first <= this.first && otherRange.last >= this.last

fun IntRange.overlaps(otherRange: IntRange): Boolean =
    this.first <= otherRange.last && this.last >= otherRange.first