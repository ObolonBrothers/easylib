/**
 * The BinaryHeap is an -generic- implementation of the PriorityQueue interface.
 * This is a binary min-heap implementation of the priority queue ADT.
 *
 * @author Vasyl Antoniuk (@toniukan).
 * @since 0.1
 * @param E the type of the elements which the BinaryHeap will contain
 */

class BinaryHeap<E : Comparable<E>> : AbstractHeap<E> {
    private var list: MutableList<E>? = null

    override val size: Int
        get() = this.list?.size ?: 0

    /**
     * @author Vasyl Antoniuk (@toniukan)
     * @since 0.1
     * @return true if the heap has no elements; false otherwise.
     */
    override fun isEmpty(): Boolean {
        return this.size == 0
    }

    /**
     * Inserts element to the BinaryHeap.
     *
     * @author Vasyl Antoniuk (@toniukan)
     * @since 0.1
     * @param element Element which should be added to the BinaryHeap.
     * @see BinaryHeap .
     */

    override fun add(element: E) {
        if (this.list == null) {
            this.list = arrayListOf()
        }
        this.list?.add(element)

        bubbleUp()
    }

    /**
     * Removes all of the objects from the collection.
     *
     * @author Vasyl Antoniuk (@toniukan)
     * @since 0.1
     * @see BinaryHeap .
     */

    override fun clear() {
        this.list = null
    }

    /**
     * Removes and returns the minimum element in the heap.
     *
     * @author Vasyl Antoniuk (@toniukan)
     * @since 0.1
     * @see BinaryHeap .
     */

    override fun remove(): E {
        val result = this.peek()
        if (this.size == 1)
            this.clear()
        else
            this.list!![0] = this.list!!.removeAt(this.size - 1)

        bubbleDown()

        return result
    }

    /**
     * Returns (but does not remove) the minimum element in the heap.
     *
     * @author Vasyl Antoniuk (@toniukan)
     * @since 0.1
     * @see BinaryHeap .
     */

    override fun peek(): E {
        if (this.isEmpty()) {
            throw IllegalStateException()
        }
        return this.list!![0]
    }

    /**
     * Performs the "bubble up" operation to place a newly inserted element
     * (i.e. the element that is at the size index) in its correct place so
     * that the heap maintains the min-heap order property.
     */
    private fun bubbleUp() {
        var index = this.size - 1

        while (hasParent(index)
                && parent(index) > this.list!![index]) {
            // parent/child are out of order; swap them
            swap(index, parentIndex(index))
            index = parentIndex(index)
        }
    }

    /**
     * Performs the "bubble down" operation to place the element that is at the
     * root of the heap in its correct place so that the heap maintains the
     * min-heap order property.
     */
    private fun bubbleDown() {
        var index = 0

        // bubble down
        while (hasLeftChild(index)) {
            // which of my children is smaller?
            var smallerChild = leftIndex(index)

            // bubble with the smaller child, if I have a smaller child
            if (hasRightChild(index)
                    && this.list!![leftIndex(index)] > this.list!![rightIndex(index)]) {
                smallerChild = rightIndex(index)
            }

            if (this.list!![index] > this.list!![smallerChild]) {
                swap(index, smallerChild)
            } else {
                // otherwise, get outta here!
                break
            }

            // make sure to update loop counter/index of where last el is put
            index = smallerChild
        }
    }

    private fun hasParent(i: Int): Boolean {
        return i > 0
    }

    private fun parentIndex(i: Int): Int {
        return (i - 1) / 2
    }

    private fun leftIndex(i: Int): Int {
        return i * 2 + 1
    }

    private fun rightIndex(i: Int): Int {
        return i * 2 + 2
    }

    private fun hasLeftChild(i: Int): Boolean {
        return leftIndex(i) < this.size
    }

    private fun hasRightChild(i: Int): Boolean {
        return rightIndex(i) < this.size
    }

    private fun parent(i: Int): E {
        return this.list!![parentIndex(i)]
    }

    private fun swap(index1: Int, index2: Int) {
        val tmp = this.list!![index1]
        this.list!![index1] = this.list!![index2]
        this.list!![index2] = tmp
    }
}