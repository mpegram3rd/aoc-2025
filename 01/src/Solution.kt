import java.io.File
import kotlin.math.abs

const val STARTING_POSITION = 50
const val DIAL_SIZE = 100

fun main() {
    var passwordOne = 0
    var passwordTwo = 0
    var currentPosition = STARTING_POSITION
    val garbIn = File("./puzzle-input.txt")
    garbIn.forEachLine { line ->
        val direction = line.take(1)
        val clicks = line.substring(1).toInt()
        println("Starting at: $currentPosition : $line")
        val adjustmentValue = calculateAdjustment(direction, clicks)
        val newPosition = calculateNewPosition(currentPosition, adjustmentValue)
        passwordOne += calculateSolutionOne(newPosition)
        passwordTwo += calculateSolutionTwo(currentPosition, adjustmentValue)
        currentPosition = newPosition
    }

    println("Part One Password: $passwordOne")
    println("Part Two Password: $passwordTwo")
}

// How many clicks, in which direction do we need to land at our new spot
fun calculateAdjustment(direction: String, clicks: Int): Int {
    return if (direction.equals("L", ignoreCase = true)) clicks * -1 else clicks
}


// Where is the new position on the dial
fun calculateNewPosition(currentPosition: Int, adjustmentValue: Int) : Int {
    val relativePosition = currentPosition + adjustmentValue
    var newPosition  = relativePosition % DIAL_SIZE
    if (newPosition < 0) newPosition += DIAL_SIZE
    return newPosition
}

// Solution 1 is to count the number of times the move lands the dial on the number 0
fun calculateSolutionOne(newPosition: Int): Int {
    return if (newPosition == 0) 1 else 0
}

// Solution 2 is to count the number of times the dial would have passed the number 0 or landed on 0 exactly.
fun calculateSolutionTwo(currentPosition: Int, adjustmentValue: Int): Int {

    // How many full dial spins happen
    var passes = abs(adjustmentValue / DIAL_SIZE)

    // How many extra clicks are left
    val extraClicks = (adjustmentValue % DIAL_SIZE)

    val newRelPosition = currentPosition + extraClicks

    // When we move to the new position does it cause us to pass 0
    if (newRelPosition <= 0 && currentPosition != 0)
        passes++
    else if (newRelPosition >= 100 && currentPosition != 0)
        passes++

    return passes

}