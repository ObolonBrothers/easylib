/**
 * Implementation of BinaryTree Data Structure
 *
 * @author Yurii Yatsenko (@kyparus).
 * @since 0.1
 * @param E type of elements which represents the data.
 */

open class BinaryTree <E> : AbstractTree<E> {

    /**
     * Inner misc class for representation of container with data and children nodes.
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param value valuable data object
     * @see BinaryTree .
     * @constructor creates object with value.
     */
  
    internal class BTNode<T> (val value: T) : Serializable {

        override fun serialize(): String {
            return ""
        }

        /**
         * Left and right children of the node.
         *
         * @author Yurii Yatsenko (@kyparus)
         * @since 0.1
         */
        var left: BTNode<T>? = null

        var right: BTNode<T>? = null
    }

    /**
     * Root of the tree.
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     */
    private var root: BTNode<E>? = null

    /**
     * Number of nodes in the tree
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     */
    private var nodesNumber = 0

    /**
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @return number of nodes in the tree
     */
    override var size: Int = 0
        get() = nodesNumber

    /**
     * Remove all objects from collection.
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @see BinaryTree .
     */
    override fun clear() {
        clear(root)
        root = null
        nodesNumber = 0
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
     * Function to insert element to the tree
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param value Element to insert in this collection.
     * @see BinaryTree
     */
    override fun insert(value: E) {
        root = insert(root, value)
        nodesNumber++
    }

    private fun insert(_node: BTNode<E>?, value: E): BTNode<E> {
        var node = _node
        if (node == null) {
            node = BTNode<E>(value)
        } else {
            if (node.right == null)
                node.right = insert(node.right, value)
            else
                node.left = insert(node.left, value)
        }
        return node
    }

    /**
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param value Element to insert in this collection.
     * @see BinaryTree
     * @return 'true' if value is in the collection, otherwise 'false'
     */
    override fun search(value: E): Boolean {
        return search(root, value)
    }

    private fun search(_root: BTNode<E>?, value: E): Boolean {
        if (_root?.value == value)
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
     * Removes element by value from collection
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param value Element to delete from this collection.
     * @see BinaryTree
     * @return 'true' if it was deleted, otherwise 'false'
     */
    fun remove(value: E): Boolean {
        var rememberedNodesNumber = nodesNumber
        root = remove(root, value)
        return rememberedNodesNumber != nodesNumber
    }

    private fun remove(node: BTNode<E>?, value: E): BTNode<E>? {
        if (node?.value == value){
            var leaf = getLeaf(node)
            nodesNumber--
            if (leaf == node)
                return null
            leaf?.left = node?.left
            leaf?.right = node?.right

            return leaf
        }
        var rememberedNodesNumber = nodesNumber
        node?.left = remove(node?.left, value)
        if (rememberedNodesNumber == nodesNumber)
            node?.right = remove(node?.right, value)

        return node
    }

    private fun getLeaf(node: BTNode<E>?): BTNode<E>? {
        if (node == null || (node?.left == null && node?.right == null)) {
            return node
        }
        if (node.left != null)
            return getLeaf(node.left)
        else
            return getLeaf(node.right)

    }
    /**
     * Check if this collection doesn`t have elements.
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @see BinaryTree .
     */
    override val isEmpty: Boolean
        get() = root == null

}

