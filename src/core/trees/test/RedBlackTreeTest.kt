
import org.junit.Test
import kotlin.test.*

/**
 *  @Author Yurii Yatsenko (@kyparus)
 *  Created on 05.12.17.
 */
class RedBlackTreeTest {

    val redBlackTree = RedBlackTree<Int, Int>()
    var maxBlackHeight = -1

    fun validate(node: RedBlackNode<Int, Int>? = redBlackTree.root, blackHeight: Int = 1): Boolean {
        if (node == null)
            return true

        if (node.isLeaf()) {
            if (maxBlackHeight == -1)
                maxBlackHeight = blackHeight

            if (maxBlackHeight != blackHeight)
                return false

            return true
        }

        if (!node.colorBlack) {

            if (node.left?.colorBlack == false || node.right?.colorBlack == false)
                return false
            else
                return validate(node.left, blackHeight + 1) && validate(node.right, blackHeight + 1)
        }
        else {
            val res1: Boolean
            val res2: Boolean

            if (node.left?.colorBlack == true)
                res1 = validate(node.left, blackHeight + 1)
            else
                res1 = validate(node.left, blackHeight)

            if (node.right?.colorBlack == true)
                res2 = validate(node.right, blackHeight + 1)
            else
                res2 = validate(node.right, blackHeight)

            return res1 && res2
        }
    }


    @Test fun findExistentKey() {
        for (i in 1..100)
            redBlackTree.insert(i, i)

        for (i in 1..100)
            assertEquals(redBlackTree.find(i), Pair(i, i))
    }

    @Test fun findNonexistentKey() {
        for (i in 1..100)
            redBlackTree.insert(i, i)

        assertNull(redBlackTree.find(0))
        assertNull(redBlackTree.find(101))
    }

    @Test fun insertNormal() {
        for (i in 1..100)
            redBlackTree.insert(i, i)

        for (i in 1..100)
            assertNotNull(redBlackTree.find(i))
    }

    @Test fun insertSaveStructure() {
        for (i in 1..100) {
            redBlackTree.insert(i, i)
            maxBlackHeight = -1
            assertTrue(validate())
        }
    }

    @Test fun insertReverseSaveStructure() {
        for (i in 100 downTo 1) {
            redBlackTree.insert(i, i)
            maxBlackHeight = -1
            assertTrue(validate())
        }
    }

    @Test fun rootColorAfterInsert() {
        for (i in 1..100) {
            redBlackTree.insert(i, i)
            assertEquals(redBlackTree.root?.colorBlack, true)
        }
    }

    @Test fun deleteNormal() {
        redBlackTree.insert(1, 1)
        redBlackTree.remove(1)
        assertNull(redBlackTree.root)

        for (i in 1..100)
            redBlackTree.insert(i, i)

        for (i in 30..60)
            redBlackTree.remove(i)

        for (i in 1..100) {

            if (i in 30..60)
                assertNull(redBlackTree.find(i))
            else
                assertNotNull(redBlackTree.find(i))
        }
    }

    @Test fun deleteNonexistentKey() {
        for (i in 1..49)
            redBlackTree.insert(i, i)

        for (i in 51..100)
            redBlackTree.insert(i, i)

        redBlackTree.remove(50)

        for (i in 1..100) {
            if (i == 50)
                assertNull(redBlackTree.find(i))
            else
                assertNotNull(redBlackTree.find(i))
        }
    }

}