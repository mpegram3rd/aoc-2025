import java.io.File
import java.nio.charset.Charset

fun main() {
    val garbIn = File("puzzle-input.txt")
    val rangeStr = garbIn.readText(Charset.defaultCharset())
    val ranges = extractRanges(rangeStr)
    val solutionOne = calculateSolutionOne(ranges)
    println("Solution One: $solutionOne")

}

// Input parser
fun extractRanges(rangeStr: String): List<LongRange> {
    return rangeStr.split(",").map {
        val bounds = it.split("-").map { it.toLong() }
        bounds[0]..bounds[1]
    }
}

// Find all numbers in the ranges that have exactly 2 repeating sequences
fun calculateSolutionOne(ranges: List<LongRange>): Long {
    var tally: Long = 0

    // gonna just brute force this initially though I think a more clever
    // approach might be working with powers of 10.
    for (range in ranges) {
        for (num in range) {
            val numStr = num.toString()
            // Only worry about values that are even length so
            if (numStr.length % 2 != 0) {
                continue
            }
            val left = numStr.take(numStr.length / 2)
            val right = numStr.substring(numStr.length / 2)
            tally += if (left == right) num else 0
        }
    }

    return tally
}