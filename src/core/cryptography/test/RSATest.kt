import org.junit.Test
import java.security.SecureRandom
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * @author is Vasyl Antoniuk (@toniukan)
 */
class RSATest {
    @Test fun smallCase(): Unit {
        val rsa = RSA(512)

        val message = "hello world"
        val encrypted = rsa.encrypt(message)
        assertEquals(message, rsa.decrypt(encrypted))
    }

    fun randomString(len: Int, minChar: Int, maxChar: Int, random: SecureRandom): String {
        var res : String = ""
        for (i in 1..len) {
            res += random.nextInt(maxChar - minChar + 1) + maxChar
        }
        return res
    }

    @Test fun fixedKeyRandomString(): Unit {
        val random = SecureRandom()

        val rsa = RSA(1024)
        for (i in 1..10) {
            val message = randomString(random.nextInt(20) + 1, 10, 256, random)
            val encrypted = rsa.encrypt(message)
            assertEquals(message, rsa.decrypt(encrypted))
        }
    }

    @Test fun randomKeyFixedString(): Unit {
        val random = SecureRandom()
        val message = randomString(random.nextInt(20) + 1, 10, 256, random)

        val rsa = RSA(1024)
        for (i in 1..10) {
            rsa.generateKeys()
            val encrypted = rsa.encrypt(message)
            assertEquals(message, rsa.decrypt(encrypted))
        }
    }
}