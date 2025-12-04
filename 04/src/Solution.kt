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

    // check left and right
    adjacentRolls += if (x - 1 >= 0 && grid[y][x-1] == '@') 1 else 0
    adjacentRolls += if (x + 1 < gridWidth && grid[y][x+1] == '@') 1 else 0

    // check above left, center and right
    if (y - 1 >= 0) {
        val curY = y - 1 // just calc once
        adjacentRolls += if (x - 1 >= 0 && grid[curY][x-1] == '@') 1 else 0
        adjacentRolls += if (grid[curY][x] == '@') 1 else 0
        adjacentRolls += if (x + 1 < gridWidth && grid[curY][x+1] == '@') 1 else 0
    }

    // check below left, center and right
    if (y + 1 < gridHeight) {
        val curY = y + 1 // just calc once
        adjacentRolls += if (x - 1 >= 0 && grid[curY][x-1] == '@') 1 else 0
        adjacentRolls += if (grid[curY][x] == '@') 1 else 0
        adjacentRolls += if (x + 1 < gridWidth && grid[curY][x+1] == '@') 1 else 0
    }
    return adjacentRolls < 4
}