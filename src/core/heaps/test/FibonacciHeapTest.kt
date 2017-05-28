import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @Author Vasyl Antoniuk (@toniukan)
 */

class FibonacciHeapTest {
    @Test fun createHeap(): Unit {
        val heap = FibonacciHeap<Int>()

        assertEquals(heap.size, 0)
        assertEquals(heap.isEmpty(), true)
        return
    }

    @Test fun addOneElement(): Unit {
        val heap = FibonacciHeap<Int>()

        heap.enqueue(10, .1)

        assertEquals(heap.size, 1)
        assertEquals(heap.isEmpty(), false)
    }

    @Test fun clear(): Unit {
        val heap = FibonacciHeap<Double>()

        heap.enqueue(10.0, .1)
        heap.enqueue(12.0, .2)

        heap.clear()

        assertEquals(heap.size, 0)
        assertEquals(heap.isEmpty(), true)
    }

    @Test fun peek(): Unit {
        val heap = FibonacciHeap<Int>()

        heap.enqueue(10, .5)
        heap.enqueue(20, .1)

        assertEquals(heap.peek(), 20)
        // to check if 'peek' is not changing the heap
        assertEquals(heap.peek(), 20)
    }

    @Test fun remove(): Unit {
        val heap = FibonacciHeap<Int>()

        heap.enqueue(10, .3)
        heap.enqueue(20, .2)
        heap.enqueue(30, .1)

        assertEquals(heap.remove(), 30)
        assertEquals(heap.remove(), 20)
        assertEquals(heap.remove(), 10)
        assertEquals(heap.isEmpty(), true)
    }

    @Test fun normalScenario(): Unit {
        val heap = FibonacciHeap<Char>()

        heap.enqueue('d', .3)
        assertEquals(heap.peek(), 'd')

        heap.enqueue('c', .1)
        assertEquals(heap.peek(), 'c')

        heap.enqueue('a', .2)
        assertEquals(heap.remove(), 'c')
        assertEquals(heap.peek(), 'a')

        heap.enqueue('b', .1)
        assertEquals(heap.remove(), 'b')
        assertEquals(heap.remove(), 'a')
        assertEquals(heap.remove(), 'd')
    }

    @Test fun duplicates(): Unit {
        val heap = FibonacciHeap<Int>()

        heap.enqueue(10, .1)
        heap.enqueue(15, .2)
        heap.enqueue(15, .2)
        heap.enqueue(10, .1)

        assertEquals(heap.remove(), 10)
        assertEquals(heap.remove(), 10)
        assertEquals(heap.remove(), 15)
        assertEquals(heap.remove(), 15)
    }
}