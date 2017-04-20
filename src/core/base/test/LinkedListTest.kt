
import org.junit.Assert
import org.junit.Test

/**
 * @Author is Alex Syrotenko (@flystyle)
 *  Created on 16.04.17.
 */
class LinkedListTest {
    private val list = LinkedList<Int>()
    private val sublist = LinkedList<Int>()
    private val checklist = LinkedList<Int>()

    @Test fun addToLinkedList() : Unit {
        val len = 20
        for (i in IntRange(1, len)) {
            list.add(i)
            list.addFront(i + len)
        }

        Assert.assertEquals(list.size, len*2)
        list.clear()
        return
    }

    @Test fun batchAddToLinkedList() : Unit {
        val len = 20

        for (i in IntRange(1, len)) {
            sublist.add(i)
        }

        for (i in IntRange(1 + len, len*2)) {
            checklist.addFront(i)
        }

        list.addAll(sublist)
        list.addAllToFront(checklist)

        Assert.assertEquals(list.size, len*2)

        list.clear()
        sublist.clear()
        checklist.clear()
    }

    /**
     * We know that standart library (stdlib) guarantees us right sequence order
     * in their data structures.
     */

    @Test fun checkOrder() : Unit {
        val checklist = listOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val mainlist = LinkedList<Int>()

        for (i in IntRange(1, 10))
            mainlist.addFront(i)

        for (i in IntRange(1, 10))
            mainlist.add(i)

        val equality = true
        var currentEquality = true

        var checkIterator = checklist.iterator()
        var mainIterator = checklist.iterator()


        while (checkIterator.hasNext() && mainIterator.hasNext()) {
            if (checkIterator.next() != mainIterator.next())
                currentEquality = false
        }
        Assert.assertEquals(equality, currentEquality)
    }

    @Test fun checkLLContains(): Unit {
        val len = 20
        for (i in IntRange(1, len))
            list.add(i)

        for (i in IntRange(1, len/2))
            sublist.add(i)

        Assert.assertEquals(list.contains(10), true)
        Assert.assertEquals(list.contains(30), false)
        Assert.assertEquals(list.containsAll(sublist), true)

    }

    @Test fun removeFromLinkedList() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            list.add(i)

        for (i in IntRange(1, len))
            list.remove(i)

        Assert.assertEquals(list.size, 0)
    }

    @Test fun removeRandomValFromLinkedList() : Unit {
        val len = 100
        for (i in IntRange(1, len))
            list.add(i)

        Assert.assertEquals(list.size, len)

        val randArr = arrayListOf(4, 22, 27, 31, 37, 55, 13, 87, 3, 11)

        for (i in IntRange(0, randArr.size - 1)) {
            list.remove(randArr[i])
        }

        Assert.assertEquals(list.size, len - 10)
    }

    @Test fun clearLinkedList() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            list.add(i)

        list.clear()
        Assert.assertEquals(list.size, 0)
    }

    @Test fun retainsLinkedList() : Unit {
        val len = 10
        for (i in IntRange(1, len))         // 1, 2, ..., 9, 10
            list.add(i)

        for (i in IntRange(len/2, len)) {   // 6, 7, 8, 9, 11
            sublist.add(i)
            checklist.add(i)
        }

        list.retainAll(sublist)

        Assert.assertEquals(list.equals(checklist), true)
    }

}