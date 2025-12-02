import java.io.File
import java.nio.charset.Charset

fun main() {
    val garbIn = File("puzzle-input.txt")
    val rangeStr = garbIn.readText(Charset.defaultCharset())
    val ranges = extractRanges(rangeStr)
    processRanges(ranges)
}

// Input parser
fun extractRanges(rangeStr: String): List<LongRange> {
    return rangeStr.split(",").map { rangeStr ->
        val bounds = rangeStr.split("-").map { it.toLong() }
        bounds[0]..bounds[1]
    }
}

// Refactored to make processing the ranges for both solutions a single pass
fun processRanges(ranges: List<LongRange>) {
    var sol1Tally: Long = 0
    var sol2Tally: Long = 0

    for (range in ranges) {
        for (num in range) {
            val numStr = num.toString()
            sol1Tally += if (calculateSolutionOne(numStr)) num else 0
            sol2Tally += if (calculateSolutionTwo(numStr)) num else 0
        }
    }
    println("Solution One: $sol1Tally")
    println("Solution Two: $sol2Tally")
}

// Find all numbers in the ranges that have exactly 2 repeating sequences
fun calculateSolutionOne(numStr: String): Boolean {

    // Only worry about values that are even length
    if (numStr.length % 2 != 0) {
        return false
    }

    // Split the string in half and compare
    val left = numStr.take(numStr.length / 2)
    val right = numStr.substring(numStr.length / 2)

    // check if both halves match
    return left == right
}

// Part 2, look for numbers that have any number of repeating sequences
// This is a bit more brute force than I'd like but has some minor optimizations
fun calculateSolutionTwo(numStr: String): Boolean {

    // We only need to check up to half the length of the string
    // Anything longer can't be a repeating pattern
    val maxIndex = (numStr.length / 2)
    for (i in 1..maxIndex) {

        // Snag the prefix
        val left = numStr.take(i)

        // Now get the rest of the string which we'll chip away at
        // by seeing if it starts with the prefix
        var right = numStr.substring(i)

        // Iterate over the right side, matching the left side in chunks
        // If we can fully consume the right side, we have a match
        while (right.isNotEmpty()) {

            // Check if the left side is a prefix of the remaining string
            if (left.length <= right.length && right.startsWith(left)) {
                // Chop off the prefix leaving the rest of the string.
                right = right.substring(left.length)
            } else {
                // If the prefix can't be matched no point continuing to look for pattern
               break
            }
        }
        // If we make it here we have a repeating pattern so add it to the tally and return immediately
        if (right.isEmpty()) {
            return true
        }
    }
    return false
}

