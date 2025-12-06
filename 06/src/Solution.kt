import java.io.File

val add: (Long, Long) -> Long = { a, b -> a + b }
val multiply: (Long, Long) -> Long = { a, b -> a * b }

fun main() {

    val dataFile = "puzzle-input.txt"

    println("Solution One: ${solutionOne(dataFile)}")
    println("Solution Two: ${solutionTwo(dataFile)}")

}

fun solutionOne(filename: String): Long {
    val problems = mutableListOf<MathProblem>()
    val garbIn = File(filename)
    garbIn.forEachLine { line ->
        // Note.. the split produces empty strings so need to filter those
        val components = line.trim().split(" ").filter { it != "" }

        if (components[0] == "+" || components[0] == "*") {
            parseOperators(problems, components)
        }
        else {
            parseNumbers(problems, components)
        }
    }

    var tally: Long = 0
    problems.forEach { problem ->
        tally += problem.runCalculation()
    }
    return tally
}

fun solutionTwo(filename: String): Long {
    val lines: List<String> = File(filename).readLines()

    val maxWidth = lines.maxOf { it.length }

    // We need to be able to read this in columnar order so we'll convert the data into a 2d grid.
    val dataGrid: Array<CharArray> = lines.map { line ->
        // pad grid if line length doesn't match max length
        if (line.length < maxWidth) {
            var newLine = line
            for (x in 1..(maxWidth - line.length)) {
                newLine += ' '
            }
            newLine.toCharArray()
        }
        else
            line.toCharArray()
    }.toTypedArray()

    val maxX = dataGrid[0].size - 1
    val maxY = dataGrid.size - 1
    var tally: Long = 0

    var problem = MathProblem()

    // Problem says work right to left
    for (xIndex in maxX  downTo 0) {
        var num:Long = 0
        for (yIndex in 0..< maxY) {
            val char = dataGrid[yIndex][xIndex]
            if (char in '0'..'9') {
                num = num * 10 + (dataGrid[yIndex][xIndex].code - '0'.code)
            }
        }
        val lastChar = dataGrid[maxY][xIndex]
        // See if we accumulated a number but haven't reached the end of processing the math problem.
        if (num > 0) {
            problem.include(num)
        }
        // If there's an operator that is the end of the problem
        if (lastChar == '+' || lastChar == '*') {
            problem.calculation = if ('+' == lastChar) add else multiply
            tally += problem.runCalculation()
            problem = MathProblem()
        }

    }

    return tally
}

fun parseOperators(problems: MutableList<MathProblem>, operators: List<String>) {
    operators.forEachIndexed { index, value ->
        problems[index].calculation = if ("+" == value) add else multiply
    }
}

fun parseNumbers(problems: MutableList<MathProblem>, components: List<String>) {
    components.forEachIndexed { index, value ->
        val num = value.toLong()
        if (problems.size < components.size) {
            val problem = MathProblem()
            problems.add(problem)
            problem.include(num)
        }
        else {
            problems[index].include(num)
        }
    }
}

