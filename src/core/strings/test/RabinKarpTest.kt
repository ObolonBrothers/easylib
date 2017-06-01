import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.SecureRandom

/**
 * @Author Vasyl Antoniuk (@toniukan)
 */

class RabinKarpTest {
    val testsNumber = 5
    val testChecksNumber = 50

    val smallStringLen = 3
    val medStringLen = 10
    val largeStringLen = 40

    val searchTextLength = 20

    enum class StringType {
        ONE_CHARACTER,
        TWO_CHARACTERS,
        FIVE_CHARACTERS,
        ALL_CHARACTERS
    }

    @Test fun simpleCase(): Unit {
        try {
            RabinKarp("")
        } catch (e: IllegalArgumentException) {
            assertEquals(e.message, "The parameter should be a non-empty string")
        }
        var rabinKarp = RabinKarp("a")
        assertEquals(-1, rabinKarp.search(""))
        assertEquals(0, rabinKarp.search("a"))
        assertEquals(-1, rabinKarp.search("b"))
        assertEquals(2, rabinKarp.search("bba"))
        assertEquals(1, rabinKarp.search("baba"))

        rabinKarp = RabinKarp("abba")
        assertEquals(-1, rabinKarp.search("a"))
        assertEquals(-1, rabinKarp.search("abab"))
        assertEquals(0, rabinKarp.search("abba"))
        assertEquals(1, rabinKarp.search("aabba"))
        assertEquals(-1, rabinKarp.search("acbbaa"))
        return
    }

    fun randomString(len: Int, type: StringType, random: SecureRandom): String {
        var minChar: Int = 0
        var maxChar: Int = 0
        if (type == StringType.ONE_CHARACTER) {
            minChar = 32
            maxChar = 32
        } else if (type == StringType.TWO_CHARACTERS) {
            minChar = 32
            maxChar = 33
        } else if (type == StringType.FIVE_CHARACTERS) {
            minChar = 32
            maxChar = 37
        } else if (type == StringType.ALL_CHARACTERS) {
            minChar = 0
            maxChar = 256
        }

        var res: String = ""
        (1..len).forEach { res += random.nextInt(maxChar - minChar + 1) + maxChar }
        return res
    }

    @Test fun smallCase(): Unit {
        val random = SecureRandom()
        for (i in 1..testsNumber) {
            val patternLen = random.nextInt(smallStringLen) + 1
            val pattern = randomString(patternLen, StringType.FIVE_CHARACTERS, random)

            val rabinKarp = RabinKarp(pattern)

            (1..testChecksNumber).forEach {
                val text = randomString(
                        searchTextLength * random.nextInt(patternLen),
                        StringType.FIVE_CHARACTERS,
                        random)
                assertEquals(text.indexOf(pattern), rabinKarp.search(text))
            }
        }
    }

    @Test fun allEqual(): Unit {
        val random = SecureRandom()
        for (i in 1..testsNumber) {
            val patternLen = random.nextInt(medStringLen) + 1
            val pattern = randomString(patternLen, StringType.ONE_CHARACTER, random)

            val rabinKarp = RabinKarp(pattern)

            (1..testChecksNumber).forEach {
                val text = randomString(
                        searchTextLength * random.nextInt(patternLen),
                        StringType.ONE_CHARACTER,
                        random)
                assertEquals(text.indexOf(pattern), rabinKarp.search(text))
            }
        }
    }

    @Test fun largeRandomAB(): Unit {
        val random = SecureRandom()
        for (i in 1..testsNumber) {
            val patternLen = random.nextInt(largeStringLen) + 1
            val pattern = randomString(patternLen, StringType.TWO_CHARACTERS, random)

            val rabinKarp = RabinKarp(pattern)

            (1..testChecksNumber).forEach {
                val text = randomString(
                        searchTextLength * random.nextInt(patternLen),
                        StringType.TWO_CHARACTERS,
                        random)
                assertEquals(text.indexOf(pattern), rabinKarp.search(text))
            }
        }
    }

    @Test fun largeRandomAZ(): Unit {
        val random = SecureRandom()
        for (i in 1..testsNumber) {
            val patternLen = random.nextInt(largeStringLen) + 1
            val pattern = randomString(patternLen, StringType.ALL_CHARACTERS, random)

            val rabinKarp = RabinKarp(pattern)

            (1..testChecksNumber).forEach {
                val text = randomString(
                        searchTextLength * random.nextInt(patternLen),
                        StringType.ALL_CHARACTERS,
                        random)
                assertEquals(text.indexOf(pattern), rabinKarp.search(text))
            }
        }
    }

    @Test fun extensionsTest(): Unit {
        val test: String = "ab"
        with(RabinKarp) {
            assertEquals(1, test.RabinKarpSearchIn("bababa"))
        }
    }
}