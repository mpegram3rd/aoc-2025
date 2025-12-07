import java.io.File

fun main() {

    val filename = "puzzle-input.txt"

    println("Solution One: ${solutionOne(filename)}")
    println("Solution Two: ${solutionTwo(filename)}")

}

// Handle logic for challenge 1
fun solutionOne(filename: String): Int {

    val dataGrid = readGrid(filename)
    val splits = fireTachyons(dataGrid)

    return splits
}

// Handle logic for challenge 2
fun solutionTwo(filename: String): Int {
    val dataGrid = readGrid(filename)

    return 0
}

// Read the grid in from the filesystem
fun readGrid(filename: String): Array<CharArray> {
    val lines: List<String> = File(filename).readLines()

    val dataGrid: Array<CharArray> = lines.map { line ->
        line.toCharArray()
    }.toTypedArray()

    return dataGrid
}

// Yes, mutating the dataGrid in place feels a bit icky
// this is basically the first solution's logic
fun fireTachyons(dataGrid: Array<CharArray>): Int{
    var splits = 0
    dataGrid.forEachIndexed { yIndex, row ->
        row.forEachIndexed { xIndex, symbol ->
            splits += if (processLocation(symbol, xIndex, yIndex, dataGrid)) 1 else 0
        }
    }
    return splits
}

fun processLocation(symbol: Char, xIndex: Int, yIndex: Int, dataGrid: Array<CharArray>): Boolean {

    var split = false

    when (symbol) {
        'S' -> handleStartBeam(xIndex, yIndex, dataGrid)
        '|' -> handleBeam(xIndex, yIndex, dataGrid)
        '^' -> split = handleSplit(xIndex, yIndex, dataGrid)
    }
    return split
}

// Not sure yet if part 2 is going to throw a curveball that wrecks the assumption this gets handled like any other beam
fun handleStartBeam(xIndex: Int, yIndex: Int, dataGrid: Array<CharArray>) {
    handleBeam(xIndex, yIndex, dataGrid)
}

fun handleBeam(xIndex: Int, yIndex: Int, dataGrid: Array<CharArray>) {
    // Check to make sure we are in-bounds before adding a beam
    if (yIndex + 1 < dataGrid.size && dataGrid[yIndex + 1][xIndex] == '.') {
        dataGrid[yIndex + 1][xIndex] = '|'
    }
}

// Returns TRUE if the beam was split.
fun handleSplit(xIndex: Int, yIndex: Int, dataGrid: Array<CharArray>): Boolean {
    // Check to see if a beam is coming in from above
    if (yIndex - 1 >= 0 && dataGrid[yIndex - 1][xIndex] == '|') {
        if (xIndex -1 >= 0) {
            dataGrid[yIndex][xIndex-1] = '|'
            // since we've already passed processing this new beam we'll need to step backwards briefly and process it
            processLocation('|', xIndex -1, yIndex, dataGrid)
        }
        // This doesn't require special handling because it will be processed on the next iteration
        if (xIndex + 1 < dataGrid[yIndex].size) {
            dataGrid[yIndex][xIndex + 1] = '|'
        }
        return true
    }

    // No incoming beam so we have no reason to split
    return false
}

// Output the grid's current state
fun displayGrid(dataGrid: Array<CharArray>) {
    // Dump results
    dataGrid.forEach { row ->
        println()
        row.forEach { symbol ->
            print(symbol)
        }
    }
    println()
}