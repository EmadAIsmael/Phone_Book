package phonebook

import java.io.File

class PhoneBook(dirFilePath: String, searchFilePath: String): Search1() {
    private val directoryFile = File(dirFilePath)
    private val searchItemsFile = File(searchFilePath)
    private val dirList = mutableListOf<PhoneBookEntry>()
    private val searchList = mutableListOf<String>()
    private val sorter = Sort()

    data class PhoneBookEntry(val phone: String, val name: String) {
        override fun equals(other: Any?): Boolean = this.name == (other as PhoneBookEntry).name
        override fun hashCode(): Int = name.hashCode()
    }

    companion object Timings {
        var linearFoundCount: Int = 0
        var bubbleFoundCount: Int = 0
        var quickFoundCount: Int = 0

        //
        // unordered, linear search
        //
        var linearSearchTime: Long = 0L
        var linearTotalTime: Long = 0L
        //
        // bubble sort, jump search
        //
        var bubbleSortTime: Long = 0L

        var bubbleSearchTime: Long = 0L

        var bubbleTotalTime: Long = 0L

        var bubbleSortTimeMessage = ""

        var bubbleSearchTimeMessage = ""
        //
        // quick sort, binary search
        //
        var quickSortTime: Long = 0L

        var quickSearchTime: Long = 0L

        var quickTotalTime: Long = 0L

        var quickSortTimeMessage = ""

        var quickSearchTimeMessage = ""
        //
        //
    }

    init {
        dirList()
        searchList()
    }

    private fun dirList(): MutableList<PhoneBookEntry> {
        directoryFile.forEachLine {
            dirList.add(PhoneBookEntry(phone = it.substringBefore(" "), name = it.substringAfter(" ")))
        }
        return dirList
    }

    private fun searchList(): MutableList<String> {
        searchItemsFile.forEachLine {
            searchList.add(it)
        }
        return searchList
    }

    private fun formatMillis(t: Long): LongArray {
        val min = t / 60_000
        val sec = (t % 60_000) / 1_000
        val ms = (t % 60_000) % 1_000

        return longArrayOf(min, sec, ms)
    }

//    private fun timingMessage(t: Long): String {
//        val (min, sec, ms) = formatMillis(t)
//        return "$min min. $sec sec. $ms ms."
//    }
//
//    private fun recordsMessage(found: Int, total: Int) = "Found $found / $total entries."
//
//    fun statusMessage(found: Int, total: Int, searchTime: Long) =
//        "${recordsMessage(found, total)} Time taken: ${timingMessage(searchTime)}"

    fun linearSearch(): Long {

        search(dirList, searchList, SearchType.LINEAR_SEARCH)
        println(statusMessage(linearFoundCount, searchList.size, linearSearchTime))

        linearTotalTime = linearSearchTime
        return linearTotalTime
    }

    fun bubbleSortJumpSearch() {
        if (sorter.bubbleSort(dirList)) {
            val timing = System.currentTimeMillis()
            search(dirList, searchList, SearchType.JUMP_SEARCH)
            bubbleSearchTime = System.currentTimeMillis() - timing

            bubbleSortTimeMessage = "Sorting time: ${timingMessage(bubbleSortTime)}"
            bubbleSearchTimeMessage = "Searching time: ${timingMessage(bubbleSearchTime)}"
        } else {
            val timing = System.currentTimeMillis()
            search(dirList, searchList, SearchType.LINEAR_SEARCH)
            bubbleSearchTime = System.currentTimeMillis() - timing

            bubbleSortTimeMessage = "Sorting time: ${timingMessage(bubbleSortTime)} - STOPPED, moved to linear search"
            bubbleSearchTimeMessage = "Searching time: ${timingMessage(bubbleSearchTime)}"
        }

        bubbleTotalTime = bubbleSortTime + bubbleSearchTime
        println(statusMessage(linearFoundCount, searchList.size, bubbleTotalTime))
        println(bubbleSortTimeMessage)
        println(bubbleSearchTimeMessage)
    }

    fun quickSortBinarySearch() {
        val startTime = System.currentTimeMillis()
        sorter.quick(dirList)
        search(dirList, searchList, SearchType.BINARY_SEARCH)
        quickSortTime = System.currentTimeMillis() - startTime
        quickSortTimeMessage = "Sorting time: ${phonebook.timingMessage(quickSortTime)}"
        quickSearchTimeMessage = "Searching time: ${phonebook.timingMessage(quickSearchTime)}"
        quickTotalTime = quickSortTime + quickSearchTime

        println(statusMessage(linearFoundCount, searchList.size, quickTotalTime))
        println(quickSortTimeMessage)
        println(quickSearchTimeMessage)
    }
}

fun main() {

    val pb = PhoneBook(
        "/home/emad/kotlin_workspace/directory.txt",
        "/home/emad/kotlin_workspace/find.txt"
    )

    println("Start searching (linear search)...")
    pb.linearSearch()

    println()
    println("Start searching (bubble sort + jump search)...")
    pb.bubbleSortJumpSearch()

    println()
    println("Start searching (quick sort + binary search)...")
    pb.quickSortBinarySearch()
}
