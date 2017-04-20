/**
 * Implementation of classic simple one-side linked list
 *
 * @author Alex Syrotenko (@FlyCreat1ve).
 * @since 0.1
 * @param E the type of elements which are consists in this data structure.
 * @see Node .
 * @see ClassicListIterator .
 */

open class ClassicList<E> : MutableCollection<E> {


    /**
     * Inner misc class for representation of container with data and next element reference.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param el valuable data object
     * @see ClassicList .
     * @see ClassicListIterator .
     * @constructor creates object with data and temporary null reference for next block.
     */

    class Node<T> (private val el: T) : Serializable {

        private var next : Node<T>? = null

        fun next(): Node<T>? = this.next

        /**
         * Connect node with new element to list
         *
         * @author Alex Syrotenko (@FlyCreat1ve)
         * @since 0.1
         * @param node new node with valuable data.
         * @see Node .
         */

        fun accept(node : Node<T>) {
            next = node
        }

        /**
         * Unwrap value of current node
         *
         * @author Alex Syrotenko (@FlyCreat1ve)
         * @since 0.1
         * @see Node
         * @return Node value
         */

        fun unwrap() : T = el

        override fun serialize(): String {
            return ""
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
            result = 31 * result + (next?.hashCode() ?: 0)
            return result
        }
    }

    /**
     * Iterator class for ClassicList
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param head reference to list`s first node
     * @see ClassicList .
     * @constructor References first node for it`s inner tasks.
     */

    class ClassicListIterator <E> (private var head: Node<E>?) : MutableIterator<E> {

        private var current : Node<E>? = null

        /**
         * Check existence of next list node
         *
         * @author Alex Syrotenko (@FlyCreat1ve)
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
         * @author Alex Syrotenko (@FlyCreat1ve)
         * @since 0.1
         * @return Next node value
         */

        override fun next() : E {
            if (this.current == null) {
                this.current = head
                return this.current?.unwrap()!!
            }

            if (!this.hasNext()) {
                throw IndexOutOfBoundsException()
            }
            current = current!!.next()
            return current!!.unwrap()
        }

        /**
         *
         * Not implemented for current version.
         *
         * @author Alex Syrotenko (@FlyCreat1ve)
         * @since 0.1.1
         * @see ClassicList
         * @see ClassicListIterator
         */

        override fun remove() {
            // will come in 0.1.1 version
            throw UnsupportedOperationException()
        }
    }


    protected var head : Node<E>? = null
    protected var tail : Node<E>? = null
    protected var _size = 0

    override val size: Int
        get() = this._size


    /**
     * Adds element of type E to this list
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param element Element which should be added to list
     * @see ClassicList .
     */

    override fun add(element: E): Boolean {
        val el = Node(element)

        if (tail == null) {
            head = el
            tail = el
        }
        else {
            tail?.accept(el)
            tail = el
        }
        _size++
        return true
    }

    /**
     * Addition of all elements from other collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param elements Collection which contains elements with same type.
     * @see ClassicList .
     * @see add .
     */

    override fun addAll(elements: Collection<E>): Boolean {
        elements.forEach { el -> this.add(el) }
        return true
    }

    /**
     * Remove element of type E from сurrent list, if it exists.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param element Element which should be removed from list.
     * @see ClassicList .
     */

    override fun remove(element: E): Boolean {
        if(head == null) return true

        if (head == tail) {
            head = null
            tail = null
            _size--
            return true
        }


        if (head?.unwrap()!! == element) {
            head = head?.next()
            _size--
            return true
        }

        var t = head

        while (t?.next() != null) {
            if (t.next()?.unwrap() == element) {
                if(tail == t.next()) {
                    tail = t
                }
                t.next()?.next()?.let { t?.next()?.accept(it) }
                _size--
                return true
            }
            t = t.next()
        }
        return false
    }

    /**
     * Removes of all elements which are similar with some element from other collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param elements Collection which contains elements with same type.
     * @see ClassicList .
     * @see remove .
     */

    override fun removeAll(elements: Collection<E>): Boolean {
        elements.forEach { el -> this.remove(el) }
        return true
    }

    /**
     * Check if element contains in this collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param element Element which are checking for existence in this collection.
     * @see ClassicList .
     */

    override fun contains(element: E): Boolean {

        if (head?.unwrap()!! == element)
            return true

        if (tail?.unwrap()!! == element)
            return true

        var temp = head
        while (temp?.next() != null) {
            if (temp.unwrap()!! == element)
                return true
            else temp = temp.next()
        }
        return false
    }

    /**
     * Check if all element of some collections contains in this collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param elements Elements which are checking for existence in this collection.
     * @see ClassicList .
     */

    override fun containsAll(elements: Collection<E>): Boolean {
        return !elements.none { this.contains(it) }
    }

    /**
     * Check if this collection doesn`t have elements.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @see ClassicList .
     */

    override fun isEmpty(): Boolean {
        return this._size == 0
    }

    /**
     * Remove all objects from collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @see ClassicList .
     */

    override fun clear() {
        this.head = null
        this.tail = null
        _size = 0
    }

    /**
     * Returns the iterator to the head element of collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @see ClassicList .
     */

    override fun iterator(): MutableIterator<E> {
        return ClassicListIterator(head)
    }

    /**
     * Removes of all elements which are not present in other collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @see ClassicList .
     */

    override fun retainAll(elements: Collection<E>): Boolean {
        val it = this.iterator()
        while (it.hasNext()) {
            val elem = it.next()
            if (!elements.contains(elem)) {
                this.remove(elem)
            }
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ClassicList<*>

        if (head?.equals(other.head) == false) return false
        if (tail?.equals(other.tail) == false) return false
        if (_size != other._size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = head?.hashCode() ?: 0
        result = 31 * result + (tail?.hashCode() ?: 0)
        result = 31 * result + _size
        return result
    }
    
}