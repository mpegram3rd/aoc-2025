import java.io.File

fun main() {
    var totalJoltage = 0

    val garbIn = File("puzzle-input.txt")
    // Parse file line by line
    garbIn.forEachLine { batteryBank ->
        totalJoltage += solutionOne(batteryBank)
    }

    println("Solution 1 - Total Joltage: $totalJoltage")
}

// Finds two largest numbers it can reading from left to right
fun solutionOne(batteryBank: String): Int {
    val cells = IntArray(2)

    print("Input: $batteryBank")

    // The final digit is a special case.  If it happens to be larger than the first digit
    // we should NOT slide the cells because there will be no other digit to fill in the new open slot.
    // This is a small optimization to keep from recalculating this value unnecessarily.
    val endIndex = batteryBank.length - 1

    // Walk through the battery bank one battery cell at a time
    batteryBank.forEachIndexed { index, character ->
        val value = character.toString().toInt()

        // If we find a value that is larger than what we have in the right hand digit, replace it.
        if (value > cells[1]) {
            cells[1] = value
        }

        // If the right hand cell value is larger than the left hand cell value, AND (importantly) not the last cell
        // Then we have an opportunity to increase the joltage value by sliding the larger value into the left hand cell
        // to make room for other values in the right hand cell.
        if (cells[1] > cells[0] && index < endIndex) {
            slideLeft(cells, 0)
        }
    }

    // Convert our 2 values into a "Joltage" value
    var joltage = 0
    for (cell in cells) {
        joltage = (joltage * 10) + cell
    }

    println(" Joltage: $joltage")
    return joltage
}

// Slides the values in the array to the left and inserts the new value at the end
fun slideLeft(cells: IntArray, newValue: Int) {
    cells.forEachIndexed { index, _ ->
        if (index < cells.size - 1) {
            cells[index] = cells[index + 1]
        }
        else {
            cells[index] = newValue
        }
    }
}
