import java.util.Scanner

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val (x, y, z) = BooleanArray(3) { scanner.nextBoolean() }
    println(!(x and y) or z)
}
