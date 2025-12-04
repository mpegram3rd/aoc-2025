import java.io.File

fun main() {

    val lines: List<String> = File("puzzle-input.txt").readLines()
    val grid: Array<CharArray> = lines.map { line ->
        line.toCharArray()
    }.toTypedArray()

    println("Solution One: ${solutionOne(grid)}")

    // Note: because solution 2 modifies the grid it always has to run after solution 1
    println("Solution Two: ${solutionTwo(grid)}")
}

// This does a simple pass through the grid but doesn't remove any rolls
fun solutionOne(grid: Array<CharArray>): Int {
    return processGrid(grid, false)
}

// This time we will remove the rolls and re-evaluate until no more rolls are removed.
fun solutionTwo(grid: Array<CharArray>): Int {
    var totalRolls = 0

    do {
        val removed = processGrid(grid, true)
        totalRolls += removed
    } while (removed > 0)
    return totalRolls
}

// Visits each spot in the grid and checks for space.
fun processGrid(grid: Array<CharArray>, removeRolls: Boolean): Int {
    val gridHeight = grid.size
    val gridWidth = grid[0].size

    var y = 0
    var count = 0

    grid.forEach { rowData ->
        var x = 0
        rowData.forEach { data ->
            if (data == '@' && checkForSpace(x, y, grid, gridWidth, gridHeight)) {
                count++

                // Mark the spot where we're going to remove the roll if the boolean is true.
                // This will make the space open and potentially allow for more rolls to be removed
                // if we re-process the grid. Downside.., it directly modifies data in the grid
                if (removeRolls) grid[y][x] = 'x'
            }
            x++
        }
        y++
    }
    return count
}

// Kind of brute force, but basically checks all 8 directions for adjacent rolls. Tallies those up and
// returns TRUE if it is less than the threshold, false otherwise.
fun checkForSpace(x: Int, y: Int, grid: Array<CharArray>, gridWidth: Int, gridHeight: Int): Boolean {
    var adjacentRolls = 0

    for (curY in y - 1 .. y + 1) {
        // check y bounds
        if (curY in 0..< gridHeight) {
            for (curX in x - 1 .. x + 1) {
                // Check x bounds and also make sure we're not sitting on the value we're evaluating
                if (curX in 0 ..<  gridWidth && !(curX == x && curY == y)) {
                    adjacentRolls += if (grid[curY][curX] == '@') 1 else 0
                }
            }
        }
    }

    return adjacentRolls < 4
}