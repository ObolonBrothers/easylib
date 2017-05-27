import org.junit.Assert
import org.junit.Test

/**
 * Created by akisemenovych on 02.05.2017.
 * @See Stack
 */

class StackTest {
        private val stack = Stack<Int>()
        private val subStack = Stack<Int>()

        @Test fun addToStack() : Unit {
            val len = 20
            for (i in IntRange(1, len))
                stack.push(i)

            Assert.assertEquals(stack.size, len)
            return
        }

        @Test fun checkContains(): Unit {
            val len = 20
            for (i in IntRange(1, len))
                stack.push(i)

            for (i in IntRange(1, len/2))
                subStack.push(i)

            Assert.assertEquals(stack.contains(10), true)
            Assert.assertEquals(stack.contains(30), false)
        }

        @Test fun removeFromStack() : Unit {
            val len = 20
            for (i in IntRange(1, len))
                stack.push(i)

            for (i in IntRange(1, len))
                stack.pop()

            Assert.assertEquals(stack.size, 0)
        }

        @Test fun clearStack() : Unit {
            val len = 20
            for (i in IntRange(1, len))
                stack.push(i)

            stack.clear()
            Assert.assertEquals(stack.size, 0)
        }
}