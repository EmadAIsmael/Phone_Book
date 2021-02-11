package phonebook

import java.io.File

class LinearSearch(directoryFile: File, searchItemsFile: File): Search() {

    init {
        val lines = directoryFile.readLines()
        val searchItems = searchItemsFile.readLines()

        search(lines, searchItems, SearchType.LINEAR_SEARCH)
        println(statusMessage(foundCount, searchItems.size, searchTime))
    }
}
