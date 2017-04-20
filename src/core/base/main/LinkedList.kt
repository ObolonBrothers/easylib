/**
 * Implementation of double-side linked list.
 *
 * Developers declare that all standart operations from `MutableCollection`
 * interface will be used for tail (or back side) of the list.
 * We marked methods which are using the head of linked list as "`methodnameFront`"
 * or "`methodnameToFront`".
 *
 *
 * @author Alex Syrotenko (@FlyCreat1ve).
 * @since 0.1
 * @param E the type of elements which are consists in this data structure.
 * @see TwoSideNode .
 * @see LinkedListIterator .
 * @see LinkedListBackIterator .
 */

open class LinkedList<E> : MutableCollection<E> {

    class TwoSideNode <T> (private val el : T) {

        private var next : TwoSideNode<T>? = null
        fun next() = next

        private var prev : TwoSideNode <T>? = null
        fun prev() = prev

        /**
         * Connect node`s references with last element of list
         * In case of adding new element to back side of list (traditionally).
         *
         * @author Alex Syrotenko (@FlyCreat1ve)
         * @since 0.1
         * @param node new node with valuable data.
         * @see TwoSideNode .
         */

        fun acceptTail(node : TwoSideNode<T>?) {
            next = node
            node?.prev = this
        }

        /**
         * Connect node`s `prev` references with first element of list
         * In case of adding new element to front side of list.
         *
         * @author Alex Syrotenko (@FlyCreat1ve)
         * @since 0.1
         * @param node new node with valuable data.
         * @see TwoSideNode .
         */

        fun acceptHead(node : TwoSideNode<T>?) {
            prev = node
            node?.next = this
        }

        /**
         * Unwrap value of current node
         *
         * @author Alex Syrotenko (@FlyCreat1ve)
         * @since 0.1
         * @see TwoSideNode
         * @return TwoSideNode value
         */

        fun unwrap() : T = el

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other?.javaClass != javaClass) return false
            other as TwoSideNode<*>

            if (el != other.el) return false

