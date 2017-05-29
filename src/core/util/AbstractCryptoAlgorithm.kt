/**
 * @author is Vasyl Antoniuk (@toniukan)
 */

interface AbstractCryptoAlgorithm {
    /**
     * Encrypt the given plaintext message
     */
    fun encrypt(message: String): String

    /**
     * Decrypt the given ciphertext message
     */
    fun decrypt(message: String): String
}