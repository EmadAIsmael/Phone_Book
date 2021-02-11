package phonebook

import java.io.File

fun main() {
    val directoryFile = File("/home/emad/kotlin_workspace/directory.txt")
    val searchItemsFile = File("/home/emad/kotlin_workspace/find.txt")

    println("Start searching (linear search)...")
    val linearSearchTiming = LinearSearch(directoryFile, searchItemsFile).searchTime

    println()
    println("Start searching (bubble sort + jump search)...")
    val bubbleSortJumpSearch = BubbleSortJumpSearch(directoryFile, searchItemsFile, linearSearchTiming)

}

fun formatMillis(t: Long): LongArray {
    val min = t / 60_000
    val sec = (t % 60_000) / 1_000
    val ms = (t % 60_000) % 1_000

    return longArrayOf(min, sec, ms)
}

fun timingMessage(t: Long): String {
    val (min, sec, ms) = formatMillis(t)
    return "$min min. $sec sec. $ms ms."
}

fun recordsMessage(found: Int, total: Int) = "Found $found / ${total} entries."

fun statusMessage(found: Int, total: Int, searchTime: Long) =
    "${recordsMessage(found, total)} Time taken: ${timingMessage(searchTime)}"
