/**
 *  The BoyerMoore class finds the first occurrence of a pattern string in a text string.
 *
 *  This implementation uses the Boyer-Moore algorithm (with the bad-character
 *  rule, but not the strong good suffix rule).

 * @author Vasyl Antoniuk (@toniukan).
 * @since 0.3
 */

class BoyerMoore {
    private val R: Int = 256        // the radix
    private var right: IntArray     // the bad-character skip array

    private val pat: String         // or as a string

    /**
     * Preprocesses the pattern string.

     * @param pat the pattern string
     */
    constructor(pat: String) {
        if (pat.isEmpty())
            throw IllegalArgumentException("The parameter should be a non-empty string")

        this.pat = pat

        // position of rightmost occurrence of c in the pattern
        right = IntArray(R)
        for (c in 0..R - 1)
            right[c] = -1
        for (j in 0..pat.length - 1)
            right[pat[j].toInt()] = j
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
        val m = pat.length
        val n = txt.length
        var skip: Int
        var i = 0
        while (i <= n - m) {
            skip = 0
            for (j in m - 1 downTo 0) {
                @Suppress("DEPRECATED_IDENTITY_EQUALS")
                if (pat[j] !== txt[i + j]) {
                    skip = Math.max(1, j - right[txt[i + j].toInt()])
                    break
                }
            }
            if (skip == 0) return i    // found
            i += skip
        }
        return -1                      // not found
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
        fun String.BoyerMooreSearchIn(text: String): Int {
            return BoyerMoore(this).search(text)
        }
    }
}