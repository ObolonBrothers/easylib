import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @Author Vasyl Antoniuk (@toniukan)
 */

class BinaryHeapTest {
    @Test fun createHeap(): Unit {
        val heap = BinaryHeap<Int>()

        assertEquals(heap.size, 0)
        assertEquals(heap.isEmpty(), true)
        return
    }

    @Test fun addOneElement(): Unit {
        val heap = BinaryHeap<Int>()

        heap.add(10)

        assertEquals(heap.size, 1)
        assertEquals(heap.isEmpty(), false)
    }

    @Test fun clear(): Unit {
        val heap = BinaryHeap<Int>()

        heap.add(10)

        heap.clear()

        assertEquals(heap.size, 0)
        assertEquals(heap.isEmpty(), true)
    }

    @Test fun peek(): Unit {
        val heap = BinaryHeap<Int>()

        heap.add(10)
        heap.add(20)

        assertEquals(heap.peek(), 10)
        // to check if 'peek' is not changing the heap
        assertEquals(heap.peek(), 10)
    }

    @Test fun remove(): Unit {
        val heap = BinaryHeap<Int>()

        heap.add(10)
        heap.add(20)
        heap.add(30)

        assertEquals(heap.remove(), 10)
        assertEquals(heap.remove(), 20)
        assertEquals(heap.remove(), 30)
        assertEquals(heap.isEmpty(), true)
    }

    @Test fun normalScenario(): Unit {
        val heap = BinaryHeap<Int>()

        heap.add(30)
        assertEquals(heap.peek(), 30)

        heap.add(15)
        assertEquals(heap.peek(), 15)

        heap.add(10)
        assertEquals(heap.remove(), 10)
        assertEquals(heap.peek(), 15)

        heap.add(25)
        assertEquals(heap.remove(), 15)
        assertEquals(heap.remove(), 25)
        assertEquals(heap.remove(), 30)
    }

    @Test fun duplicates(): Unit {
        val heap = BinaryHeap<Int>()

        heap.add(10)
        heap.add(5)
        heap.add(15)
        heap.add(10)

        assertEquals(heap.remove(), 5)
        assertEquals(heap.remove(), 10)
        assertEquals(heap.remove(), 10)
        assertEquals(heap.remove(), 15)
    }
}