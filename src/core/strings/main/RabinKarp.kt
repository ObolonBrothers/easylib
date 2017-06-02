
import java.util.Random
import java.math.BigInteger

/**
 *  The RabinKarp class finds the first occurrence of a pattern string in a text string.
 *
 *  This implementation uses the Rabin-Karp algorithm.

 * @author Vasyl Antoniuk (@toniukan).
 * @since 0.3
 */

class RabinKarp {
    private var pat: String             // the pattern  // needed only for Las Vegas
    private var patHash: Long           // pattern hash value
    private var patternLength: Int = 0  // pattern length
    private var q: Long = 0             // a large prime, small enough to avoid long overflow
    private var R: Int = 0              // radix
    private var RM: Long = 0            // R^(M-1) % Q

    /**
     * Preprocesses the pattern string.

     * @param pat the pattern string
     */
    constructor(pattern: String) {
        if (pattern.isEmpty())
            throw IllegalArgumentException("The parameter should be a non-empty string")

        this.pat = pattern      // save pattern (needed only for Las Vegas)
        R = 256
        patternLength = pattern.length
        q = longRandomPrime()

        // precompute R^(m-1) % q for use in removing leading digit
        RM = 1
        (1..patternLength - 1).forEach { RM = R * RM % q }
        patHash = hash(pat, patternLength)
    }

    // Compute hash for key[0..m-1].
    private fun hash(key: String, m: Int): Long {
        var h: Long = 0
        (0..m - 1).forEach { j -> h = (R * h + key[j].toInt()) % q }
        return h
    }

    // Las Vegas version: does pat[] match txt[i..i-m+1] ?
    private fun check(txt: String, i: Int): Boolean {
        return (0..patternLength - 1).none{
            @Suppress("DEPRECATED_IDENTITY_EQUALS")
            pat[it] !== txt[i + it]
        }
    }

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.

     * @param  txt the text string
     * *
     * @return the index of the first occurrence of the pattern string
     * *         in the text string; -1 if no such match
     */
    fun search(txt: String): Int {
        val n = txt.length
        if (n < patternLength) return -1
        var txtHash = hash(txt, patternLength)

        // check for match at offset 0
        @Suppress("DEPRECATED_IDENTITY_EQUALS")
        if (patHash === txtHash && check(txt, 0))
            return 0

        // check for hash match; if hash match, check for exact match
        (patternLength..n - 1).forEach { i ->
            // Remove leading digit, add trailing digit, check for match.
            txtHash = (txtHash + q - RM * txt[i - patternLength].toInt() % q) % q
            txtHash = (txtHash * R + txt[i].toInt()) % q

            // match
            val offset = i - patternLength + 1
            @Suppress("DEPRECATED_IDENTITY_EQUALS")
            if (patHash === txtHash && check(txt, offset))
                return offset
        }

        // no match
        return -1
    }

    // a random 31-bit prime
    private fun longRandomPrime(): Long {
        val prime = BigInteger.probablePrime(31, Random())
        return prime.toLong()
    }

    /**
     * Returns the index of the first occurrence of the pattern string
     * in the text string.

     * @param  text the text string
     * *
     * @return the index of the first occurrence of the pattern string
     * *         in the text string; -1 if no such match
     */
    companion object StringExtension {
        fun String.RabinKarpSearchIn(text: String): Int {
            return RabinKarp(this).search(text)
        }
    }
}