import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @Author Alex Syrotenko (@flystyle)
 *  Created on 05.03.17.
 *  @See ClassicListTest
 *  @Testmethod addToClassicList. It tests fucntionality of element addition to list.
 *  @Testmethod checkContains. It tests containing of element in list.
 *
 */

class ClassicListTest {
    private val list = ClassicList<Int>()
    private val sublist = ClassicList<Int>()

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

}