import java.io.File

fun main() {

    val garbIn = File("puzzle-input.txt")

    var readingRanges = true
    var ranges = mutableListOf<Range>()
    val indexes = mutableListOf<Long>()

    // Process the input file
    garbIn.forEachLine { line ->
        if (line.isEmpty()) {
            readingRanges = false
            ranges = consolidateRanges(ranges).toMutableList()
            ranges.sort()
        }
        else
            if (readingRanges) {
                val bounds = line.split("-").map { it.toLong() }
                ranges.add(Range(bounds[0], bounds[1]))
            }
            else {
                indexes.add(line.toLong())
            }
    }

    println("Solution One: ${solutionOne(ranges, indexes)}")
    println("Solution Two: ${solutionTwo(ranges)}")

}

// Logic for solution one is to see if the value is in one of the ranges.  If so it is safe
fun solutionOne(ranges: List<Range>, indexes: List<Long>): Long {
    return indexes.fold(0) { acc, index ->
        if (checkSafety(ranges, index)) acc + 1 else acc
    }
}

// Figure out how many GOOD indexes we have
fun solutionTwo(ranges: List<Range>): Long {
    return ranges.fold(0) { acc, range ->
        acc + range.size()
    }
}

// Checks to see if the index value is in one of the ranges
fun checkSafety(ranges: List<Range>, index: Long): Boolean {
    ranges.forEach{ range ->
        if (range.inRange(index)) {
            return true
        }

    }

    return false
}

// This goes through and merges the ranges expanding and trimming where necessary
fun consolidateRanges(ranges: List<Range>): List<Range> {

    var changesMade = false
    val sortedList = ranges.sorted()
    sortedList.forEachIndexed { index, range ->
        // see if we can fold the next range into the current one
        if (index + 1 < ranges.size) {
            val nextRange = sortedList[index + 1]
            if (range.hasOverlap(nextRange)) {
                range.expand(range)

                // now the next range has to shrink to eliminate the part we absorbed
                // Note this could create an invalid "range" values which  we will scrub before returning
                if (nextRange.start <= range.end)
                    nextRange.start = range.end + 1
                changesMade = true
            }
        }
    }

    // Eliminate the shrunken lists that have been fully consumed
    var newList = ranges.filter { range -> range.start <= range.end }

    // If there were consolidations we might still need to make more
    if (changesMade)
        newList = consolidateRanges(newList)

    return newList
}

