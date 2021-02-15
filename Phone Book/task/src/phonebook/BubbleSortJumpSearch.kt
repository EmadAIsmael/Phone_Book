package phonebook

import java.io.File

class BubbleSortJumpSearch(directoryFile: File,
                           searchItemsFile: File,
                           private val linearSearchTiming: Long = 0L): Search() {

    private var bubbleSortTime: Long = 0L

    private var totalTime: Long = 0L

    private var sortingTimeMessage = ""

    private var searchingTimeMessage = ""

    init {

        val lines = directoryFile.readLines().toMutableList()
        val searchItems = searchItemsFile.readLines()

        if (bubbleSort(lines)) {
            search(lines, searchItems, SearchType.JUMP_SEARCH)

            sortingTimeMessage = "Sorting time: ${timingMessage(bubbleSortTime)}"
            searchingTimeMessage = "Searching time: ${timingMessage(searchTime)}"
        } else {

            search(lines, searchItems, SearchType.LINEAR_SEARCH)
            sortingTimeMessage = "Sorting time: ${timingMessage(bubbleSortTime)} - STOPPED, moved to linear search"
            searchingTimeMessage = "Searching time: ${timingMessage(searchTime)}"
        }

        totalTime = bubbleSortTime + searchTime
        println(statusMessage(foundCount, searchItems.size, totalTime))
        println(sortingTimeMessage)
        println(searchingTimeMessage)
    }

    private fun bubbleSort(a: MutableList<String>): Boolean {
        val timeLimit = linearSearchTiming * 10
        val startTime = System.currentTimeMillis()
        for (i in (a.size - 1) downTo 0) {
            var swapped = false
            for (j in 0 until i) {
                if (a[j].substringAfter(" ") > a[j + 1].substringAfter(" ")) {
                    a[j] = a[j + 1].also { a[j + 1] = a[j] }
                    swapped = true
                }
            }
            bubbleSortTime = System.currentTimeMillis() - startTime
            if (bubbleSortTime > timeLimit) return false
            if (!swapped) break
        }
        return true
    }
}
