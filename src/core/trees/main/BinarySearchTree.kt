/**
 * Implementation of BinarySearchTree Data Structure
 *
 * @author Yurii Yatsenko (@kyparus).
 * @since 0.1
 * @param E type of elements which represents the data.
 */
class BinarySearchTree<E : Comparable<E>> : AbstractTree<E> {

    /**
     * Inner misc class for representation of container with data and children nodes.
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param value valuable data object
     * @see BinarySearchTree .
     * @constructor creates object with value.
     */
    internal class BSTNode<T>(val value: T) : Serializable {

        override fun serialize(): String {
            return ""
        }

        /**
         * Left and right children of the node.
         *
         * @author Yurii Yatsenko (@kyparus)
         * @since 0.1
         */
        var left: BSTNode<T>? = null

        var right: BSTNode<T>? = null
    }

    /**
     * Root of the tree.
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     */
    private var root: BSTNode<E>? = null

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
     * @see BinarySearchTree .
     */
    override fun clear() {
        clear(root)
        root = null
        nodesNumber = 0
    }

    private fun clear(node: BSTNode<E>?) {
        if (node != null) {
            clear(node.left)
            clear(node.right)
            node.left = null
            node.right = null
        }
    }

    override val isEmpty: Boolean
        get() = root == null

    /**
     * Function to insert element to the tree
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param value Element to insert in this collection.
     * @see BinarySearchTree
     */
    override fun insert(value: E) {
        val newNode = BSTNode(value)
        if (root == null) {
            root = newNode
            nodesNumber++
            return
        }
        var current = root
        var parent: BSTNode<E>? = null
        while (true) {
            parent = current
            if (value < current!!.value) {
                current = current.left
                if (current == null) {
                    parent!!.left = newNode
                    nodesNumber++
                    return
                }
            } else {
                current = current.right
                if (current == null) {
                    parent!!.right = newNode
                    nodesNumber++
                    return
                }
            }
        }
    }

    /**
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param value Element to insert in this collection.
     * @see BinarySearchTree
     * @return 'true' if value is in the collection, otherwise 'false'
     */
    override fun search(value: E): Boolean {
        var current = root
        while (current != null) {
            if (current.value == value) {
                return true
            } else if (current.value > value) {
                current = current.left
            } else {
                current = current.right
            }
        }
        return false
    }

    /**
     * Removes element by value from collection
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param value Element to delete from this collection.
     * @see BinarySearchTree
     * @return 'true' if it was deleted, otherwise 'false'
     */
    fun remove(value: E): Boolean {
        if(root == null)
            return false
        var parent = root
        var current = root
        var isLeftChild = false
        while (current?.value != value) {
            parent = current
            if (current!!.value > value) {
                isLeftChild = true
                current = current.left
            } else {
                isLeftChild = false
                current = current.right
            }
            if (current == null) {
                return false
            }
        }

        //if we are here that means we have found the node
        //Case 1: if node to be deleted has no children
        if (current?.left == null && current?.right == null) {
            if (current == root) {
                root = null;
            }
            if (isLeftChild == true) {
                parent?.left = null;
            } else {
                parent?.right = null;
            }
        } //Case 2 : if node to be deleted has only one child
        else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent?.left = current.left;
            } else {
                parent?.right = current.left;
            }
        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                parent?.left = current.right;
            } else {
                parent?.right = current.right;
            }
        } // Case 2 : if node to be deleted has two children
        else if (current.left != null && current.right != null) {

            //now we have found the minimum element in the right sub tree
            val successor = getSuccessor (current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent?.left = successor;
            } else {
                parent?.right = successor;
            }
            successor?.left = current.left;
        }
        nodesNumber--
        return true
    }

    /**
     * Get next node in ascending order
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @see BinarySearchTree
     */
    private fun getSuccessor(node: BSTNode<E>?) : BSTNode<E>? {
        var successsor: BSTNode<E>? = null
        var successsorParent: BSTNode<E>? = null
        var current = node?.right
        while (current != null) {
            successsorParent = successsor
            successsor = current
            current = current!!.left
        }
        //check if successor has the right child, it cannot have left child for sure
        // if it does have the right child, add it to the left of successorParent.
        //		successorParent
        if (successsor !== node?.right) {
            successsorParent!!.left = successsor!!.right
            successsor!!.right = node?.right
        }
        return successsor
    }

    /**
     * getMin/Max funcs to get min/max values from collection
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @see BinarySearchTree
     * @return min/max value
     */
    fun getMin() : E? {
        return getEdgeValue(true)
    }

    fun getMax() : E? {
        return getEdgeValue(false)
    }

    /**
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param min defines what we want to get
     * @see BinarySearchTree
     * @return min value if param 'min' is true otherwise max value
     */
    private fun getEdgeValue(min : Boolean) : E? {
        var current = root
        var lastValue = root?.value
        while (current != null) {
            lastValue = current.value
            if (min)
                current = current.left
            else
                current = current.right
        }
        return lastValue
    }

}