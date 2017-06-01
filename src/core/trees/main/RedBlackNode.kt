/**
 * Implementation of RedBlackTree Node
 *
 * @author Yurii Yatsenko (@kyparus).
 * @since 0.2
 * @param K type of key.
 * @param V type of element which represents the data.
 */
class RedBlackNode<K: Comparable<K>, V>(var key: K, var value: V, var parent: RedBlackNode<K, V>? = null, var colorBlack: Boolean = false) {

    var left: RedBlackNode<K, V>? = null

    var right: RedBlackNode<K, V>? = null


    fun isLeaf(): Boolean = (this.left == null) && (this.right == null)

    /**
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.2
     * @return brother of the current node if it exists, otherwise null
     */
    fun brother(): RedBlackNode<K, V>? {
        if (this == this.parent?.left)
            return this.parent!!.right

        return this.parent?.left
    }

    /**
     * Performs left reconstruction of the tree
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.2
     */
    fun rotateLeft() {
        val rightChild = this.right ?: return
        val dad = this.parent

        this.swapColors(rightChild)
        rightChild.left?.parent = this
        this.right = rightChild.left
        rightChild.left = this

        when {
            this == dad?.left -> dad.left = rightChild
            this == dad?.right -> dad.right = rightChild
        }

        this.parent = rightChild
        rightChild.parent = dad
    }

    /**
     * Performs right reconstruction of the tree
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.2
     */
    fun rotateRight() {
        val leftChild = this.left ?: return
        val dad = this.parent

        this.swapColors(leftChild)
        leftChild.right?.parent = this
        this.left = leftChild.right
        leftChild.right = this

        when {
            this == dad?.left -> dad.left = leftChild
            this == dad?.right -> dad.right = leftChild
        }

        this.parent = leftChild
        leftChild.parent = dad
    }

    /**
     * Swaps colors with a given node
     *
     * @author Yurii Yatsenko (@kyparus)
     * @since 0.2
     * @param node2 to swap color with
     */
    private fun swapColors(node2: RedBlackNode<K, V>?) {
        val node1color = this.colorBlack

        if (node2 != null) {
            this.colorBlack = node2.colorBlack
            node2.colorBlack = node1color
        }
    }

}