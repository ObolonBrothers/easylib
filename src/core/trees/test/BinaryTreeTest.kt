import org.junit.Assert
import org.junit.Test

/**
 * @Author Yurii Yatsenko (@kyparus)
 *  Created on 05.08.17.
 */
class BinaryTreeTest {

    @Test fun isEmpty(): Unit {
        val tree = BinaryTree<Int>()
        Assert.assertEquals(true, tree.isEmpty)

        tree.insert(10)
        Assert.assertEquals(false, tree.isEmpty)
        tree.clear()
        Assert.assertEquals(true, tree.isEmpty)
    }

    @Test fun size(): Unit {
        val tree = BinaryTree<Double>()
        Assert.assertEquals(0, tree.size)

        for(i in 0..23)
            tree.insert(10000 * Math.random())

        Assert.assertEquals(24, tree.size)
        tree.clear()
        Assert.assertEquals(0, tree.size)
    }

    @Test fun searchAndInsert(): Unit {
        val tree = BinaryTree<Int>()
        Assert.assertEquals(false, tree.search(100))

        tree.insert(4)
        Assert.assertEquals(true, tree.search(4))

        tree.insert(42349234)
        tree.insert(-943529)
        tree.insert(78434599)
        Assert.assertEquals(true, tree.search(42349234))
        Assert.assertEquals(false, tree.search(100000))
        Assert.assertEquals(true, tree.search(-943529))
    }
}