import java.math.BigInteger
import java.security.SecureRandom

/**
 * Generate an N-bit public and private RSA key and use to encrypt
 * and decrypt a message.
 *
 * @author Vasyl Antoniuk (@toniukan).
 * @since 0.1
 */

class RSA: AbstractCryptoAlgorithm {
    private var n: BigInteger
    private var d: BigInteger
    private var e: BigInteger

    private var bitlen = 1024

    /**
     * Create an instance that can encrypt using someone elses public key.
     */
    constructor(newn: BigInteger, newe: BigInteger) {
        n = newn
        e = newe
        d = e.modInverse(n)
    }

    /**
     * Create an instance that can both encrypt and decrypt.
     */
    constructor(bits: Int) {
        bitlen = bits
        val r = SecureRandom()
        val p = BigInteger(bitlen / 2, 100, r)
        val q = BigInteger(bitlen / 2, 100, r)
        n = p.multiply(q)
        val m = p.subtract(BigInteger.ONE).multiply(q
                .subtract(BigInteger.ONE))
        e = BigInteger("3")
        while (m.gcd(e).toInt() > 1) {
            e = e.add(BigInteger("2"))
        }
        d = e.modInverse(m)
    }

    /**
     *  Encrypt the given plaintext message.
     */
    override fun encrypt(message: String): String {
        return BigInteger(message.toByteArray()).modPow(e, n).toString()
    }

    /**
     * Decrypt the given ciphertext message.
     */
    override fun decrypt(message: String): String {
        return String(BigInteger(message).modPow(d, n).toByteArray())
    }

    /**
     * Generate a new public and private key set.
     */
    fun generateKeys() {
        val r = SecureRandom()
        val p = BigInteger(bitlen / 2, 100, r)
        val q = BigInteger(bitlen / 2, 100, r)
        n = p.multiply(q)
        val m = p.subtract(BigInteger.ONE).multiply(q
                .subtract(BigInteger.ONE))
        e = BigInteger("3")
        while (m.gcd(e).toInt() > 1) {
            e = e.add(BigInteger("2"))
        }
        d = e.modInverse(m)
    }

    /**
     * Return the modulus.
     */
    fun getN() = n

    /**
     * Return the public key.
     */
    fun getE() = e
}