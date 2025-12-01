import java.io.File

const val STARTING_POSITION = 50
const val DIAL_SIZE = 100

fun main() {
    var password = 0
    var currentPosition = STARTING_POSITION
    val garbIn = File("./puzzle-input.txt")
    garbIn.forEachLine { line ->
        val direction = line.take(1)
        val clicks = line.substring(1).toInt()

        currentPosition += if (direction.equals("L", ignoreCase = true)) clicks * -1 else clicks
        currentPosition %= DIAL_SIZE
        password += if (currentPosition == 0) 1 else 0
    }

    println("Password: $password")

}
