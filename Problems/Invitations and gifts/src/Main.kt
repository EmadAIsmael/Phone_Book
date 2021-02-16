import java.util.Scanner

fun main() {
    // write your code here
    val scanner = Scanner(java.lang.System.`in`)
    val (hasInvitation, gotGift) = Array(2) { scanner.nextBoolean() }

    println(hasInvitation && gotGift)
}
