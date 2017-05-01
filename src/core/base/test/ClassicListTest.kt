import org.junit.Assert.assertEquals
import org.junit.Test
import sun.jvm.hotspot.utilities.Assert

/**
 * @Author Alex Syrotenko (@FlyCreat1ve)
 *  Created on 05.03.17.
 *  @See ClassicListTest
 *  @Testmethod addToClassicList. It tests fucntionality of element addition to list.
 *  @Testmethod checkContains. It tests containing of element in list.
 *
 */

class ClassicListTest {
    private val list = ClassicList<Int>()
    private val sublist = ClassicList<Int>()
    private val checklist = ClassicList<Int>()

    fun equalList(checklist : List<Int>, list: LinkedList<Int>) : Boolean {
        var currentEquality = true

        var checkIterator = checklist.iterator()
        var mainIterator = list.iterator()

        while (checkIterator.hasNext() && mainIterator.hasNext()) {
            if (checkIterator.next() != mainIterator.next())
                currentEquality = false
        }
        return currentEquality
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
        var checklist = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        for (i in IntRange(1, len))
            list.add(i)

        for (i in IntRange(1, len)) {
            list.remove(i)
            checklist.remove(i)
            assertEquals(true, equalList(checklist, list))
        }

        assertEquals(list.size, 0)
    }

    @Test fun clearList() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            list.add(i)

        list.clear()
        assertEquals(list.size, 0)
    }

    @Test fun filterTest() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            list.add(i)

        println(list.filter { it -> it % 2 == 0 })
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