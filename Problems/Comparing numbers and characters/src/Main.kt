import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val num = scanner.nextInt()
    val character = scanner.next()

    println(num.toChar().toString() == character)

}
