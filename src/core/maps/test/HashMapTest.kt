import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

/**
 * @Author is Alex Syrotenko (@flystyle)
 *  Created on 30.05.17.
 */

class HashMapTest {

    val map = HashMap<Int, Int>();
    val rand = Random()

    @Test fun putAndGetFromHashMap() {
        for (i in 1..100) {
            map.put(i, i + 100)
        }

        var index = rand.nextInt(100)
        assertEquals(index + 100, map[index])

        index = rand.nextInt(100)
        assertEquals(index + 100, map[index])
    }

    @Test fun sizeAndClearTest() {
        val size = 100
        for (i in 1..size) {
            map.put(i, i + 100)
        }
        assertEquals(size, map.size)

        map.clear()
        assertEquals(0, map.size)
    }

}
