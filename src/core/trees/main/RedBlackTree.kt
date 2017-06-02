/**
 * Implementation of Red-Black Tree Data Structure
 *
 * @author Yurii Yatsenko (@kyparus).
 * @since 0.2
 * @param K type of elements which map to the data.
 * @param V type of elements which represents the data.
 */
class RedBlackTree<K : Comparable<K>, V> : AbstractTree<K> {

    var root: RedBlackNode<K, V>? = null

    /**
     * Number of nodes in the tree
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     */
    private var nodesNumber = 0

    /**
     * Function to insert element to the tree
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.2
     * @param key of the Element to insert in this collection.
     * @param value of the Element to insert in this collection.
     * @see BinarySearchTree
     */
    fun insert(key: K, value: V?) {
        if (value == null)
            return
        var father: RedBlackNode<K, V>? = null
        var current: RedBlackNode<K, V>? = root

        while (current != null) {
            father = current

            when {
                key < current.key -> current = current.left

                key > current.key -> current = current.right

                key == current.key -> {
                    current.value = value!!
                    return
                }
            }
        }

        if (father == null) {
            root = RedBlackNode(key, value!!, father, true)
            return
        }

        if (key < father.key) {
            father.left = RedBlackNode(key, value!!, father, false)
            insertFixup(father.left)
        } else {
            father.right = RedBlackNode(key, value!!, father, false)
            insertFixup(father.right)
        }
        nodesNumber++
    }

    private fun insertFixup(node: RedBlackNode<K, V>?) {
        var uncle: RedBlackNode<K, V>?
        var current: RedBlackNode<K, V>? = node

        while (current?.parent?.colorBlack == false) {
            if (current.parent == current.parent?.parent?.left) {
                uncle = current.parent?.parent?.right

                when {
                    uncle?.colorBlack == false -> {
                        current.parent?.colorBlack = true
                        uncle.colorBlack = true
                        current.parent?.parent?.colorBlack = false
                        current = current.parent?.parent
                    }

                    current == current.parent?.right -> {
                        current = current.parent
                        if (current!!.parent?.parent == null) root = current.parent
                        current.rotateLeft()
                    }

                    current == current.parent?.left -> {
                        if (current.parent?.parent?.parent == null) root = current.parent
                        current.parent?.parent?.rotateRight()
                    }
                }
            } else {
                uncle = current.parent?.parent?.left

                when {
                    uncle?.colorBlack == false -> {
                        current.parent?.colorBlack = true
                        uncle.colorBlack = true
                        current.parent?.parent?.colorBlack = false
                        current = current.parent?.parent
                    }

                    current == current.parent?.left -> {
                        current = current.parent
                        if (current!!.parent?.parent == null) root = current.parent
                        current.rotateRight()
                    }

                    current == current.parent?.right -> {
                        if (current.parent?.parent?.parent == null) root = current.parent
                        current.parent?.parent?.rotateLeft()
                    }
                }
            }
        }
        root?.colorBlack = true
    }

    override fun insert(value: K) {
        insert(value, null)
    }

    /**
     * Find (Key, Value) Pair by Key
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param key of the Element to find in this collection.
     * @see RedBlackTree
     * @return Pair key value if key was found, otherwise null
     */
    fun find(key: K): Pair<K, V>? {
        val result = findNode(key)

        if (result == null)
            return null
        else
            return Pair(result.key, result.value)
    }

    private fun findNode(key: K): RedBlackNode<K, V>? {
        var current = root

        while (current != null) {
            if (key == current.key)
                return current

            if (key < current.key)
                current = current.left
            else
                current = current.right
        }
        return null
    }


    /**
     * Removes element by key from collection
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param key of the Element to delete from this collection.
     * @see RedBlackTree
     */
    fun remove(key: K) {
        val node = findNode(key) ?: return

        deleteNode(node)
        nodesNumber--
    }

    override fun search(value: K): Boolean {
        return find(value) != null
    }

