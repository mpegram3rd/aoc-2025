import java.io.File

fun main() {
    var totalJoltage1:Long = 0
    var totalJoltage2:Long = 0

    val garbIn = File("puzzle-input.txt")
    // Parse file line by line
    garbIn.forEachLine { batteryBank ->
        totalJoltage1 += solution(batteryBank, 2)
        totalJoltage2 += solution(batteryBank, 12)
    }

    println("Solution 1 - Total Joltage: $totalJoltage1")
    println("Solution 2 - Total Joltage: $totalJoltage2")
}

// Generalized solution that can calculate joltage for an arbitrary number of cells in the battery bank
fun solution(batteryBank: String, maxCells: Int): Long {
    val cells = IntArray(maxCells)

    // Which battery cell are we currently filling
    var currentCellIndex = 0

    // How many cell slots to the right of the current cell are available to be filled
    var cellsRemaining = maxCells - 1

    // How many digits do we have left to work with
    var digitsRemaining = batteryBank.length

    // Walk through the battery bank one battery cell at a time
    batteryBank.forEach { numChar ->

        digitsRemaining--
        val value = numChar.toString().toInt()

        // If the value is less than or equal to what is in the current cell, and there's more cells to fill
        // just drop that value in the current cell and move on to the next one.
        if (value <= cells[currentCellIndex] && cellsRemaining > 0) {
            currentCellIndex++
            cellsRemaining--
        }

        // If the value is greater than that of the current cell drop the value into the current cell
        if (value > cells[currentCellIndex]) {

            // Drop the new value into the current position
            cells[currentCellIndex] = value

            // If the new value is > than it's neighbor to the left, we may be able to shift it left
            // This tries to figure out how far left we can shift the value (the answer might be 0)
            val howMuchShift = calculateShift(value, cells, currentCellIndex, digitsRemaining)

            // Shift the cells as much as we can and make sure the new currentCellIndex points to the next cell to work
            currentCellIndex = shiftCells(cells, currentCellIndex, howMuchShift)
            cellsRemaining = cells.size - currentCellIndex - 1
        }
    }

    return calculateTotalJoltage(cells)
}

// Converts the array of cells into a single joltage value
fun calculateTotalJoltage(cells: IntArray): Long {
    var joltage:Long = 0
    for (cell in cells) {
        joltage = (joltage * 10) + cell
    }
    return joltage
}

// How many positions to the left can we shift the value where it is greater than the value in that slot.
// The tricky part is we can't shift it too far left where we leave empty slots that can't be filled by the
// remaining digits.
fun calculateShift(value: Int, cells: IntArray, currentIndex: Int, digitsRemaining: Int): Int {

    // Tally how many slots we can shift
    var shiftCount = 0

    // How many unfilled cells are available to the right of the current index
    val availableCells = cells.size - currentIndex - 1

    // We can't shift left more than the number of remaining digits so figure ou the maximum available shifts
    // Note: this value could be 0 or fewer (which will cause the below loop to be skipped)
    var availableShifts = digitsRemaining - availableCells
    var index = currentIndex - 1

    // As long as we still have available shifts, and we haven't reached the front of the cell bank, keep working
    while (availableShifts > 0 && index >= 0) {

        // If the value is greater than what is at the current location, plan for another shift
        if (value > cells[index]) {
            shiftCount++
            availableShifts--
        }
        index--
    }

    return shiftCount
}

// Shifts the value at the current index left by howMuchShift positions and fills vacated slots with 0
fun shiftCells(cells: IntArray, currentIndex: Int, howMuchShift: Int): Int {

    val newIndex = currentIndex - howMuchShift
    cells[newIndex] = cells[currentIndex]

    // Flush out vacated cells
    for (i in currentIndex downTo newIndex + 1) {
        cells[i] = 0
    }

    // Move the current index pointer to the next available cell
    return if (newIndex + 1 < cells.size) newIndex + 1 else newIndex
}
