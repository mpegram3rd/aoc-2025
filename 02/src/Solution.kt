import java.io.File
import java.nio.charset.Charset

fun main() {
    val garbIn = File("puzzle-input.txt")
    val rangeStr = garbIn.readText(Charset.defaultCharset())
    val ranges = extractRanges(rangeStr)

    val solutionOne = calculateSolutionOne(ranges)
    println("Solution One: $solutionOne")

    val solutionTwo = calculateSolutionTwo(ranges)
    println("Solution Two: $solutionTwo")
}

// Input parser
fun extractRanges(rangeStr: String): List<LongRange> {
    return rangeStr.split(",").map { rangeStr ->
        val bounds = rangeStr.split("-").map { rangeStr.toLong() }
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

// Part 2, look for numbers that have any number of repeating sequences
fun calculateSolutionTwo(ranges: List<LongRange>): Long {
    var tally: Long = 0

    // Another case of brute forcing it (for now).
    for (range in ranges) {
        for (num in range) {
            val numStr = num.toString()

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
                // If we make it here we have a repeating pattern so add it to the tally
                if (right.isEmpty()) {
                    tally += num
                    break
                }
            }
        }
    }
    return tally
}

