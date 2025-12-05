/**
 * Represents a range of numbers and provides several helpful methods to create
 * Longeractions between this range and other ranges.
 */
data class Range (var start: Long, var end: Long): Comparable<Range> {

    /**
     * Checks to see if the provided range overlaps in any way with this range
     */
    fun hasOverlap(range: Range): Boolean {
        // check for overlap with the front of the range
        var overlapping = (range.start in start..end)

        // if not found, check for overlap at the backend of the range
        overlapping = (!overlapping && range.end in start .. end)

        // Finally see if this range fits inside the provided range
        overlapping = (!overlapping && range.start <= start && range.end >= end)

        return overlapping
    }

    /**
     * Expands this range as needed to merge it with the
     */
    fun expand(range: Range)  {
        if (!hasOverlap(range))
            return

        if (start > range.start)
            start = range.start

        if (end < range.end)
            end = range.end
    }

    /**
     * Checks to see if the provided value is in the range
     */
    fun inRange(value: Long): Boolean {
        return value in start..end
    }

    /**
     * A function for sorting ranges
     */
    override fun compareTo(other: Range): Int {
        return compareValues(start, other.start)
    }

}