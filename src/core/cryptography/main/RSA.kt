import java.math.BigInteger
import java.security.SecureRandom

/**
 * Generate an N-bit public and private RSA key and use to encrypt
 * and decrypt a message.
 *
 * @author Vasyl Antoniuk (@toniukan).
 * @since 0.2
 */

class RSA: AbstractCryptoAlgorithm {
    private var modulo: BigInteger = BigInteger("1")
    private var privateKey: BigInteger = BigInteger("1")
    private var publicKey: BigInteger = BigInteger("1")

    private var bitlen = 1024

    /**
     * Create an instance that can encrypt using someone elses public key.
     */
    constructor(newModulo: BigInteger, newPublicKey: BigInteger) {
        modulo = newModulo
        publicKey = newPublicKey
        privateKey = publicKey.modInverse(modulo)
    }

    /**
     * Create an instance that can both encrypt and decrypt.
     */
    constructor(bits: Int) {
        bitlen = bits
        generateKeys()
    }

    /**
     *  Encrypt the given plaintext message.
     */
    override fun encrypt(message: String): String {
        return BigInteger(message.toByteArray()).modPow(publicKey, modulo).toString()
    }

    /**
     * Decrypt the given ciphertext message.
     */
    override fun decrypt(message: String): String {
        return String(BigInteger(message).modPow(privateKey, modulo).toByteArray())
    }

    /**
     * Generate a new public and private key set.
     */
    fun generateKeys() {
        val random = SecureRandom()
        val p = BigInteger(bitlen / 2, 100, random)
        val q = BigInteger(bitlen / 2, 100, random)
        modulo = p.multiply(q)
        val m = p.subtract(BigInteger.ONE)
                    .multiply(q.subtract(BigInteger.ONE))
        publicKey = BigInteger("3")
        while (m.gcd(publicKey).toInt() > 1) {
            publicKey = publicKey.add(BigInteger("2"))
        }
        privateKey = publicKey.modInverse(m)
    }

    /**
     * Return the modulus.
     */
    fun getModulo() = modulo

    /**
     * Return the public key.
     */
    fun getPublicKey() = publicKey
}