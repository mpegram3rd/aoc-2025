import java.io.File

val add: (Long, Long) -> Long = { a, b -> a + b }
val multiply: (Long, Long) -> Long = { a, b -> a * b }

fun main() {

    val problems = mutableListOf<MathProblem>()

    val garbIn = File("puzzle-input.txt")
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

    println("Solution One: ${solutionOne(problems)}")
    println("Solution Two: ${solutionTwo()}")

}

fun solutionOne(problems: List<MathProblem>): Long {
    var tally: Long = 0
    problems.forEach { problem ->
        tally += problem.runCalculation()
    }
    return tally
}

fun solutionTwo(): Long {
    return 0
}

fun parseOperators(problems: MutableList<MathProblem>, operators: List<String>) {
    operators.forEachIndexed { index, value ->
        problems[index].calculation = if ("+".equals(value)) add else multiply
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

