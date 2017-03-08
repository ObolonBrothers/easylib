/**
 * @Author is flystyle
 *  Created on 04.03.17.
 */
class ClassicList<E> : MutableCollection<E>, Serializable {

    private class Node<E> (private val el: E) : Serializable {
        private var next : Node<E>? = null

        fun next(): Node<E>? = this.next
        fun accept(node : Node<E>) {
            next = node
        }
        fun unwrap() : E = el

        override fun serialize(): String {
            return ""
        }
    }

    private var root : Node<E>? = null
    private var tail : Node<E>? = null
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun iterator(): MutableIterator<E> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun serialize(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun testOutput() {
        var i = 0
        var temp = root
        while (i < this._size) {
            println(temp?.unwrap())
            temp = temp?.next()
            ++i
        }
    }
}