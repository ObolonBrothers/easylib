/**
 * Created by akisemenovych on 02.05.2017.
 * A stack is an abstract data type that serves as a collection of elements, with two principal operations: push,
 * which adds an element to the collection, and pop, which removes the most recently added element that was not yet
 * removed.
 *
 * @author Volodymyr Semenovych (@akisemenovych).
 * @since 0.1
 * @param E the type of elements which are consists in this data structure.
 * @see Node .
 */

class Stack<E> (private var stackHead : Node<E>? = null) {

    /**
     * Inner misc class for representation of container with data and next element reference.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param el valuable data object
     * @see Stack .
     * @constructor creates object with data and temporary null reference for next block.
     */
    class Node<E> (private var el: E) {

        private var next : Node<E>? = null
        /**
         * Unpack element from object Node
         */
        fun getElement() : E = el

        fun setElement(el : E) {
            this.el = el
        }
        /**
         * Get link on connected Node
         */
        fun next() : Node<E>? = next

        /**
         * Set link in connected Node
         */
        fun setNext(nextEl : Node<E>?) {
            this.next = nextEl
        }

        /**
         * Remove current Node
         */
        fun remove() {
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

    fun head(): Node<E>? = this.stackHead
    fun next(): Node<E>? = this.stackHead!!.next()
    private var stackSize = 0


    /**
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return Stack size
     */
    val size: Int
        get() = this.stackSize


    /**
     * Push element into Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param element that needs to be pushed into Stack
     * @return flag of action status
     */
    fun push(element : E): Boolean {
        var current : Node<E>? = null
        if (element != null) {
            current?.setElement(element)
            stackSize += 1
            current?.setNext(stackHead)
            stackHead = current
            return true
        }
        return false
    }

    /**
     * Push elements collection into Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param elements collection that need to be pushed into Stack
     * @return flag of action status
     */
    fun pushAll(elements: Collection<E>): Boolean {
        elements.forEach { el -> this.push(el) }
        return true
    }

    /**
    * Pop element from Stack
    *
    * @author Volodymyr Semenovych (@akisemenovych).
    * @since 0.1
    * @return element from Stack Head
    */
    fun pop(): E? {
        if (stackHead == null) return null
        var popElement = stackHead!!.getElement()
        stackHead!!.remove()
        stackSize--
        return popElement
    }

    /**
     * Remove element from Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param element that need to be removed from Stack
     * @return flag of action status
     */
    fun remove(element: E): Boolean {
        if (stackHead == null) return true

        if (stackHead?.getElement()!! == element) {
            stackHead!!.remove()
            stackSize--
            return true
        }

        var temp = stackHead

        while (temp?.next() != null) {
            if (temp.next()?.getElement() == element) {

                temp.remove()
                stackSize--
                return true
            }
            temp = temp.next()
        }
        return false
    }

    /**
     * Return first (Head element) from Stack without remove.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return first (Head element) from Stack.
     */
    fun peek(): E? {
        return this.stackHead.getElement()
    }

    /**
     * Checks if Stack is empty
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return flag of action status
     */
    fun isEmpty(): Boolean {
        return this.stackSize == 0
    }

    /**
     * Clears Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     */
    fun clear() {
        this.stackHead = null
        stackSize = 0
    }
}