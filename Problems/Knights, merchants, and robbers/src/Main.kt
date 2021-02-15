import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val (firstAnswer, secondAnswer, confession) = Array(3) { scanner.nextBoolean() }

    println(if (confession) false else firstAnswer == secondAnswer)
}
