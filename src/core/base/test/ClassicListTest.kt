import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @Author is Alex Syrotenko (@flystyle)
 *  Created on 05.03.17.
 *  @Class ClassicListTest
 *
 */

class ClassicListTest {
    private val list = ClassicList<Int>()

    @Test fun addToClassicList() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            list.add(i)

        list.testOutput()
        assertEquals(list.size, len)
        return
    }

}