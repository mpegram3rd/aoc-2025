import java.io.File

fun main() {

    val lines: List<String> = File("puzzle-input.txt").readLines()
    val grid: Array<CharArray> = lines.map { line ->
        line.toCharArray()
    }.toTypedArray()

    println("Solution One: ${solutionOne(grid)}")
}

// Visits each spot in the grid and checks for space.
fun solutionOne(grid: Array<CharArray>): Int {
    val gridHeight = grid.size
    val gridWidth = grid[0].size

    var y = 0
    var count = 0

    grid.forEach { rowData ->
        var x = 0
        rowData.forEach { data ->
            if (data == '@' && checkForSpace(x, y, grid, gridWidth, gridHeight))
                count++
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