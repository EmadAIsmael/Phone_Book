package phonebook

import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

abstract class Search {

    enum class SearchType {
        LINEAR_SEARCH, JUMP_SEARCH, BINARY_SEARCH;
    }

    var searchTime: Long = 0L
//        get() = field

    var foundCount = 0
//        get() = field

    fun search(lines: List<String>, searchItems: List<String>, searchType: SearchType) {
        val startTime = System.currentTimeMillis()
        for (item in searchItems) {
            when (searchType) {
                SearchType.LINEAR_SEARCH -> linearSearch(item, lines)
                SearchType.JUMP_SEARCH -> {
                    val index = jumpSearch(item, lines)
                    if (index != -1)
                        foundCount++
                }
                SearchType.BINARY_SEARCH -> {
                    val index = binarySearch(item, lines)
                    if (index != -1)
                        foundCount++
                }
            }
        }

        val endTime = System.currentTimeMillis()
        searchTime = endTime - startTime
    }

    fun linearSearch(item: String, lines: List<String>): Boolean {
        for (line in lines) {
            if (item == line.substringAfter(' ')) {
                foundCount++
                return true
            }
        }
        return false
    }

    fun jumpSearch(item: String, arr: List<String>, left: Int = 0, right: Int = arr.size - 1): Int {
        val step = floor(sqrt(right - left + 1.0)).toInt()
        var lleft: Int = left
        var lright: Int = min(lleft + step - 1, right)

        if (right - left + 1 <= 1) {
            return if (item == arr[lleft].substringAfter(" "))
                lleft
            else -1
        }
        while (lleft <= right) {

            if (item == arr[lleft].substringAfter(" "))
                return lleft
            if (item < arr[lright].substringAfter(" "))
                return jumpSearch(item, arr, max(0, lleft - step), max(0, lleft - 1))
            if (lleft == right) break
            lleft = min(lleft + step, right)
            lright = min(lleft + step - 1, right)
        }
        return -1
    }

    fun binarySearch(item: String, arr: List<String>, left: Int = 0, right: Int = arr.size - 1): Int {
        val mid = (left + right) / 2
        return if (item == arr[mid].substringAfter(" "))
            mid
        else if (item < arr[mid].substringAfter(" "))
            binarySearch(item, arr, left, mid - 1)
        else if (item > arr[mid].substringAfter(" "))
            binarySearch(item, arr, mid + 1, right)
        else
            -1
    }
}