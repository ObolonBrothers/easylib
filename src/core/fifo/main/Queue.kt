/**
 * Created by akisemenovych on 10.05.2017.
 * Queue is particular kind of abstract data type or collection in which the entities
 * in the collection are kept in order and the principal (or only) operations on the
 * collection are the addition of entities to the rear terminal position, known as enqueue,
 * and removal of entities from the front terminal position, known as dequeue.
 * This makes the queue a First-In-First-Out (FIFO) data structure. In a FIFO data structure,
 * the first element added to the queue will be the first one to be removed.
 *
 * @author Volodymyr Semenovych (@akisemenovych).
 * @since 0.1
 * @param E the type of elements which are consists in this data structure.
 * @see Node .
 */
class Queue<E> (private var queueHead : Node<E>? = null, private var queueTail : Node<E>? = null) : AbstractQueue<E> {
    init {
        queueHead?.setNext(queueTail)
    }
    /**
     * Inner misc class for representation of container with data and next element reference.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param el valuable data object
     * @see Queue .
     * @constructor creates object with data and temporary null reference for next block.
     */
    class Node<E> (private var el: E) : AbstractQueue.QueueNode<E>(el) {

        private var next : Node<E>? = null
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
         * Set link in connected Node
         */
        fun setNext(nextEl : Node<E>?) {
            this.next = nextEl
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
    override fun head(): Node<E>? = this.queueHead

    /**
     * Retrieves, but does not remove, the tail of this queue.
     */
    override fun tail(): Node<E>? = this.queueTail

    /**
     * Retrieves, but does not remove, the next node of this queue.
     */
    fun next(selectedNode : Node<E>): Node<E>? = selectedNode?.next()

    private var queueSize = 0

    /**
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return Queue size
     */
    override val size: Int
        get() = this.queueSize

    /**
     * Inserts the specified element into this queue.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param element
     * @return flag of action status
     */
    override fun add(element : E): Boolean {
        if (element != null) {
            var current = Node(element)
            queueSize += 1
            if (queueHead == null) {
                queueHead = current
                return true
            } else if (queueTail == null) {
                queueTail = current
                queueHead?.setNext(queueTail)
                return true
            } else {
                queueTail?.setNext(current)
                queueTail = current
                return true
            }
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
        return queueHead?.getElement()
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
        var element : E? = queueHead?.getElement()
        queueHead = queueHead?.next()
        return element
    }
}