/**
 * @Author is Yurii Yatsenko (@kyparus)
 * A generic tree of elements.
 * @param E the type of elements contained in the collection. The collection is covariant on its element type.
 */
interface AbstractTree<E> {
    // Query Operations
    /**
     * Returns the size of the collection.
     */
    val size: Int

    /**
     * Returns `true` if the collection is empty (contains no elements), `false` otherwise.
     */
    val isEmpty: Boolean

    /**
     * Adds a value to tree.
     */
    fun insert(data: E)

    /**
     * Returns 'true' if value is in the collection, otherwise 'false'
     */
    fun search(value: E): Boolean

    /**
     * Removes all elements from the tree.
     */
    fun clear()
}