            return true
        }

        override fun hashCode(): Int {
            var result = el?.hashCode() ?: 0
            result = 31 * result + (next?.hashCode() ?: 0)
            return result
        }

        override fun toString(): String {
            return "TS-Node(el = $el)"
        }
    }

    class LinkedListIterator <E> (private var head: TwoSideNode<E>?) : MutableIterator<E> {

        private var current : TwoSideNode<E>? = null

        /**
         * Check existence of next node of list.
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
         * @see LinkedList
         * @see LinkedListIterator
         */

        override fun remove() {
            // will come in 0.1.1 version
            throw UnsupportedOperationException()
        }
    }

    class LinkedListBackIterator <E> (private var tail: TwoSideNode<E>?) : MutableIterator<E> {

        private var current : TwoSideNode<E>? = null

        /**
         * Check existence of next node of list.
         *
         * @author Alex Syrotenko (@FlyCreat1ve)
         * @since 0.1
         * @return Boolean existence flag
         */

        override fun hasNext(): Boolean {
            if (current == null && tail !== null)
                return true
            return current?.prev() !== null
        }

        /**
         *
         * @author Alex Syrotenko (@FlyCreat1ve)
         * @since 0.1
         * @return Next node value
         */

        override fun next() : E {
            if (this.current == null) {
                this.current = tail
                return this.current?.unwrap()!!
            }

            if (!this.hasNext()) {
                throw IndexOutOfBoundsException()
            }
            current = current!!.prev()
            return current!!.unwrap()
        }

        /**
         *
         * Not implemented for current version.
         *
         * @author Alex Syrotenko (@FlyCreat1ve), Vasia Antoniuk (@toniukan)
         * @since 0.1.1
         * @see LinkedList
         * @see LinkedListIterator
         * @see LinkedListBackIterator
         */

        override fun remove() {
            // will come in 0.1.1 version
            throw UnsupportedOperationException()
        }
    }

    // TODO: Discuss about idea of double side iterator.

    protected var _size = 0
    protected var head : TwoSideNode<E>? = null
    protected var tail : TwoSideNode<E>? = null

    override val size: Int
        get() = _size

    /**
     * Adds element of type E to tail (back side) of this list
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param element Element which should be added to list
     * @see LinkedList .
     */

    override fun add(element: E): Boolean {
        val el = TwoSideNode(element)

        if (tail == null) {
            head = el
            tail = el
        }
        else {
            tail?.acceptTail(el)
            tail = el
        }
        _size++
        return true
    }

    /**
     * Adds element of type E to head (front side) of this list
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param element Element which should be added to list
     * @see LinkedList .
     */

    fun addFront(element: E) : Boolean {
        val el = TwoSideNode(element)

        if (head == null) {
            head = el
            tail = el
        }
        else {
            head?.acceptHead(el)
            head = el
        }
        _size++
        return true
    }

    /**
     * Addition of all elements from other collection to back side of list.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param elements Collection which contains elements with same type.
     * @see LinkedList .
     * @see add .
     */

    override fun addAll(elements: Collection<E>): Boolean {
        elements.forEach { el -> this.add(el) }
        return true
    }

    /**
     * Addition of all elements from other collection to back side of list.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param elements Collection which contains elements with same type.
     * @see LinkedList .
     * @see addFront .
     */

    fun addAllToFront(elements: Collection<E>): Boolean {
        elements.forEach { el -> this.addFront(el) }
        return true
    }

    /**
     * Check if collection contains the element.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param element Element which are checking for existence in this collection.
     * @see LinkedList .
     */

    override fun contains(element: E): Boolean {
        if (this.isEmpty())
            return false

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
     * Check if the list contains each element of passed collection
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param elements Elements which are checking for existence in this collection.
     * @see LinkedList .
     */

    override fun containsAll(elements: Collection<E>): Boolean {
        return !elements.none { this.contains(it) }
    }

    /**
     * Remove all objects from collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @see LinkedList .
     */

    override fun clear() {
        this.head = null
        this.tail = null
        _size = 0
    }

    override fun isEmpty(): Boolean {
        return _size == 0
    }

    /**
     * Returns the iterator to the head element of collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @see LinkedList .
     */

    override fun iterator(): MutableIterator<E> {
        return LinkedListIterator(head)
    }

    /**
     * Returns the backward iterator to the tail element of collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @see LinkedList .
     * @return tail-to-head directed iterator.
     */

    fun backwardIterator(): MutableIterator<E> {
        return LinkedListBackIterator(tail)
    }

    /**
     * Remove element of type E from сurrent list, if it exists.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @param element Element which should be removed from list.
     * @see LinkedList .
     */

    override fun remove(element: E): Boolean {
        if(head == null)
            return false

        if (head == tail && head?.unwrap() == element) {
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
                else {
                    t.acceptTail(t.next()!!.next())
                }
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
     * @param elements Collection which contains elements of type E.
     * @see LinkedList .
     * @see remove .
     */

    override fun removeAll(elements: Collection<E>): Boolean {
        elements.forEach { el -> this.remove(el) }
        return true
    }

    /**
     * Removes of all elements which are not present in other collection.
     *
     * @author Alex Syrotenko (@FlyCreat1ve)
     * @since 0.1
     * @see LinkedList .
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
        if (other !is LinkedList<*>) return false

        if (head?.equals(other.head) == false) return false
        if (tail?.equals(other.tail) == false) return false
        if (_size != other._size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _size
        result = 31 * result + (head?.hashCode() ?: 0)
        result = 31 * result + (tail?.hashCode() ?: 0)
        return result
    }

    companion object {
        inline fun <E> linkedListOf(vararg elements : E) : LinkedList<E>? {
            val list = LinkedList<E>()
            list += elements

            return list
        }
    }

}