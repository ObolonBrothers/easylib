import org.junit.Assert
import org.junit.Test

/**
 * Created by yurii on 6/1/17.
 */
class BinarySearchTreeTest {

    @Test fun isEmpty(): Unit {
        val tree = BinarySearchTree<Int>()
        Assert.assertEquals(true, tree.isEmpty)

        tree.insert(10)
        Assert.assertEquals(false, tree.isEmpty)
        tree.clear()
        Assert.assertEquals(true, tree.isEmpty)
    }

    @Test fun size(): Unit {
        val tree = BinarySearchTree<Double>()
        Assert.assertEquals(0, tree.size)

        for(i in 0..23)
            tree.insert(10000 * Math.random())

        Assert.assertEquals(24, tree.size)
        tree.clear()
        Assert.assertEquals(0, tree.size)
    }

    @Test fun searchAndInsert(): Unit {
        val tree = BinarySearchTree<Int>()
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

    @Test fun remove(): Unit {
        val tree = BinarySearchTree<Int>()
        Assert.assertEquals(false, tree.search(100))

        tree.insert(4)
        Assert.assertEquals(true, tree.remove(4))

        tree.insert(4)
        tree.insert(6)
        Assert.assertEquals(false, tree.remove(5))
        Assert.assertEquals(true, tree.remove(6))
        Assert.assertEquals(1, tree.size)

        tree.insert(425)
        tree.insert(-254)
        tree.insert(-9953458)
        tree.insert(5349596)
        Assert.assertEquals(true, tree.search(-9953458))
        Assert.assertEquals(true, tree.remove(-9953458))
        Assert.assertEquals(false, tree.search(-9953458))
        Assert.assertEquals(4, tree.size)
    }

    @Test fun minMax(): Unit {
        val tree = BinarySearchTree<Int>()
        Assert.assertEquals(null, tree.getMin())

        tree.insert(35)
        Assert.assertEquals(35, tree.getMin())
        Assert.assertEquals(35, tree.getMax())

        tree.insert(4)
        tree.insert(-74)
        Assert.assertEquals(-74, tree.getMin())
        Assert.assertEquals(35, tree.getMax())

        tree.remove(-74)
        Assert.assertEquals(4, tree.getMin())

        tree.insert(425)
        tree.insert(-254)
        tree.insert(-9953458)
        tree.insert(5349596)
        Assert.assertEquals(-9953458, tree.getMin())
        Assert.assertEquals(5349596, tree.getMax())
    }
}