package phonebook

class Sort {

    fun bubbleSort(a: MutableList<PhoneBook.PhoneBookEntry>): Boolean {
        val timeLimit = PhoneBook.linearSearchTime * 10
        val startTime = System.currentTimeMillis()
        for (i in (a.size - 1) downTo 0) {
            var swapped = false
            for (j in 0 until i) {
                if (a[j].name > a[j + 1].name) {
                    a[j] = a[j + 1].also { a[j + 1] = a[j] }
                    swapped = true
                }
            }
            PhoneBook.bubbleSortTime = System.currentTimeMillis() - startTime
            if (PhoneBook.bubbleSortTime > timeLimit) return false
            if (!swapped) break
        }
        return true
    }

    private fun exch(a: MutableList<PhoneBook.PhoneBookEntry>, i: Int, j: Int) {
        a[i] = a[j].also { a[j] = a[i] }
    }

    fun quick(a: MutableList<PhoneBook.PhoneBookEntry>, lo: Int = 0, hi: Int = a.size - 1) {
        if (hi <= lo) return
        var lt = lo
        var i = lo + 1
        var gt = hi
        val v = a[lo]
        while (i <= gt) {
            val cmp = a[i].name.compareTo(v.name)
            if (cmp < 0) exch(a, lt++, i++) else if (cmp > 0) exch(a, i, gt--) else i++
        } // Now a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
        quick(a, lo, lt - 1)
        quick(a, gt + 1, hi)
    }
}