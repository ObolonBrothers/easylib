
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * @Author Alex Syrotenko (@FlyCreat1ve)
 *  Created on 05.03.17.
 *  @See ClassicListTest
 */

class ClassicListTest {
    private val list = ClassicList<Int>()
    private val sublist = ClassicList<Int>()
    private val checklist = ClassicList<Int>()

    fun equalList(checklist : List<Int>, list: ClassicList<Int>) : Boolean {
        var currentEquality = true

        var checkIterator = checklist.iterator()
        var mainIterator = list.iterator()

        while (checkIterator.hasNext() && mainIterator.hasNext()) {
            if (checkIterator.next() != mainIterator.next())
                currentEquality = false
        }
        return currentEquality
    }

    fun genDigit(rand : Random, bound : Int) : Int {
        return rand.nextInt(list.size - 1)
    }

    @Test fun addToClassicList() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            list.add(i)

        assertEquals(list.size, len)
        return
    }

    @Test fun checkContains(): Unit {
        val len = 20
        for (i in IntRange(1, len))
            list.add(i)

        for (i in IntRange(1, len/2))
            sublist.add(i)

        assertEquals(list.contains(10), true)
        assertEquals(list.contains(30), false)
        assertEquals(list.containsAll(sublist), true)

    }

    @Test fun removeFromClassicList() : Unit {
        val len = 10
        val _checklist = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val shuffleList = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        java.util.Collections.shuffle(shuffleList)

        list += IntRange(1, len)

        for (i in IntRange(0, len - 1)) {
            list.remove(shuffleList[i])
            _checklist.remove(shuffleList[i])
            assertEquals(true, equalList(_checklist, list))
        }

        assertEquals(list.size, 0)
    }

    @Test fun removeRandomValFromLinkedList() : Unit {
        val len = 10
        for (i in IntRange(1, len))
            list.add(i)

        assertEquals(list.size, len)

        val randArr = arrayListOf(3, 5, 8, 9)
        val _checklist = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        for (i in IntRange(0, randArr.size - 1)) {
            list.remove(randArr[i])
            _checklist.remove(randArr[i])
            assertEquals(true, equalList(_checklist, list))
        }

        assertEquals(list.size, len - 4)
    }

    @Test fun clearList() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            list.add(i)

        list.clear()
        assertEquals(list.size, 0)
    }

    @Test fun retainsList() : Unit {
        val len = 10
        for (i in IntRange(1, len))         // 1, 2, ..., 9, 10
            list.add(i)

        for (i in IntRange(len/2, len)) {   // 6, 7, 8, 9, 11
            sublist.add(i)
            checklist.add(i)
        }

        list.retainAll(sublist)

        assertEquals(list.equals(checklist), true)
    }

}