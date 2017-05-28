import org.junit.Test
import org.junit.Assert

/**
 * Created by akise on 27.05.2017
 * @See Queue .
 */
class QueueTest {
    private val queue = Queue<Int>()

    @Test fun sizeCheck(): Unit {
        val len = 15
        for (i in IntRange(1, len))
            queue.add(i)

        Assert.assertEquals(queue.size, 15)
    }

    @Test fun addCheck() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            queue.add(i)

        Assert.assertEquals(queue.size, len)
        Assert.assertEquals(queue.tail(), 20)
        Assert.assertEquals(queue.head(), 1)
    }

    @Test fun elementCheck() : Unit {
        val len = 10
        for (i in IntRange(1, len))
            queue.add(i)

        Assert.assertEquals(queue.element(), 1)
    }

    @Test fun pollCheck() : Unit {
        val len = 10
        for (i in IntRange(1, len))
            queue.add(i)

        Assert.assertEquals(queue.poll(), 1)
        Assert.assertEquals(queue.size, 9)
    }
}