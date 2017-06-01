/**
 * Created by akise on 27.05.2017.
 * Double-ended queue is an abstract data type that generalizes a queue,
 * for which elements can be added to or removed from either
 * the front (head) or back (tail).
 *
 * @author Volodymyr Semenovych (@akisemenovych).
 * @since 0.1
 * @param E the type of elements which are consists in this data structure.
 * @see Node .
 */
class Deque<E> (private var dequeHead : Node<E>? = null, private var dequeTail : Node<E>? = null) : AbstractQueue<E> {
    init {
        dequeHead?.setNext(dequeTail)
        dequeTail?.setPrev(dequeHead)
    }
    /**
     * Inner misc class for representation of container with data and next element reference.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param el valuable data object
     * @see Deque .
     * @constructor creates object with data and temporary null reference for next block.
     */
    class Node<E> (private var el: E) : AbstractQueue.QueueNode<E>(el) {

        private var next : Node<E>? = null
        private var prev : Node<E>? = null
        /**
         * Unpack element from object Node
         */
        override fun getElement() : E = el

        /**
         * Pack element to object Node
         */
        override fun setElement(el : E) {
            this.el = el
        }
        /**
         * Get link on next Node
         */
        override fun next() : Node<E>? = next

        /**
         * Get link on prev Node
         */
        fun prev() : Node<E>? = prev

        /**
         * Set link to next Node
         */
        fun setNext(nextEl : Node<E>?) {
            this.next = nextEl
        }

        /**
         * Set link to prev Node
         */
        fun setPrev(prevEl : Node<E>?) {
            this.prev = prevEl
        }

        /**
         * Remove current Node
         */
        override fun remove() {
            if (el != null) {
                this.el = next!!.getElement()
                if (next!!.next()?.equals(null) as Boolean)
                    this.next = next!!.next
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other?.javaClass != javaClass) return false
            other as Node<*>
            if (el != other.el) return false
            return true
        }

        override fun hashCode(): Int {
            var result = el?.hashCode() ?: 0
            result = 31 * result + (next!!.hashCode() ?: 0)
            return result
        }
    }
    /**
     * Retrieves, but does not remove, the head of this queue.
     */
    override fun head(): Node<E>? = this.dequeHead

    /**
     * Retrieves, but does not remove, the tail of this queue.
     */
    override fun tail(): Node<E>? = this.dequeTail

    /**
     * Retrieves, but does not remove, the next node of this queue.
     */
    fun next(selectedNode : Node<E>) : Node<E>? = selectedNode?.next()

    /**
     * Retrieves, but does not remove, the prev node of this queue.
     */
    fun prev(selectedNode : Node<E>) : Node<E>? = selectedNode?.prev()


    private var queueSize = 0

    /**
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return Queue size
     */
    override val size: Int
        get() = this.queueSize

    /**
     * Inserts the specified element into this queue (to Tail).
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param element
     * @return flag of action status
     */
    override fun add(element : E): Boolean {
        var current = Node(element)
        if (dequeHead == null) {
            dequeHead = current
            queueSize += 1
            return true
        } else if (dequeTail == null) {
            dequeTail = current
            dequeHead?.setNext(dequeTail)
            queueSize += 1
            return true
        } else {
            dequeTail?.setNext(current)
            dequeTail = current
            queueSize += 1
            return true
        }
        return false
    }

    /**
     * Inserts the specified element into this queue to Head.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param element
     * @return flag of action status
     */
    fun addToHead(element : E): Boolean {
        var current : Node<E>? = null
        current?.setElement(element)
        if (dequeHead == null) {
            dequeHead = current
            queueSize += 1
            return true
        } else {
            dequeHead?.setPrev(current)
            current?.setNext(dequeHead)
            dequeHead = current
            queueSize += 1
            return true
        }
        return false
    }

    /**
     * Retrieves, but does not remove, the head element of this queue.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return head element from Queue
     */
    override fun element() : E? {
        return dequeHead?.getElement()
    }

    /**
     * Retrieves, but does not remove, the tail element of this queue.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return tail element from Queue
     */
    fun elementFromTail() : E? {
        return dequeTail?.getElement()
    }

    /**
     * Retrieves and removes, the head element of this queue,
     * or returns null if this queue is empty.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return head element from Queue
     */
    override fun poll() : E? {
        var element : E? = dequeHead?.getElement()
        dequeHead = dequeHead?.next()
        return element
    }

    /**
     * Retrieves and removes, the tail element of this queue,
     * or returns null if this queue is empty.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return tail element from Queue
     */
    fun pollFromTail() : E? {
        var element : E? = dequeTail?.getElement()
        dequeTail = dequeTail?.prev()
        return element
    }
}