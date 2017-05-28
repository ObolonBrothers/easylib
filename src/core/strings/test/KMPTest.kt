import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.SecureRandom

/**
 * @Author Vasyl Antoniuk (@toniukan)
 */

class KMPTest {
    @Test fun simpleCase(): Unit {
        try {
            val kmp = KMP("")
        } catch (e: IllegalArgumentException) {
            assertEquals(e.message, "The parameter should be a non-empty string")
        }
        var kmp = KMP("a")
        assertEquals(-1, kmp.search(""))
        assertEquals(0, kmp.search("a"))
        assertEquals(-1, kmp.search("b"))
        assertEquals(2, kmp.search("bba"))
        assertEquals(1, kmp.search("baba"))

        kmp = KMP("abba")
        assertEquals(-1, kmp.search("a"))
        assertEquals(-1, kmp.search("abab"))
        assertEquals(0, kmp.search("abba"))
        assertEquals(1, kmp.search("aabba"))
        assertEquals(-1, kmp.search("acbbaa"))
        return
    }

    fun randomString(len: Int, minChar: Int, maxChar: Int, random: SecureRandom): String {
        var res : String = ""
        for (i in 1..len) {
            res += random.nextInt(maxChar - minChar + 1) + maxChar
        }
        return res
    }

    @Test fun smallCase(): Unit {
        val random = SecureRandom()
        for (i in 1..5) {
            val patternLen = random.nextInt(3) + 1
            val pattern = randomString(patternLen, 0, 5, random)

            val kmp = KMP(pattern)

            for (j in 1..50) {
                val text = randomString(10 * random.nextInt(patternLen), 0, 5, random)
                assertEquals(text.indexOf(pattern), kmp.search(text))
            }
        }
    }

    @Test fun allEqual(): Unit {
        val random = SecureRandom()
        for (i in 1..5) {
            val patternLen = random.nextInt(10) + 1
            val pattern = randomString(patternLen, 5, 5, random)

            val kmp = KMP(pattern)

            for (j in 1..50) {
                val text = randomString(10 * random.nextInt(patternLen), 5, 5, random)
                assertEquals(text.indexOf(pattern), kmp.search(text))
            }
        }
    }

    @Test fun largeRandomAB(): Unit {
        val random = SecureRandom()
        for (i in 1..5) {
            val patternLen = random.nextInt(40) + 1
            val pattern = randomString(patternLen, 5, 6, random)

            val kmp = KMP(pattern)

            for (j in 1..50) {
                val text = randomString(10 * random.nextInt(patternLen), 5, 6, random)
                assertEquals(text.indexOf(pattern), kmp.search(text))
            }
        }
    }

    @Test fun largeRandomAZ(): Unit {
        val random = SecureRandom()
        for (i in 1..5) {
            val patternLen = random.nextInt(40) + 1
            val pattern = randomString(patternLen, 0, 256, random)

            val kmp = KMP(pattern)

            for (j in 1..50) {
                val text = randomString(10 * random.nextInt(patternLen), 0, 256, random)
                assertEquals(text.indexOf(pattern), kmp.search(text))
            }
        }
    }
}