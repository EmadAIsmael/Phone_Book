package phonebook


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

    println()
    println("Start searching (hash table)...")
    pb.hashmapSearch()
}
