import org.junit.Assert
import org.junit.Test

/**
 * Created by akisemenovych on 02.05.2017.
 * @See Stack
 */

class StackTest {
    class ClassicListTest {
        private val stack = Stack<Int>()
        private val subStack = Stack<Int>()
        private val checkStack = Stack<Int>()


        @Test fun addToStack() : Unit {
            val len = 20
            for (i in IntRange(1, len))
                stack.add(i)

            Assert.assertEquals(stack.size, len)
            return
        }

        @Test fun checkContains(): Unit {
            val len = 20
            for (i in IntRange(1, len))
                stack.add(i)

            for (i in IntRange(1, len/2))
                subStack.add(i)

            Assert.assertEquals(stack.contains(10), true)
            Assert.assertEquals(stack.contains(30), false)
            Assert.assertEquals(stack.containsAll(subStack), true)

        }

        @Test fun removeFromClassicList() : Unit {
            val len = 20
            for (i in IntRange(1, len))
                stack.add(i)

            for (i in IntRange(1, len))
                stack.remove(i)

            Assert.assertEquals(stack.size, 0)
        }

        @Test fun clearList() : Unit {
            val len = 20
            for (i in IntRange(1, len))
                stack.add(i)

            stack.clear()
            Assert.assertEquals(stack.size, 0)
        }

        @Test fun filterTest() : Unit {
            val len = 20
            for (i in IntRange(1, len))
                stack.add(i)

            println(stack.filter { it -> it % 2 == 0 })
        }

        @Test fun retainsList() : Unit {
            val len = 10
            for (i in IntRange(1, len))
                stack.add(i)

            for (i in IntRange(len/2, len)) {
                subStack.add(i)
                checkStack.add(i)
            }

            stack.retainAll(subStack)

            Assert.assertEquals(stack.equals(checkStack), true)
        }

    }
}