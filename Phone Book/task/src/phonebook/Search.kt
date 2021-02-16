package phonebook

import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

abstract class Search {

    enum class SearchType {
        LINEAR_SEARCH, JUMP_SEARCH, BINARY_SEARCH, HASH_SEARCH;
    }

    fun search(
        pb: PhoneBook,
        searchItems: List<String>,
        searchType: SearchType
    ) {

        val lines = pb.dirList
        val startTime = System.currentTimeMillis()
        for (item in searchItems) {
            when (searchType) {
                SearchType.LINEAR_SEARCH -> linearSearch(item, lines)
                SearchType.JUMP_SEARCH -> {
                    val index = jumpSearch(item, lines)
                    if (index != -1)
                        PhoneBook.bubbleFoundCount++
                }
                SearchType.BINARY_SEARCH -> {
                    val index = binarySearch(item, lines)
                    if (index != -1)
                        PhoneBook.quickFoundCount++
                }
                SearchType.HASH_SEARCH -> {
                    val index = pb.ht?.search(item)
                    if (index != -1)
                        PhoneBook.hashFoundCount++
                }
            }
        }

        val endTime = System.currentTimeMillis()
        PhoneBook.linearSearchTime = endTime - startTime
    }

    private fun linearSearch(item: String, lines: MutableList<PhoneBook.PhoneBookEntry>): Int {
        for ((idx, phoneBookEntry) in lines.withIndex()) {
            if (item == phoneBookEntry.name) {
                PhoneBook.linearFoundCount++
                return idx
            }
        }
        return -1
    }

    private fun jumpSearch(
        item: String,
        arr: MutableList<PhoneBook.PhoneBookEntry>,
        left: Int = 0,
        right: Int = arr.size - 1
    ): Int {

        val step = floor(sqrt(right - left + 1.0)).toInt()
        var lleft: Int = left
        var lright: Int = min(lleft + step - 1, right)

        if (right - left + 1 <= 1) {
            return if (item == arr[lleft].name)
                lleft
            else -1
        }
        while (lleft <= right) {

            if (item == arr[lleft].name)
                return lleft
            if (item < arr[lright].name)
                return jumpSearch(item, arr, max(0, lleft - step), max(0, lleft - 1))
            if (lleft == right) break
            lleft = min(lleft + step, right)
            lright = min(lleft + step - 1, right)
        }
        return -1
    }

    private fun binarySearch(
        item: String,
        arr: MutableList<PhoneBook.PhoneBookEntry>,
        left: Int = 0,
        right: Int = arr.size - 1
    ): Int {
        val mid = (left + right) / 2
        return when {
            item == arr[mid].name -> mid
            item < arr[mid].name -> binarySearch(item, arr, left, mid - 1)
            item > arr[mid].name -> binarySearch(item, arr, mid + 1, right)
            else -> -1
        }
    }
}