    /**
     * Next are some functions to handle different
     * cases of element removal
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @param node to delete from this collection.
     * @see RedBlackTree
     */
    private fun deleteNode(node: RedBlackNode<K, V>) {
        val prev = max(node.left)

        when {
            (node.right != null && node.left != null) -> {
                node.key = prev!!.key
                node.value = prev.value
                deleteNode(prev)
                return
            }

            (node == root && node.isLeaf()) -> {
                root = null
                return
            }

            (!node.colorBlack && node.isLeaf()) -> {
                if (node == node.parent!!.left)
                    node.parent!!.left = null
                else
                    node.parent!!.right = null

                return
            }

            (node.colorBlack && node.left != null && !node.left!!.colorBlack) -> {
                node.key = node.left!!.key
                node.value = node.left!!.value
                node.left = null
                return
            }

            (node.colorBlack && node.right != null && !node.right!!.colorBlack) -> {
                node.key = node.right!!.key
                node.value = node.right!!.value
                node.right = null
                return
            }

            else -> {
                deleteCase1(node)
            }
        }

        if (node == node.parent!!.left)
            node.parent!!.left = null
        else
            node.parent!!.right = null
    }

    private fun deleteCase1(node: RedBlackNode<K, V>) {
        if (node.parent != null)
            deleteCase2(node)
    }

    private fun deleteCase2(node: RedBlackNode<K, V>) {
        val brother = node.brother()

        if (!brother!!.colorBlack) {
            if (node == node.parent!!.left)
                node.parent!!.rotateLeft()
            else if (node == node.parent!!.right)
                node.parent!!.rotateRight()

            if (root == node.parent)
                root = node.parent!!.parent
        }
        deleteCase3(node)
    }

    private fun deleteCase3(node: RedBlackNode<K, V>) {
        val brother = node.brother()

        val a: Boolean = brother!!.left == null || brother.left!!.colorBlack
        val b: Boolean = brother.right == null || brother.right!!.colorBlack

        if (a && b && brother.colorBlack && node.parent!!.colorBlack) {
            brother.colorBlack = false
            deleteCase1(node.parent!!)
        } else
            deleteCase4(node)
    }

    private fun deleteCase4(node: RedBlackNode<K, V>) {
        val brother = node.brother()

        val a: Boolean = brother!!.left == null || brother.left!!.colorBlack
        val b: Boolean = brother.right == null || brother.right!!.colorBlack

        if (a && b && brother.colorBlack && !node.parent!!.colorBlack) {
            brother.colorBlack = false
            node.parent!!.colorBlack = true
        } else
            deleteCase5(node)
    }

    private fun deleteCase5(node: RedBlackNode<K, V>) {
        val brother = node.brother()

        val a: Boolean = brother!!.left == null || brother.left!!.colorBlack
        val b: Boolean = brother.right == null || brother.right!!.colorBlack

        if (brother.colorBlack) {
            if (brother.left?.colorBlack == false && b && node == node.parent?.left)
                brother.rotateRight()
            else if (brother.right?.colorBlack == false && a && node == node.parent?.right)
                brother.rotateLeft()
        }
        deleteCase6(node)
    }

    private fun deleteCase6(node: RedBlackNode<K, V>) {
        val brother = node.brother()

        if (node == node.parent!!.left) {
            brother?.right?.colorBlack = true
            node.parent?.rotateLeft()
        } else {
            brother?.left?.colorBlack = true
            node.parent?.rotateRight()
        }

        if (root == node.parent)
            root = node.parent!!.parent
    }

    private fun nextSmaller(node: RedBlackNode<K, V>?): RedBlackNode<K, V>? {
        var smaller = node ?: return null

        if (smaller.left != null)
            return max(smaller.left!!)
        else if (smaller == smaller.parent?.left) {
            while (smaller == smaller.parent?.left)
                smaller = smaller.parent!!
        }
        return smaller.parent
    }

    /**
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.1
     * @return number of nodes in the tree
     */
    override val size: Int
        get() = nodesNumber

    override val isEmpty: Boolean
        get() = root == null

    /**
     * Remove all objects from collection.
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.2
     * @see RedBlackTree
     */
    override fun clear() {
        clear(root)
        root = null
        nodesNumber = 0
    }

    private fun clear(node: RedBlackNode<K, V>?) {
        if (node != null) {
            clear(node.left)
            clear(node.right)
            node.left = null
            node.right = null
            node.parent = null
        }
    }

    /**
     * Min and Max functions
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.2
     * @see RedBlackTree
     * @return min or max node by key
     */
    private fun min(node: RedBlackNode<K, V>?): RedBlackNode<K, V>? {
        if (node?.left == null)
            return node
        else
            return min(node.left)
    }

    private fun max(node: RedBlackNode<K, V>?): RedBlackNode<K, V>? {
        if (node?.right == null)
            return node
        else
            return max(node.right)
    }
}