package phonebook

import java.io.File

class PhoneBook(dirFilePath: String, searchFilePath: String) : Search() {
    private val directoryFile = File(dirFilePath)
    private val searchItemsFile = File(searchFilePath)
    val dirList = mutableListOf<PhoneBookEntry>()
    private val searchList = mutableListOf<String>()
    private val sorter = Sort()
    var ht: HashTable? = null

    data class PhoneBookEntry(val phone: String, val name: String) {
        override fun equals(other: Any?): Boolean = this.name == (other as PhoneBookEntry).name
        override fun hashCode(): Int = name.hashCode()
    }

    companion object Timings {
        var linearFoundCount: Int = 0
        var bubbleFoundCount: Int = 0
        var quickFoundCount: Int = 0
        var hashFoundCount: Int = 0

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
        // hash table
        //
        var hashCreateTime: Long = 0L
        var hashSearchTime: Long = 0L
        var hashTotalTime: Long = 0L
        var hashCreateTimeMessage = ""
        var hashSearchTimeMessage = ""
    }

    init {

        dirList()
        searchList()
        ht = HashTable(dirList)
    }

    private fun dirList(): MutableList<PhoneBookEntry> {
        directoryFile.forEachLine {
            dirList.add(
                PhoneBookEntry(
                    phone = it.substringBefore(" "),
                    name = it.substringAfter(" ")
                )
            )
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

    private fun timingMessage(t: Long): String {
        val (min, sec, ms) = formatMillis(t)
        return "$min min. $sec sec. $ms ms."
    }

    private fun recordsMessage(found: Int, total: Int) = "Found $found / $total entries."

    private fun statusMessage(found: Int, total: Int, searchTime: Long) =
        "${recordsMessage(found, total)} Time taken: ${timingMessage(searchTime)}"

    fun linearSearch(): Long {

        search(this, searchList, SearchType.LINEAR_SEARCH)
        println(statusMessage(linearFoundCount, searchList.size, linearSearchTime))

        linearTotalTime = linearSearchTime
        return linearTotalTime
    }

    fun bubbleSortJumpSearch() {
        if (sorter.bubbleSort(dirList)) {
            val timing = System.currentTimeMillis()
            search(this, searchList, SearchType.JUMP_SEARCH)
            bubbleSearchTime = System.currentTimeMillis() - timing

            bubbleSortTimeMessage = "Sorting time: ${timingMessage(bubbleSortTime)}"
            bubbleSearchTimeMessage = "Searching time: ${timingMessage(bubbleSearchTime)}"
        } else {
            val timing = System.currentTimeMillis()
            linearFoundCount = 0
            search(this, searchList, SearchType.LINEAR_SEARCH)
            bubbleFoundCount = linearFoundCount
            bubbleSearchTime = System.currentTimeMillis() - timing

            bubbleSortTimeMessage = "Sorting time: ${timingMessage(bubbleSortTime)} - STOPPED, moved to linear search"
            bubbleSearchTimeMessage = "Searching time: ${timingMessage(bubbleSearchTime)}"
        }

        bubbleTotalTime = bubbleSortTime + bubbleSearchTime
        println(statusMessage(bubbleFoundCount, searchList.size, bubbleTotalTime))
        println(bubbleSortTimeMessage)
        println(bubbleSearchTimeMessage)
    }

    fun quickSortBinarySearch() {
        var startTime = System.currentTimeMillis()
        sorter.quick(dirList)
        quickSortTime = System.currentTimeMillis() - startTime

        startTime = System.currentTimeMillis()
        search(this, searchList, SearchType.BINARY_SEARCH)
        quickSearchTime = System.currentTimeMillis() - startTime

        quickSortTimeMessage = "Sorting time: ${timingMessage(quickSortTime)}"
        quickSearchTimeMessage = "Searching time: ${timingMessage(quickSearchTime)}"
        quickTotalTime = quickSortTime + quickSearchTime

        println(statusMessage(quickFoundCount, searchList.size, quickTotalTime))
        println(quickSortTimeMessage)
        println(quickSearchTimeMessage)
    }

    fun hashmapSearch() {
        var startTime = System.currentTimeMillis()
        ht?.index()
        hashCreateTime = System.currentTimeMillis() - startTime

        startTime = System.currentTimeMillis()
        search(this, searchList, SearchType.HASH_SEARCH)
        hashSearchTime = System.currentTimeMillis() - startTime

        hashCreateTimeMessage = "Creating time: ${timingMessage(hashCreateTime)}"
        hashSearchTimeMessage = "Searching time: ${timingMessage(hashSearchTime)}"
        hashTotalTime = hashCreateTime + hashSearchTime

        println(statusMessage(hashFoundCount, searchList.size, hashTotalTime))
        println(hashCreateTimeMessage)
        println(hashSearchTimeMessage)
    }
}
