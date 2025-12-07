import java.io.File

fun main() {

    val lines: List<String> = File("puzzle-input.txt").readLines()

    val dataGrid: Array<CharArray> = lines.map { line ->
        line.toCharArray()
    }.toTypedArray()

    println("Solution One: ${solutionOne(dataGrid)}")
    println("Solution Two: ${solutionTwo(dataGrid)}")

}

fun solutionOne(dataGrid: Array<CharArray>): Int {
    return 0
}

fun solutionTwo(dataGrid: Array<CharArray>): Int {
    return 0
}