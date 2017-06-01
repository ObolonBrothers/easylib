import org.junit.Assert
import org.junit.Test

/**
 * Created by akise on 28.05.2017.
 * @See Deque
 */
class DequeTest {
    private val deque = Deque<Int>()

    @Test fun sizeCheck(): Unit {
        val len = 15
        for (i in IntRange(1, len))
            deque.add(i)

        Assert.assertEquals(deque.size, 15)
    }

    @Test fun addCeck() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            deque.add(i)

        Assert.assertEquals(deque.size, len)
        Assert.assertEquals(deque.tail(), 20)
        Assert.assertEquals(deque.head(), 1)
    }

    @Test fun addToHeadCheck() : Unit {
        val len = 20
        for (i in IntRange(1, len))
            deque.addToHead(i)

        Assert.assertEquals(deque.size, len)
        Assert.assertEquals(deque.tail(), 1)
        Assert.assertEquals(deque.head(), 20)
    }

    @Test fun elementCheck() : Unit {
        val len = 10
        for (i in IntRange(1, len))
            deque.add(i)

        Assert.assertEquals(deque.element(), 1)
    }

    @Test fun elementFromTailCheck() : Unit {
        val len = 10
        for (i in IntRange(1, len))
            deque.add(i)

        Assert.assertEquals(deque.elementFromTail(), 10)
    }

    @Test fun pollCheck() : Unit {
        val len = 10
        for (i in IntRange(1, len))
            deque.add(i)

        for (i in IntRange(1, len)) {
            Assert.assertEquals(deque.poll(), i)
            Assert.assertEquals(deque.size, len-i)
        }
    }

    @Test fun pollFromTailCheck() : Unit {
        val len = 10
        for (i in IntRange(1, len))
            deque.add(i)

        for (i in IntRange(1, len)) {
            Assert.assertEquals(deque.pollFromTail(), len + 1 - i)
            Assert.assertEquals(deque.size, len-i)
        }
    }
}