/**
 * Implementation of classic simple one-side linked list
 *
 * @author Alex Syrotenko (@flystyle).
 * @since 0.1
 * @param E the type of elements which are consists in this data structure.
 * @see Node .
 * @see ClassicListIterator .
 */

class ClassicList<E> : MutableCollection<E>, Serializable {

    /**
     * Inner misc class for representation of container with data and next element reference.
     *
     * @author Alex Syrotenko (@flystyle)
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
         * @author Alex Syrotenko (@flystyle)
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
         * @author Alex Syrotenko (@flystyle)
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
            if (next != other.next) return false

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
     * @author Alex Syrotenko (@flystyle)
     * @since 0.1
     * @param current reference to list`s first node
     * @see ClassicList .
     * @constructor References first node for it`s inner tasks.
     */

    class ClassicListIterator <E> (private var current : Node<E>?) : MutableIterator<E> {

        /**
         * Check existence of next list node
         *
         * @author Alex Syrotenko (@flystyle)
         * @since 0.1
         * @return Boolean existence flag
         */

        override fun hasNext(): Boolean {
            return current?.next() !== null
        }

        /**
         *
         * @author Alex Syrotenko (@flystyle)
         * @since 0.1
         * @return Next node value
         */

        override fun next() : E {
            if (this.hasNext()) {
                current = current!!.next()
                return current!!.unwrap()
            }
            else throw IndexOutOfBoundsException()
        }

        /**
         *
         * Not implemented for current version.
         *
         * @author Alex Syrotenko (@flystyle)
         * @since 0.1.1
         * @see ClassicList
         * @see ClassicListIterator
         */

        override fun remove() {
            // comes in 0.1.1 version
            throw  UnsupportedOperationException()
        }
    }

    private var root : Node<E>? = null
        get() = this.root

    private var tail : Node<E>? = null
        get() = this.tail

    private var _size = 0

    override val size: Int
        get() = this._size


    override fun add(element: E): Boolean {
        val el = Node(element)

        if (tail == null) {
            root = el
            tail = el
        }
        else {
            tail?.accept(el)
            tail = el
        }
        _size++
        return true
    }

    override fun addAll(elements: Collection<E>): Boolean {
        elements.forEach { el -> this.add(el) }
        return true
    }

    override fun remove(element: E): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contains(element: E): Boolean {
        var temp = root
        while (temp?.next() != null) {
            if (temp.unwrap()!! == element)
                return true
            else temp = temp.next()
        }
        return false
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return if (elements.none { this.contains(it) }) false else false
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun iterator(): MutableIterator<E> {
        return ClassicListIterator(root)
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun serialize(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}