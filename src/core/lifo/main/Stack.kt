/**
 * A stack is an abstract data type that serves as a collection of elements, with two principal operations: push,
 * which adds an element to the collection, and pop, which removes the most recently added element that was not yet
 * removed.
 *
 * @author Volodymyr Semenovych (@akisemenovych).
 * @since 0.1
 * @param E the type of elements which are consists in this data structure.
 * @see Node .
 * @see StackIterator .
 */

class Stack<E> (private var stackHead : Node<E>? = null): MutableCollection<E> {

    /**
     * Inner misc class for representation of container with data and next element reference.
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param el valuable data object
     * @see Stack .
     * @see StackIterator .
     * @constructor creates object with data and temporary null reference for next block.
     */
    class Node<E> (private var el: E, private var next : Node<E>) {

        /**
         * Unpack element from object Node
         */
        fun getElement() : E = el

        /**
         * Get link on connected Node
         */
        fun next() : Node<E>? = next

        /**
         * Set link in connected Node
         */
        fun setNext(nextEl : Node<E>) {
            this.next = nextEl
        }

        /**
         * Remove current Node
         */
        fun remove() {
            if (el != null) {
                this.el = next.getElement()
                if (next.next()?.equals(null) as Boolean)
                this.next = next.next
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
            result = 31 * result + (next.hashCode() ?: 0)
            return result
        }
    }

    /**
     * Iterator class for Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param head reference to list`s first node
     * @see Stack .
     * @constructor References first node for it`s inner tasks.
     */
    class StackIterator <E> (private var head: Node<E>?) : MutableIterator<E> {

        private var current : Node<E>? = null

        /**
         * Checks existences of next Stack Node
         *
         * @author Volodymyr Semenovych (@akisemenovych).
         * @since 0.1
         * @return Boolean existence flag
         */
        override fun hasNext(): Boolean {
            if (current == null && head !== null)
                return true
            return current?.next() !== null
        }

        /**
         *
         * @author Volodymyr Semenovych (@akisemenovych).
         * @since 0.1
         * @return Next linked Node
         */
        override fun next() : E {
            if (this.current == null) {
                this.current = head
                return this.current?.getElement()!!
            }

            if (!this.hasNext()) {
                throw IndexOutOfBoundsException()
            }
            current = current!!.next()
            return current!!.getElement()
        }

        /**
         *
         * Removes current Node from Stack
         * @author Volodymyr Semenovych (@akisemenovych).
         * @since 0.1
         */
        override fun remove() {
            this.current!!.remove()
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
    override val size: Int
        get() = this.stackSize


    /**
     * Adds element into Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param element that needs to be added into Stack
     * @return flag of action status
     */
    override fun add(element : E): Boolean {
        var current : Node<E>? = null
        if (current != null) {
            stackSize += 1
            current.setNext(stackHead!!)
            stackHead = current
            return true
        }
        return false
    }

    /**
     * Adds elements collection into Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param elements collection that need to be added into Stack
     * @return flag of action status
     */
    override fun addAll(elements: Collection<E>): Boolean {
        elements.forEach { el -> this.add(el) }
        return true
    }

    /**
     * Remove element from Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param elements collection that need to be added into Stack
     * @return flag of action status
     */
    override fun remove(element: E): Boolean {
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
     *
     * Remove elements collection from Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param elements collection that need to be removed from Stack
     * @return flag of action status
    */
    override fun removeAll(elements: Collection<E>): Boolean {
        elements.forEach { el -> this.remove(el) }
        return true
    }

    /**
     * Checks if element contains in Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param element
     * @return flag of action status
     */
    override fun contains(element: E): Boolean {

        if (stackHead?.getElement()!! == element)
            return true

        var temp = stackHead
        while (temp?.next() != null) {
            if (temp.getElement()!! == element)
                return true
            else temp = temp.next()
        }
        return false
    }

    /**
     * Checks if elements collection contains in Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param elements
     * @return flag of action status
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        return !elements.none { this.contains(it) }
    }

    /**
     * Stacks iterator
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return Stacks iterator
     */
    override fun iterator(): MutableIterator<E> {
        return StackIterator(stackHead)
    }

    /**
     * Checks if Stack is empty
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @return flag of action status
     */
    override fun isEmpty(): Boolean {
        return this.stackSize == 0
    }

    /**
     * Clears Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     */
    override fun clear() {
        this.stackHead = null
        stackSize = 0
    }

    /**
     * Removes elements collection from Stack
     *
     * @author Volodymyr Semenovych (@akisemenovych).
     * @since 0.1
     * @param elements
     * @return flag of action status
     */
    override fun retainAll(elements: Collection<E>): Boolean {
        val localIterator = this.iterator()
        while (localIterator.hasNext()) {
            val elem = localIterator.next()
            if (!elements.contains(elem)) {
                this.remove(elem)
            }
        }
        return true
    }
}