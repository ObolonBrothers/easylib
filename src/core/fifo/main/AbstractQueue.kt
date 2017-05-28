/**
 * Created by akisemenovych on 09.05.2017.
 */
interface AbstractQueue <E> {
    /**
     * Inner misc class for representation of container with data and next element reference.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param el valuable data object
     * @see Queue .
     * @constructor creates object with data and temporary null reference for next block.
     */
    abstract class QueueNode<E> (private var el: E) {

        /**
         * Unpack element from object Node
         */
        abstract fun getElement() : E?

        /**
         * Pack element to object Node
         */
        abstract fun setElement(el : E)

        /**
         * Get link on next Node
         */
        abstract fun next() : QueueNode<E>?

        /**
         * Remove current Node
         */
        abstract fun remove()

    }

    /**
     * Retrieves, but does not remove, the head of this queue.
     */
    fun head(): QueueNode<E>?

    /**
     * Retrieves, but does not remove, the tail of this queue.
     */
    fun tail(): QueueNode<E>?

    val size: Int

    /**
     * Inserts the specified element into this queue.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param element
     * @return flag of action status
     */
    fun add(element : E): Boolean

    /**
     * Retrieves, but does not remove, the head element of this queue.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return head element from Queue
     */
    fun element() : E?

    /**
     * Retrieves and removes, the head element of this queue,
     * or returns null if this queue is empty.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return head element from Queue
     */
    fun poll() : E?
}