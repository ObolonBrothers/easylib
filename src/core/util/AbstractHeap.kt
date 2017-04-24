/**
 * @Author is Vasyl Antoniuk (@toniukan)
 * A generic heap of elements.
 * @param E the type of elements contained in the collection. The collection is covariant on its element type.
 */
interface AbstractHeap<E> {
    // Query Operations
    /**
     * Returns the size of the collection.
     */
    val size: Int

    /**
     * Returns `true` if the collection is empty (contains no elements), `false` otherwise.
     */
    fun isEmpty(): Boolean

    /**
     * Adds a value to heap.
     */
    fun add(element: E)

    /**
     * Returns (but does not remove) the top element in the heap.
     */
    fun peek(): E

    /**
     * Removes and returns the top element in the heap.
     */
    fun remove(): E

    /**
     * Removes all elements from the heap.
     */
    fun clear()
}