package phonebook

import java.io.File

class QuickSortBinarySearch(
    directoryFile: File,
    searchItemsFile: File/*,
    val linearSearchTiming: Long = 0L*/
) : Search() {

    private var quickSortTime: Long = 0L

    private var totalTime: Long = 0L

    private var sortingTimeMessage = ""

    private var searchingTimeMessage = ""

    init {

        val lines = directoryFile.readLines().toMutableList()
        val searchItems = searchItemsFile.readLines()

        // val timeLimit = linearSearchTiming * 10
        val startTime = System.currentTimeMillis()
        quick(lines)
        search(lines, searchItems, SearchType.BINARY_SEARCH)
        quickSortTime = System.currentTimeMillis() - startTime
        sortingTimeMessage = "Sorting time: ${timingMessage(quickSortTime)}"
        searchingTimeMessage = "Searching time: ${timingMessage(searchTime)}"
        totalTime = quickSortTime + searchTime

        println(statusMessage(foundCount, searchItems.size, totalTime))
        println(sortingTimeMessage)
        println(searchingTimeMessage)
    }

    private fun exch(a: MutableList<String>, i: Int, j: Int) {
        a[i] = a[j].also { a[j] = a[i] }
    }

    private fun quick(a: MutableList<String>, lo: Int = 0, hi: Int = a.size - 1) {
        if (hi <= lo) return
        var lt = lo
        var i = lo + 1
        var gt = hi
        val v = a[lo]
        while (i <= gt) {
            val cmp = a[i].substringAfter(" ").compareTo(v.substringAfter(" "))
            if (cmp < 0) exch(a, lt++, i++) else if (cmp > 0) exch(a, i, gt--) else i++
        } // Now a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
        quick(a, lo, lt - 1)
        quick(a, gt + 1, hi)
    }
}