/**
 * Implementation of BinaryTree Data Structure
 *
 * @author Yurii Yatsenko (@kyparus).
 * @since 0.1
 * @param E type of elements which represents the data.
 */
class BinaryTree <E> : AbstractTree<E> {

    /**
     * Inner misc class for representation of container with data and children nodes.
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param el valuable data object
     * @see ClassicList .
     * @see ClassicListIterator .
     * @constructor creates object with data.
     */
    class BTNode<T> (val data: T) : Serializable {

        override fun serialize(): String {
            return ""
        }

        /**
         * Left child node
         */
        var left: BTNode<T>? = null

        /**
         * Right child node
         */
        var right: BTNode<T>? = null
    }

    /**
     * Root of the tree
     */
    private var root: BTNode<E>? = null

    /**
     * Number of nodes in the tree
     */
    private var nodes_number = 0

    /**
     * Function to get number of nodes in the tree
     */
    override var size: Int = 0
        get() = nodes_number


    /**
     * Clear the tree
     */
    override fun clear() {
        clear(root)
        root = null
        nodes_number = 0
    }

    private fun clear(node: BTNode<E>?){
        if (node != null){
            clear(node.left)
            clear(node.right)
            node.left = null
            node.right = null
        }
    }

    /**
     * Function to insert data to the tree
     */
    override fun insert(data: E) {
        root = insert(root, data)
        nodes_number++
    }

    /* Function to insert data recursively */
    private fun insert(_node: BTNode<E>?, data: E): BTNode<E> {
        var node = _node
        if (node == null) {
            node = BTNode<E>(data)
        } else {
            if (node.right == null)
                node.right = insert(node.right, data)
            else
                node.left = insert(node.left, data)
        }
        return node
    }

    /**
     * Returns 'true' if value is in the collection, otherwise 'false'
     */
    override fun search(value: E): Boolean {
        return search(root, value)
    }

    /* Function to search for an element recursively */
    private fun search(_root: BTNode<E>?, value: E): Boolean {
        if (_root?.data == value)
            return true
        if (_root?.left != null)
            if (search(_root?.left, value))
                return true
        if (_root?.right != null)
            if (search(_root?.right, value))
                return true
        return false
    }

    /**
     *  Function to check if tree is empty
     */
    override val isEmpty: Boolean
        get() = root == null

}

