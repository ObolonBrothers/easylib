import org.junit.Test
import java.security.SecureRandom
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * @author is Vasyl Antoniuk (@toniukan)
 */
class RSATest {
    val testsNumber = 5
    val bits = 1024
    val chars = 256
    val stringLength = bits / 32

    @Test fun smallCase(): Unit {
        val rsa = RSA(bits)

        val message = "hello world"
        val encrypted = rsa.encrypt(message)
        assertEquals(message, rsa.decrypt(encrypted))
    }

    fun randomString(len: Int, random: SecureRandom): String {
        var res : String = ""
        for (i in 1..len) {
            res += random.nextInt(chars)
        }
        return res
    }

    @Test fun fixedKeyRandomString(): Unit {
        val random = SecureRandom()

        val rsa = RSA(bits)
        for (i in 1..testsNumber) {
            val message = randomString(random.nextInt(stringLength) + 1, random)
            val encrypted = rsa.encrypt(message)
            assertEquals(message, rsa.decrypt(encrypted))
        }
    }

    @Test fun randomKeyFixedString(): Unit {
        val random = SecureRandom()
        val message = randomString(random.nextInt(stringLength) + 1, random)

        val rsa = RSA(bits)
        for (i in 1..testsNumber) {
            rsa.generateKeys()
            val encrypted = rsa.encrypt(message)
            assertEquals(message, rsa.decrypt(encrypted))
        }
    }
}