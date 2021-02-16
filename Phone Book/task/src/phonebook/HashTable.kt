package phonebook

class HashTable(private val dirList: MutableList<PhoneBook.PhoneBookEntry>) {
    private val size = dirList.size
    private val table = MutableList(dirList.size) { mutableListOf<Int>() }

    private fun hash(key: String): Int {
        return (key.hashCode() and 0x7fffffff) % size
    }

    private fun put(key: String, value: Int): Boolean {
        // key: the index key
        // value: the index number of the data in the indexed file/list
        // i.e. table[x][y] contains the dirList index
        // where dirList[v].name == key
        val index = hash(key)
        for (v in table[index]) {
            if (v == value) return false
        }
        table[index].add(value)
        return true
    }

    fun index() {
        for ((idx, record) in dirList.withIndex()) {
            put(record.name, idx)
        }
    }

    fun search(key: String): Int {
        val index = hash(key)
        for (v in table[index]) {
            if (key == dirList[v].name) {
                return v
            }
        }
        return -1
    }
}
